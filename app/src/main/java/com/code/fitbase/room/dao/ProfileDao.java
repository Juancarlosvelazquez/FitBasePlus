package com.code.fitbase.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.code.fitbase.room.profile.Profile;

import java.util.List;

@Dao
public interface ProfileDao {

    @Insert
    public void insert(Profile user);

    @Query("SELECT * FROM Profiles where Username =:email AND Password=:password AND Username=:userName")
    public Profile getUserUsingEmailAndPassword(String email, String password, String userName);

    @Query("SELECT * FROM Profiles")
    public List<Profile> getAll();


    @Query("SELECT COUNT(*) FROM Profiles")
    public int checkUserExist();

    @Update
    public void update(Profile user);

}
