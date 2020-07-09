package com.code.fitbase.room.profile;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
/**
 * Develop by Muazzam Abbas
 * Skype name muazzam.n-sol
 */
@Entity(tableName = "Profiles")
public class Profile implements Serializable {


    @ColumnInfo(name = "Email")
    String userEmail;

    public Profile(String userEmail,@NonNull String userName, String accumulatedPoints, String profilePicture, String instagramUsername, String tikTokUsername, String password) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.accumulatedPoints = accumulatedPoints;
        this.profilePicture = profilePicture;
        this.instagramUsername = instagramUsername;
        this.tikTokUsername = tikTokUsername;
        this.password = password;
    }
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Username")
    String userName;

    @ColumnInfo(name = "AccumulatedPoints")
    String accumulatedPoints;

    @ColumnInfo(name = "ProfilePicture")
    String profilePicture;

    @ColumnInfo(name = "InstagramUsername")
    String instagramUsername;

    @ColumnInfo(name = "TikTokUsername")
    String tikTokUsername;

    @ColumnInfo(name = "Password")
    String password;



    @NonNull
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@NonNull String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccumulatedPoints() {
        return accumulatedPoints;
    }

    public void setAccumulatedPoints(String accumulatedPoints) {
        this.accumulatedPoints = accumulatedPoints;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getInstagramUsername() {
        return instagramUsername;
    }

    public void setInstagramUsername(String instagramUsername) {
        this.instagramUsername = instagramUsername;
    }

    public String getTikTokUsername() {
        return tikTokUsername;
    }

    public void setTikTokUsername(String tikTokUsername) {
        this.tikTokUsername = tikTokUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
