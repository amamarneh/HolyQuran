package com.amarnehsoft.holyquran.main.readersFragment

import com.amarnehsoft.holyquran.test.Reader

import java.util.ArrayList

object ReadersProvider {
    val readers: List<Reader>
        get() {
            val readers = ArrayList<Reader>()
            readers.add(Reader.MaherMuaiqly)
            readers.add(Reader.Alafasy)
            readers.add(Reader.AhmedAjamy)
            readers.add(Reader.AbdurrahmaanSudais)
            readers.add(Reader.Shaatree)
            readers.add(Reader.Abdulsamad)
            readers.add(Reader.SaoodShuraym)
            readers.add(Reader.Husary)
            readers.add(Reader.HusaryMujawwad)
            readers.add(Reader.Hudhaify)
            readers.add(Reader.HaniRifai)
            readers.add(Reader.MuhammadAyyoub)
            readers.add(Reader.MuhammadJibreel)
            readers.add(Reader.IbrahimAkhbar)
            readers.add(Reader.AbdullahBasfar)
            readers.add(Reader.Parhizgar)
            return readers
        }
}
