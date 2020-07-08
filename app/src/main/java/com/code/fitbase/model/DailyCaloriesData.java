package com.code.fitbase.model;

import androidx.annotation.NonNull;

import java.util.Date;

public class DailyCaloriesData {
    private final Date date;
    private final int calories;

    public DailyCaloriesData(Date date, int calories) {
        this.date = new Date(date.getTime());
        this.calories = calories;
    }

    public Date getDate() {
        return date;
    }

    public int getCalories() {
        return calories;
    }

    @NonNull
    @Override
    public String toString() {
        return "DailyCaloriesData{" +
                "date=" + date +
                ", calories=" + calories +
                '}';
    }
}
