package com.code.fitbase.model;

import androidx.annotation.NonNull;

public class RunData {
    private final int date;
    private final int averageHearRate;
    private final int averageStepRate;
    private final int totalCalories;
    private final double stepDistance;
    private final float totalAltitude;
    private final int totalDistance;
    private final int step;
    private final float totalDescent;
    private final long totalTime;
    private final float averagePace;
    private final double averageSpeed;

    public RunData(int date, int averageHearRate, int averageStepRate, int totalCalories, double stepDistance, float totalAltitude, int totalDistance, int step, float totalDescent, long totalTime, float averagePace, double averageSpeed) {
        this.date = date;
        this.averageHearRate = averageHearRate;
        this.averageStepRate = averageStepRate;
        this.totalCalories = totalCalories;
        this.stepDistance = stepDistance;
        this.totalAltitude = totalAltitude;
        this.totalDistance = totalDistance;
        this.step = step;
        this.totalDescent = totalDescent;
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

    public int getAverageStepRate() {
        return averageStepRate;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public double getStepDistance() {
        return stepDistance;
    }

    public float getTotalAltitude() {
        return totalAltitude;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public int getStep() {
        return step;
    }

    public float getTotalDescent() {
        return totalDescent;
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
        return "RunData{" +
                "\ndate=" + date +
                "\naverageHearRate=" + averageHearRate +
                "\naverageStepRate=" + averageStepRate +
                "\ntotalCalories=" + totalCalories +
                "\nstepDistance=" + stepDistance +
                "\ntotalAltitude=" + totalAltitude +
                "\ntotalDistance=" + totalDistance +
                "\nstep=" + step +
                "\ntotalDescent=" + totalDescent +
                "\ntotalTime=" + totalTime +
                "\naveragePace=" + averagePace +
                "\naverageSpeed=" + averageSpeed +
                "\n}\n";
    }
}
