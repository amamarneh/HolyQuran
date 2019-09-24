package com.amarnehsoft.holyquran.network

import com.amarnehsoft.holyquran.model.Surah
import com.amarnehsoft.holyquran.model.TafseerAyah
import com.amarnehsoft.holyquran.network.n.RemoteError
import com.amarnehsoft.holyquran.network.n.RemoteResponse
import com.amarnehsoft.holyquran.network.quran.Quran
import com.amarnehsoft.holyquran.network.quran.QuranApi
import com.amarnehsoft.holyquran.network.tafseer.Tafseer
import com.amarnehsoft.holyquran.network.tafseer.TafseerApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
        private val quranApi: QuranApi,
        private val tafseerApiService: TafseerApi
) : RemoteDataSource {

    override suspend fun getTafseerList(): RemoteResponse<List<Tafseer>, Exception> {
        return safeApiCallAllowEmptyResponses(
                response = tafseerApiService.getTafseerList(),
                bodyToRemote = {
                    RemoteResponse.createSuccess<List<Tafseer>?, Exception>(it)
                },
                remoteError = {
                    it.asException()
                }
        ).map {
            it.orEmpty()
        }
    }

    override suspend fun getQuran(): RemoteResponse<Quran, Exception> {
        return safeApiCall(
                response = quranApi.getQuran(),
                bodyToRemote = {
                    RemoteResponse.createSuccess<Quran, Exception>(it.data)
                },
                remoteError = {
                    it.asException()
                }
        )
    }

    override suspend fun getAyah(ayahNumber: Int): RemoteResponse<Quran.Ayah, Exception> {
        return safeApiCall(
                response = quranApi.getAyah(ayahNumber),
                bodyToRemote = {
                    RemoteResponse.createSuccess<Quran.Ayah, Exception>(it.data)
                },
                remoteError = {
                    it.asException()
                }
        )
    }

    override suspend fun translateAya(ayaNumber: Int): RemoteResponse<Quran.Ayah, Exception> {
        return safeApiCall(
                response = quranApi.translateAya(ayaNumber),
                bodyToRemote = {
                    RemoteResponse.createSuccess<Quran.Ayah, Exception>(it.data)
                },
                remoteError = {
                    it.asException()
                }
        )
    }

    override suspend fun getChaptersList(): RemoteResponse<List<Surah>, Exception> {
        return safeApiCall(
                response = quranApi.surahsList(),
                bodyToRemote = {
                    RemoteResponse.createSuccess<List<Surah>, Exception>(
                            it.data.map {
                                Surah(
                                        it.number,
                                        it.name,
                                        it.englishName,
                                        it.englishNameTranslation,
                                        it.ayahs.size,
                                        it.revelationType
                                )
                            }
                    )
                },
                remoteError = {
                    it.asException()
                }
        )
    }

    override suspend fun tafseerAyah(
            tafseer_id: Int,
            sura_number: Int,
            ayah_number: Int
    ): RemoteResponse<TafseerAyah, Exception> {
        return safeApiCall(
                response = tafseerApiService.tafseerAyah(tafseer_id, sura_number, ayah_number),
                bodyToRemote = {
                    RemoteResponse.createSuccess<TafseerAyah, Exception>(it)
                },
                remoteError = {
                    it.asException()
                }
        )
    }

    private fun <T, R, E> Response<T>.toRemoteResponse(
            bodyToRemote: (T) -> RemoteResponse<R, E>,
            remoteError: (error: RemoteError) -> E
    ): RemoteResponse<R, E> {
        return if (isSuccessful) {
            val body = body()
            if (body == null) {
                RemoteResponse.createError(remoteError(RemoteError.general(Exception("empty response"))))
            } else {
                bodyToRemote(body)
            }
        } else {
            RemoteResponse.createError(remoteError(RemoteError.Failed(code())))
        }
    }

    private fun <T, R, E> Response<T>.toRemoteResponseAllowEmptyResponses(
            bodyToRemote: (T?) -> RemoteResponse<R?, E>,
            remoteError: (error: RemoteError) -> E
    ): RemoteResponse<R?, E> {
        return if (isSuccessful) {
            val body = body()
            if (body == null) {
                RemoteResponse.createSuccess<R?, E>(null)
            } else {
                bodyToRemote(body)
            }
        } else {
            RemoteResponse.createError(remoteError(RemoteError.Failed(code())))
        }
    }

    private fun <T, R, E> safeApiCall(
            response: Response<T>,
            bodyToRemote: (T) -> RemoteResponse<R, E>,
            remoteError: (RemoteError) -> E
    ): RemoteResponse<R, E> {
        return try {
            response.toRemoteResponse(
                    bodyToRemote = {
                        bodyToRemote(it)
                    },
                    remoteError = remoteError
            )
        } catch (e: Exception) {
            RemoteResponse.createError(remoteError(RemoteError.General(e)))
        }
    }

    private fun <T, R, E> safeApiCallAllowEmptyResponses(
            response: Response<T>,
            bodyToRemote: (T?) -> RemoteResponse<R?, E>,
            remoteError: (RemoteError) -> E
    ): RemoteResponse<R?, E> {
        return try {
            response.toRemoteResponseAllowEmptyResponses(
                    bodyToRemote = {
                        bodyToRemote(it)
                    },
                    remoteError = remoteError
            )
        } catch (e: Exception) {
            RemoteResponse.createError(remoteError(RemoteError.General(e)))
        }
    }

//    private fun <R, E> RemoteResponse<R?, E>.notNullBody(emptyError: () -> E): RemoteResponse<R, E> {
//        return when (this) {
//            is RemoteSuccessResponse -> {
//                if (this.body == null) {
//                    RemoteResponse.createError(emptyError())
//                } else {
//                    RemoteResponse.createSuccess(this.body)
//                }
//            }
//            is RemoteErrorResponse -> RemoteResponse.createError(this.error)
//        }
//    }
//
//    private fun <R> RemoteResponse<R?, Exception>.notNullBody(): RemoteResponse<R, Exception> {
//        return notNullBody {
//            Exception("body is null")
//        }
//    }
}