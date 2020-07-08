package com.code.fitbase.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HeartBeatRate implements Serializable {
    @SerializedName("hr_info")
    private int heartBitRate;
    @SerializedName("time_info")
    private long timeStamp;

    public int getHeartBitRate() {
        return heartBitRate;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
