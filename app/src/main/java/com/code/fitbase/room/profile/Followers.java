package com.code.fitbase.room.profile;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Followers")
public class Followers {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Username")
    String userName;

    @ColumnInfo(name = "Following")
    String following;

    @ColumnInfo(name = "Followers")
    String Followers;

    @ColumnInfo(name = "Status")
    Integer status;

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFollowers() {
        return Followers;
    }

    public void setFollowers(String followers) {
        Followers = followers;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Followers(){}
    public Followers(@NonNull String userName, String following, String followers, Integer status) {
        this.userName = userName;
        this.following = following;
        Followers = followers;
        this.status = status;
    }
}
