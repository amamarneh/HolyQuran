package com.amarnehsoft.holyquran.main.readersFragment;

import com.amarnehsoft.holyquran.test.Reader;

import java.util.ArrayList;
import java.util.List;

public class ReadersProvider {
    public static List<Reader> getReaders(){
        ArrayList<Reader> readers = new ArrayList<>();
        readers.add(Reader.mahermuaiqly);
        readers.add(Reader.alafasy);
        readers.add(Reader.ahmedajamy);
        readers.add(Reader.abdurrahmaansudais);
        readers.add(Reader.shaatree);
        readers.add(Reader.abdulsamad);
        readers.add(Reader.saoodshuraym);
        readers.add(Reader.husary);
        readers.add(Reader.husarymujawwad);
        readers.add(Reader.hudhaify);
        readers.add(Reader.hanirifai);
        readers.add(Reader.muhammadayyoub);
        readers.add(Reader.muhammadjibreel);
        readers.add(Reader.ibrahimakhbar);
        readers.add(Reader.abdullahbasfar);
        readers.add(Reader.parhizgar);
        return readers;
    }
}
