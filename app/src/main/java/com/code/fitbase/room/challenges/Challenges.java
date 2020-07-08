package com.code.fitbase.room.challenges;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Challenges")
public class Challenges implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Username")
    String userName;

    @ColumnInfo(name = "Workoutname")
    String workOutName;

    @ColumnInfo(name = "Points")
    String points;

    @ColumnInfo(name = "Repetitions")
    String repetitions;

    @ColumnInfo(name = "TimeInSeconds")
    String timeInSeconds;

    @ColumnInfo(name = "VideoFile")
    String videoFile;

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    public String getWorkOutName() {
        return workOutName;
    }

    public void setWorkOutName(String workOutName) {
        this.workOutName = workOutName;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(String repetitions) {
        this.repetitions = repetitions;
    }

    public String getTimeInSeconds() {
        return timeInSeconds;
    }

    public void setTimeInSeconds(String timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    public String getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }

    public Challenges(@NonNull String userName, String workOutName, String points, String repetitions, String timeInSeconds, String videoFile) {
        this.userName = userName;
        this.workOutName = workOutName;
        this.points = points;
        this.repetitions = repetitions;
        this.timeInSeconds = timeInSeconds;
        this.videoFile = videoFile;
    }
}
