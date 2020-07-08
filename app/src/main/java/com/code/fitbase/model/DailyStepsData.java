package com.code.fitbase.model;

import androidx.annotation.NonNull;

import java.util.Date;

public class DailyStepsData {
    private final Date date;
    private final int steps;

    public DailyStepsData(Date date, int steps) {
        this.date = new Date(date.getTime());
        this.steps = steps;
    }

    public Date getDate() {
        return date;
    }

    public int getSteps() {
        return steps;
    }

    @NonNull
    @Override
    public String toString() {
        return "DailyStepsData{" +
                "date=" + date +
                ", steps=" + steps +
                '}';
    }
}
