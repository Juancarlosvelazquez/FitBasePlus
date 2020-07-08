package com.code.fitbase.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RRIValue implements Serializable {
    @SerializedName("rri_value")
    private int rriValue;
    @SerializedName("rri_sqi")
    private int rriSqi;

    public int getRriValue() {
        return rriValue;
    }

    public int getRriSqi() {
        return rriSqi;
    }
}
