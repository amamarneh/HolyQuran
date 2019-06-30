package com.amarnehsoft.holyquran.model

import java.lang.Exception

interface State {
    fun exception(): Exception

    fun isErrorState(): Boolean
}