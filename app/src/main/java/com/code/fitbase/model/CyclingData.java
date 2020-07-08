package com.code.fitbase.model;

import androidx.annotation.NonNull;

public class CyclingData {
    private final int date;
    private final int averageHearRate;
    private final int totalCalories;
    private final int totalDistance;
    private final long totalTime;
    private final float averagePace;
    private final double averageSpeed;

    public CyclingData(int date, int averageHearRate, int totalCalories, int totalDistance, long totalTime, float averagePace, double averageSpeed) {
        this.date = date;
        this.averageHearRate = averageHearRate;
        this.totalCalories = totalCalories;
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
        this.averagePace = averagePace;
        this.averageSpeed = averageSpeed;
    }

    public int getDate() {
        return date;
    }

    public int getAverageHearRate() {
        return averageHearRate;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public float getAveragePace() {
        return averagePace;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    @NonNull
    @Override
    public String toString() {
        return "CyclingData{" +
                "\ndate=" + date +
                "\naverageHearRate=" + averageHearRate +
                "\ntotalCalories=" + totalCalories +
                "\ntotalDistance=" + totalDistance +
                "\ntotalTime=" + totalTime +
                "\naveragePace=" + averagePace +
                "\naverageSpeed=" + averageSpeed +
                "\n}\n";
    }
}
