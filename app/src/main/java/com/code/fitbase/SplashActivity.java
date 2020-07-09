package com.code.fitbase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.code.fitbase.room.FitBaseAppDatabase;
import com.code.fitbase.room.challenges.Challenges;
import com.code.fitbase.room.products.Prizes;
import com.code.fitbase.room.profile.Profile;

import java.util.List;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.code.fitbase.databinding.FragmentWorkoutsBinding;
import com.code.fitbase.model.DailyCaloriesData;
import com.code.fitbase.model.DailyStepsData;



import java.util.Date;

import static com.code.fitbase.util.Utils.nDaysAgo;


public class SplashActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private FragmentWorkoutsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

//
//        setupOnClickListeners();

        setupAuthObservers();
        setupQueryObservers();
        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //User user = null;
//                Intent intent = null;
//                intent = new Intent(SplashActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 2000);

        new SaveDemoDataInDatabase().execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.checkAuthorizationStatus();
    }


    private void setupAuthObservers() {
        viewModel.getAuthStatus().observe(this, permissionStatus -> {
            switch (permissionStatus) {
                case REQUEST_AUTHORIZATION:
                case PERMISSION_NOT_GRANTED:
                    askForPermissions();
                    break;
                case GOT_PERMISSION:
                    viewModel.fetchHealthInfo();
                    break;
                default:
                    finish();
                    break;
            }
        });
    }

    private void setupQueryObservers() {

        viewModel.getHeartRate().observe(this, value -> binding.heartRate.setText(getString(R.string.heart_rate, value.getHeartBitRate())));
//
//        viewModel.getStepSum().observe(this, stepsData -> {
//            StringBuilder stringBuilder = new StringBuilder();
//            for (DailyStepsData datum : stepsData) {
//                stringBuilder
//                        .append(getString(R.string.steps_daily_sums,
//                                datum.getDate(),
//                                datum.getSteps()))
//                        .append("\n");
//            }
//            binding.queryResults.setText(stringBuilder.toString());
//        });
//
//        viewModel.getCaloriesSum().observe(this, caloriesDailyData -> {
//            StringBuilder stringBuilder = new StringBuilder();
//            for (DailyCaloriesData datum : caloriesDailyData) {
//                stringBuilder
//                        .append(getString(R.string.calories_daily_sums,
//                                datum.getDate(),
//                                datum.getCalories()))
//                        .append("\n");
//            }
//            binding.queryResults.setText(stringBuilder.toString());
//        });
    }

//    private void setupOnClickListeners() {
//        binding.getDailyStepsData.setOnClickListener(v -> {
//            cleanQueryResults();
//            viewModel.fetchStepSum(getQuerySinceDate(), getQueryTillDate());
//        });
//
//        binding.geCaloriesData.setOnClickListener(v -> {
//            cleanQueryResults();
//            viewModel.fetchCaloriesSum(getQuerySinceDate(), getQueryTillDate());
//        });
//
//    }

//    private void cleanQueryResults() {
//        binding.queryResults.setText(null);
//    }
//
//    private Date getQuerySinceDate() {
//        return nDaysAgo(binding.querySince);
//    }
//
//    private Date getQueryTillDate() {
//        return nDaysAgo(binding.queryTill);
//    }

    void askForPermissions() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permissions_dialog_title)
                .setMessage(R.string.permissions_dialog_message)
                .setPositiveButton(R.string.settings, (dialog, which) -> viewModel.requestAuthorization())
                .setNegativeButton(android.R.string.no, (dialog, which) -> finish())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public class SaveDemoDataInDatabase extends AsyncTask<Void, Void, Boolean> {

        FitBaseAppDatabase fitBaseAppDatabase;

        @Override
        protected Boolean doInBackground(Void... voids) {

            fitBaseAppDatabase = FitBaseAppDatabase.getDatabase(SplashActivity.this);

            if (fitBaseAppDatabase != null) {

                List<Challenges> challenges = fitBaseAppDatabase.getChallengesDao().getAll();
                if (challenges.size()>0) {

                } else {
                    /**
                     * Adding challenges data
                     */
                    Challenges challenge1 = new Challenges(
                            "fraserwilsonfit",
                            "Side plank",
                            "5000",
                            "50",
                            "6",
                            "https://res.cloudinary.com/ddgybihx9/video/upload/v1593785360/1_gnfydi.mp4");


                    Challenges challenge2 = new Challenges(
                            "senada.greca",
                            "Static V Up",
                            "6000",
                            "15",
                            "6",
                            "https://res.cloudinary.com/ddgybihx9/video/upload/v1593786811/2_lu870d.mp4");


                    Challenges challenge3 = new Challenges(
                            "clairepthomas",
                            "Plank + flying arm",
                            "7500",
                            "25",
                            "6",
                            "https://res.cloudinary.com/ddgybihx9/video/upload/v1593786948/3_epmeqx.mp4");

                    Challenges challenge4 = new Challenges(
                            "fraserwilsonfit",
                            "Russian slam",
                            "4000",
                            "25",
                            "6",
                            "https://res.cloudinary.com/ddgybihx9/video/upload/v1593786959/4_nbfcya.mp4");


                    fitBaseAppDatabase.getChallengesDao().insert(challenge1);
                    fitBaseAppDatabase.getChallengesDao().insert(challenge2);
                    fitBaseAppDatabase.getChallengesDao().insert(challenge3);
                    fitBaseAppDatabase.getChallengesDao().insert(challenge4);


                }

                List<Prizes> prizesList = fitBaseAppDatabase.getPrizesDao().getAll();

                if (prizesList.size()>0) {

                } else {

                    /**
                     * Adding Products data
                     */

                    Prizes prize1 = new Prizes(
                            "Adidas23",
                            "2/6/2020",
                            "20/6/2021",
                            "Addidas",
                            "T-Shirt Casual",
                            "https://pasteboard.co/JfH8qxj.png",
                            "20000",
                            "15"
                    );

                    Prizes prize2 = new Prizes(
                            "Nike156",
                            "2/6/2020",
                            "25/7/2020",
                            "Nike",
                            "Sports Bottle",
                            "https://ibb.co/GJpVzvF",
                            "10000",
                            "25"
                    );

                    Prizes prize3 = new Prizes(
                            "HUAWEI782",
                            "2/6/2020",
                            "29/8/2020",
                            "Huawei",
                            "Sport Band",
                            "https://ibb.co/GJpVzvF",
                            "90000",
                            "5"
                    );

                    Prizes prize4 = new Prizes(
                            "HUAWEI782",
                            "2/6/2020",
                            "29/8/2020",
                            "Huawei",
                            "Sport Band",
                            "https://ibb.co/GJpVzvF",
                            "90000",
                            "5"
                    );


                    fitBaseAppDatabase.getPrizesDao().insert(prize1);
                    fitBaseAppDatabase.getPrizesDao().insert(prize2);
                    fitBaseAppDatabase.getPrizesDao().insert(prize3);
                    fitBaseAppDatabase.getPrizesDao().insert(prize4);

                }


                int exist = fitBaseAppDatabase.getUserDao().checkUserExist();
                if (exist == 0) {
                    /**
                     * Profile
                     */

                    Profile profile = new Profile(
                            "Juan@gmail.com",
                            "JuanVelazquez",
                            "85000",
                            "",
                            "juanan_velazquez",
                            "@berkejuanj",
                            "123"
                    );
                    fitBaseAppDatabase.getUserDao().insert(profile);

                } else {

                }


            }


            return true;

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Intent intent = null;
            intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }


}
