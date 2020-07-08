package com.code.fitbase.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.code.fitbase.room.challenges.Challenges;
import com.code.fitbase.room.profile.Profile;

import java.util.List;

@Dao
public interface ChallengesDao {

    @Insert
    public void insert(Challenges user);

    @Query("SELECT * FROM Challenges")
    public List<Challenges> getAll();

    @Update
    public void update(Challenges user);

}
