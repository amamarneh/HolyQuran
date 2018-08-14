package com.amarnehsoft.holyquran.network.response;

import com.amarnehsoft.holyquran.model.Aya;

public class GetAyaResponse extends BaseResponse{
    private Aya data;

    public Aya getData() {
        return data;
    }

    public void setData(Aya data) {
        this.data = data;
    }
}
