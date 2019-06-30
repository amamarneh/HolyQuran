package com.amarnehsoft.holyquran.main.readersFragment

import com.amarnehsoft.holyquran.test.Reader

object ReaderController {
    var currentReader = Reader.MaherMuaiqly
        private set

    fun changeReader(readers: Reader) {
        currentReader = readers
    }
}
