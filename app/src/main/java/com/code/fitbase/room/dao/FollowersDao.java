package com.code.fitbase.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.code.fitbase.room.profile.Followers;

@Dao
public interface FollowersDao {

    @Insert
    public void insert(Followers followers);

    @Query("SELECT COUNT(*) FROM Followers where Username =:userName ")
    public int getFollowersCount(String userName);

    @Query("SELECT COUNT(*) FROM Followers where Username =:userName")
    public int getFollowingCount(String userName);

    @Update
    public void update(Followers followers);

}
