package com.amarnehsoft.holyquran.network.response

open class ApiResponse<T>(
    val code: Int,
    val status: String,
    val data: T
)
