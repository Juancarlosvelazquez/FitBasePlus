package com.code.fitbase.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.code.fitbase.room.challenges.Challenges;
import com.code.fitbase.room.dao.ChallengesDao;
import com.code.fitbase.room.dao.FollowersDao;
import com.code.fitbase.room.dao.PrizesDao;
import com.code.fitbase.room.dao.ProductOwnersDao;
import com.code.fitbase.room.dao.ProfileDao;
import com.code.fitbase.room.products.Prizes;
import com.code.fitbase.room.products.ProductOwners;
import com.code.fitbase.room.profile.Followers;
import com.code.fitbase.room.profile.Profile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Profile.class, Followers.class, Prizes.class, ProductOwners.class, Challenges.class}, version = 1, exportSchema = false)
public abstract class FitBaseAppDatabase extends RoomDatabase {

    public abstract ProfileDao getUserDao();
    public abstract FollowersDao getFollowersDao();
    public abstract PrizesDao getPrizesDao();
    public abstract ProductOwnersDao getProductOwnersDao();
    public abstract ChallengesDao getChallengesDao();

    private static volatile FitBaseAppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static FitBaseAppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FitBaseAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FitBaseAppDatabase.class, "fitbase_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

