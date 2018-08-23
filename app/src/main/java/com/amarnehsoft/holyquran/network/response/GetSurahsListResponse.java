package com.amarnehsoft.holyquran.network.response;

import com.amarnehsoft.holyquran.model.Surah;

import java.util.List;

public class GetSurahsListResponse extends BaseResponse{
    private List<Surah> data;

    public List<Surah> getData() {
        return data;
    }

    public void setData(List<Surah> data) {
        this.data = data;
    }
}
