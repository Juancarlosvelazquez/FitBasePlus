package com.code.fitbase.model;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.huawei.hihealthkit.data.HiHealthKitConstant;

import java.util.Date;

public class RealTimeSportData {
    private final float targetValue;
    private final int duration;
    private final int originTarget;
    private final int aerobicExercise;
    private final int sportType;
    private final int totalCreep;
    private final int totalSteps;
    private final int distanceTarget;
    private final int sportsState;
    private final int gps;
    private final int pace;
    private final int speed;
    private final int heartRate;
    private final int heartZone;
    private final int distance;
    private final int performanceIndicator;
    private final int targetType;
    private final Date sportsStartTime;
    private final int calories;
    private final int trackType;
    private final int anaerobicExercise;
    private final int linkType;
    private final int stepRate;
    private final Date heartRateTime;
    private final int altitude;
    private final int totalDescent;

    private RealTimeSportData(float targetValue, int duration, int originTarget, int aerobicExercise, int sportType, int totalCreep, int totalSteps, int distanceTarget, int sportsState, int gps, int pace, int speed, int heartRate, int heartZone, int distance, int performanceIndicator, int targetType, Date sportsStartTime, int calories, int trackType, int anaerobicExercise, int linkType, int stepRate, Date heartRateTime, int altitude, int totalDescent) {
        this.targetValue = targetValue;
        this.duration = duration;
        this.originTarget = originTarget;
        this.aerobicExercise = aerobicExercise;
        this.sportType = sportType;
        this.totalCreep = totalCreep;
        this.totalSteps = totalSteps;
        this.distanceTarget = distanceTarget;
        this.sportsState = sportsState;
        this.gps = gps;
        this.pace = pace;
        this.speed = speed;
        this.heartRate = heartRate;
        this.heartZone = heartZone;
        this.distance = distance;
        this.performanceIndicator = performanceIndicator;
        this.targetType = targetType;
        this.sportsStartTime = sportsStartTime;
        this.calories = calories;
        this.trackType = trackType;
        this.anaerobicExercise = anaerobicExercise;
        this.linkType = linkType;
        this.stepRate = stepRate;
        this.heartRateTime = heartRateTime;
        this.altitude = altitude;
        this.totalDescent = totalDescent;
    }

    public float getTargetValue() {
        return targetValue;
    }

    public int getDuration() {
        return duration;
    }

    public int getOriginTarget() {
        return originTarget;
    }

    public int getAerobicExercise() {
        return aerobicExercise;
    }

    public int getSportType() {
        return sportType;
    }

    public int getTotalCreep() {
        return totalCreep;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public int getDistanceTarget() {
        return distanceTarget;
    }

    public int getSportsState() {
        return sportsState;
    }

    public int getGps() {
        return gps;
    }

    public int getPace() {
        return pace;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public int getHeartZone() {
        return heartZone;
    }

    public int getDistance() {
        return distance;
    }

    public int getPerformanceIndicator() {
        return performanceIndicator;
    }

    public int getTargetType() {
        return targetType;
    }

    public Date getSportsStartTime() {
        return sportsStartTime;
    }

    public int getCalories() {
        return calories;
    }

    public int getTrackType() {
        return trackType;
    }

    public int getAnaerobicExercise() {
        return anaerobicExercise;
    }

    public int getLinkType() {
        return linkType;
    }

    public int getStepRate() {
        return stepRate;
    }

    public Date getHeartRateTime() {
        return heartRateTime;
    }

    public int getAltitude() {
        return altitude;
    }

    public int getTotalDescent() {
        return totalDescent;
    }

    @NonNull
    @Override
    public String toString() {
        return "RealTimeSportData{" +
                "\ntargetValue=" + targetValue +
                "\nduration=" + duration +
                "\noriginTarget=" + originTarget +
                "\naerobicExercise=" + aerobicExercise +
                "\nsportType=" + sportType +
                "\ntotalCreep=" + totalCreep +
                "\ntotalSteps=" + totalSteps +
                "\ndistanceTarget=" + distanceTarget +
                "\nsportsState=" + sportsState +
                "\ngps=" + gps +
                "\npace=" + pace +
                "\nspeed=" + speed +
                "\nheartRate=" + heartRate +
                "\nheartZone=" + heartZone +
                "\ndistance=" + distance +
                "\nperformanceIndicator=" + performanceIndicator +
                "\ntargetType=" + targetType +
                "\nsportsStartTime=" + sportsStartTime +
                "\ncalories=" + calories +
                "\ntrackType=" + trackType +
                "\nanaerobicExercise=" + anaerobicExercise +
                "\nlinkType=" + linkType +
                "\nstepRate=" + stepRate +
                "\nheartRateTime=" + heartRateTime +
                "\naltitude=" + altitude +
                "\ntotalDescent=" + totalDescent +
                "\n}";
    }

    public static RealTimeSportData createFromBundle(Bundle bundle) {
        return new RealTimeSportData(
                bundle.getFloat(HiHealthKitConstant.BUNDLE_KEY_TARGET_VALUE),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_DURATION),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_ORIGIN_TARGET),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_AEROBIC_EXERCISE),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_SPORT_TYPE),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_TOTAL_CREEP),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_TOTAL_STEPS),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_DISTANCE_TARGET),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_SPORT_STATE),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_GPS),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_PACE),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_SPEED),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_HEARTRATE),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_HEART_RATE_ZONE),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_DISTANCE),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_PERFORMANCE_INDICATOR),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_TARGET_TYPE),
                new Date(bundle.getLong(HiHealthKitConstant.BUNDLE_KEY_SPORT_START_TIME)),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_CALORIE),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_TRACK_TYPE),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_ANAEROBIC_EXERCISE),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_LINK_TYPE),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_STEP_RATE),
                new Date(bundle.getLong(HiHealthKitConstant.BUNDLE_KEY_HEARTRATE_TIME)),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_ALTITUDE),
                bundle.getInt(HiHealthKitConstant.BUNDLE_KEY_TOTAL_DESCENT));
    }
}