package com.amarnehsoft.holyquran.domain

import androidx.lifecycle.LiveData
import com.amarnehsoft.holyquran.data.GetQuranResult
import com.amarnehsoft.holyquran.model.Result
import com.amarnehsoft.holyquran.network.quran.Quran
import com.amarnehsoft.holyquran.repo.Repo
import javax.inject.Inject

class GetQuranUseCase @Inject constructor(private val repo: Repo) {

    operator fun invoke(): LiveData<GetQuranResult> {
        return repo.getQuran()
    }
}