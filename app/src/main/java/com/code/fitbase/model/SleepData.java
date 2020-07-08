package com.code.fitbase.model;

import androidx.annotation.NonNull;

public class SleepData {
    private final float remSleep;
    private final float deepSleep;
    private final float lightSleep;
    private final float wholeDaySleepAmount;
    private final float nightSleepAmount;
    private final float deepSleepContinuity;
    private final float awakeTime;
    private final long bedTime;
    private final long riseTime;
    private final long sleepScore;

    public SleepData(float remSleep, float deepSleep, float lightSleep, float wholeDaySleepAmount, float nightSleepAmount, float deepSleepContinuity, float awakeTime, long bedTime, long riseTime, long sleepScore) {
        this.remSleep = remSleep;
        this.deepSleep = deepSleep;
        this.lightSleep = lightSleep;
        this.wholeDaySleepAmount = wholeDaySleepAmount;
        this.nightSleepAmount = nightSleepAmount;
        this.deepSleepContinuity = deepSleepContinuity;
        this.awakeTime = awakeTime;
        this.bedTime = bedTime;
        this.riseTime = riseTime;
        this.sleepScore = sleepScore;
    }

    public float getRemSleep() {
        return remSleep;
    }

    public float getDeepSleep() {
        return deepSleep;
    }

    public float getLightSleep() {
        return lightSleep;
    }

    public float getWholeDaySleepAmount() {
        return wholeDaySleepAmount;
    }

    public float getNightSleepAmount() {
        return nightSleepAmount;
    }

    public float getDeepSleepContinuity() {
        return deepSleepContinuity;
    }

    public float getAwakeTime() {
        return awakeTime;
    }

    public long getBedTime() {
        return bedTime;
    }

    public long getRiseTime() {
        return riseTime;
    }

    public long getSleepScore() {
        return sleepScore;
    }

    @NonNull
    @Override
    public String toString() {
        return "SleepData{" +
                "\nremSleep=" + remSleep +
                "\ndeepSleep=" + deepSleep +
                "\nlightSleep=" + lightSleep +
                "\nwholeDaySleepAmount=" + wholeDaySleepAmount +
                "\nnightSleepAmount=" + nightSleepAmount +
                "\ndeepSleepContinuity=" + deepSleepContinuity +
                "\nawakeTime=" + awakeTime +
                "\nbedTime=" + bedTime +
                "\nriseTime=" + riseTime +
                "\nsleepScore=" + sleepScore +
                "\n}\n";
    }
}
