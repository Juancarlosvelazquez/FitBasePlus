package com.code.fitbase.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.code.fitbase.room.products.Prizes;
import com.code.fitbase.room.profile.Profile;

import java.util.List;
/**
 * Develop by Muazzam Abbas
 * Skype name muazzam.n-sol
 */
@Dao
public interface PrizesDao {

    @Insert
    public void insert(Prizes prize);

    @Query("SELECT * FROM Prizes")
    public List<Prizes> getAll();


    @Query("SELECT COUNT(*) FROM Prizes where Code =:code ")
    public int checkUserExist(String code);

    @Update
    public void update(Prizes prize);

}
