package com.code.fitbase.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.code.fitbase.room.products.Prizes;
import com.code.fitbase.room.products.ProductOwners;

import java.util.List;

@Dao
public interface ProductOwnersDao {

    @Insert
    public void insert(ProductOwners productOwners);

    @Query("SELECT * FROM ProductOwners")
    public List<ProductOwners> getUserUsingEmailAndPassword();


    @Query("SELECT COUNT(*) FROM ProductOwners where CompanyName =:companyName ")
    public int checkUserExist(String companyName);

    @Update
    public void update(ProductOwners prize);

}
