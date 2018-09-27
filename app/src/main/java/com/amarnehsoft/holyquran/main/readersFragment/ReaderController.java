package com.amarnehsoft.holyquran.main.readersFragment;

import com.amarnehsoft.holyquran.test.Reader;

public class ReaderController {
    private static Reader currentReader = Reader.mahermuaiqly;

    public static void changeReader(Reader readers){
        currentReader = readers;
    }

    public static Reader getCurrentReader(){
        return currentReader;
    }
}
