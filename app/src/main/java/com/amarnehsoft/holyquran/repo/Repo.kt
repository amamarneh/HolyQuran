package com.amarnehsoft.holyquran.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amarnehsoft.holyquran.SPController
import com.amarnehsoft.holyquran.data.GetQuranResult
import com.amarnehsoft.holyquran.db.AyahMapper
import com.amarnehsoft.holyquran.model.Result
import com.amarnehsoft.holyquran.model.Surah
import com.amarnehsoft.holyquran.network.RemoteDataSource
import com.amarnehsoft.holyquran.network.n.RemoteResponse
import com.amarnehsoft.holyquran.network.quran.Quran
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val spController: SPController
) {

    private val ayaCache = HashMap<Int, Quran.Ayah>() // ayaNumber, aya

    fun getQuran(): LiveData<GetQuranResult> {
        val startTime = System.currentTimeMillis()
        val response = MutableLiveData<GetQuranResult>()

        response.postValue(GetQuranResult.FetchingFromNetwork)

        GlobalScope.launch(IO) {
            val result = remoteDataSource.getQuran()

            Timber.i("time elapsed for get quran from remote data source = ${System.currentTimeMillis() - startTime} ms.")

            when (result) {
                is RemoteResponse.Success -> {
                    response.postValue(GetQuranResult.SaveToDatabase)

                    val surahsList = result.body.surahs
                    val ayasList = surahsList.flatMap { surah ->
                        surah.ayahs.map {
                            AyahMapper.map(it, surah.number)
                        }
                    }
                    localDataSource.insertChaptersAndVerses(
                            chapters = surahsList.map {
                                Surah(
                                        it.number,
                                        it.name,
                                        it.englishName,
                                        it.englishNameTranslation,
                                        it.ayahs.size,
                                        it.revelationType
                                )
                            },
                            verses = ayasList
                    )
                    spController.isQuranDownloaded = true
                    response.postValue(GetQuranResult.Success)
                }
                is RemoteResponse.Error -> {
                    response.postValue(GetQuranResult.Error(result.error))
                }
            }
        }
        val endTime = System.currentTimeMillis()
        Timber.i("elapsed time in getQuran fun: ${endTime - startTime} ms.")
        return response
    }

    suspend fun chaptersList(): Result<List<Surah>> {
        val listFromDB = getFromDB { localDataSource.getChaptersList() }
        return if (listFromDB.size != 114) {
            val result = remoteDataSource.getChaptersList().toResult()
            withContext(IO) {
                launch {
                    if (result is Result.Success) {
                        localDataSource.insertChapters(result.data)
                    }
                }
            }
            return result
        } else {
            Result.Success(listFromDB)
        }
    }

    suspend fun getAya(number: Int): Quran.Ayah = (ayaCache[number] ?: getAyaFromDB(number))
            ?: throw RuntimeException("Can't find aya:$number in cache nor in database.")

    private suspend fun getAyaFromApi(number: Int): Result<Quran.Ayah> {
        val aya = remoteDataSource.getAyah(number).toResult()
//            .map {
//            it.apply {
//                surah?.let {
//                    surahNumber = it.number
//                    suraDao.insert(it)
//                }
//            }
//        }
//        val translation = translateAya(number)
//        if (translation is Result.Success) {
////            aya = aya.map {
//////                it.apply {
//////                    this.translation = translation.data
//////                }
////            }
//        }
//        val tafseer = if (aya is Result.Success) {
//            //tafseerAyah(aya.data.surahNumber, aya.data.numberInSurah)
//        } else {
//            Result.Error(RuntimeException("aya result is Error"))
//        }
//        if (tafseer is Result.Success) {
//            aya = aya.map {
//                it.apply {
//                    this.tafseer = tafseer.data.text
//                }
//            }
//        }

        if (aya is Result.Success) {
            //ayaDao.insert(AyahMapper.map(aya.data, 1))
            ayaCache[number] = aya.data
        }
//        return AyaResponse(
//            ayaResult = aya,
//            tafseerResult = tafseer,
//            translationResult = translation
//        )
        return aya
    }

    private suspend fun getAyaFromDB(number: Int): Quran.Ayah? {
        return getFromDB {
            localDataSource.getAya(number)
            //ayaDao.getByNumber(number)?.let { AyahMapper.map(it) }?.apply {
            //surah = suraDao.getSurah(surahNumber)
        }.also {
            ayaCache[number] = it
        }
    }

//    private suspend fun tafseerAyah(suraNumber: Int, numberInSura: Int): Result<TafseerAyah> =
//        remoteDataSource.tafseerAyah(1, suraNumber, numberInSura)
//
//    private suspend fun translateAya(ayaNumber: Int): Result<String> =
//        remoteDataSource.translateAya(ayaNumber).map { it.text }

    private suspend fun <T> getFromDB(callback: suspend () -> T): T {
        return withContext(IO) {
            callback()
        }
    }

    private fun <T : Any, E> RemoteResponse<T, E>.toResult(remoteError: (E) -> Exception): Result<T> {
        return when (this) {
            is RemoteResponse.Success -> Result.Success(this.body)
            is RemoteResponse.Error -> Result.Error(remoteError(this.error))
        }
    }

    fun <T : Any> RemoteResponse<T, Exception>.toResult(): Result<T> {
        return toResult {
            it
        }
    }

}