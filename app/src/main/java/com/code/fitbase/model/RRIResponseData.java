package com.code.fitbase.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RRIResponseData implements Serializable {
    @SerializedName("rri_time")
    private long rriTime;
    @SerializedName("rri_intensity")
    private int rriIntensity;
    @SerializedName("rri_acc_state")
    private int rriAccState;
    @SerializedName("rri_motion_state")
    private int rriMotionState;
    @SerializedName("rri_list")
    private List<RRIValue> rriList;

    public long getRriTime() {
        return rriTime;
    }

    public int getRriIntensity() {
        return rriIntensity;
    }

    public int getRriAccState() {
        return rriAccState;
    }

    public int getRriMotionState() {
        return rriMotionState;
    }

    public List<RRIValue> getRriList() {
        return rriList;
    }

    public String getStringRepresentation() {
        StringBuilder str = new StringBuilder();
        if (rriIntensity != 0) {
            str.append("Intensity = ").append(rriIntensity);
        }
        if (rriAccState != 0) {
            str.append("\nAccState = ").append(rriAccState);
        }
        if (rriMotionState != 0) {
            str.append("\nMotionState = ").append(rriMotionState);
        }
        str.append("\nrriList = {\n");
        for (RRIValue value : rriList) {
            if (value.getRriValue() != 0 && value.getRriSqi() != 0) {
                str.append("value = ").append(value.getRriValue()).append(" ms")
                        .append(", sqi = ").append(value.getRriSqi())
                        .append("\n");
            }
        }
        str.append("}");
        return str.toString();
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (RRIValue value : rriList) {
            if (value.getRriValue() != 0 && value.getRriSqi() != 0) {
                str.append(value.getRriValue()).append(" ms").append("\n");
            }
        }
        return str.toString();
    }
}
