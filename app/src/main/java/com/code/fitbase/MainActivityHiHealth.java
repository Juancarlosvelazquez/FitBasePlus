package com.code.fitbase;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.code.fitbase.databinding.ActivityMainBinding;
import com.code.fitbase.model.DailyCaloriesData;
import com.code.fitbase.model.DailyStepsData;



import java.util.Date;

import static com.code.fitbase.util.Utils.nDaysAgo;

public class MainActivityHiHealth extends AppCompatActivity {

    private MainViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupOnClickListeners();

        setupAuthObservers();
        setupQueryObservers();
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

        viewModel.getStepSum().observe(this, stepsData -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (DailyStepsData datum : stepsData) {
                stringBuilder
                        .append(getString(R.string.steps_daily_sums,
                                datum.getDate(),
                                datum.getSteps()))
                        .append("\n");
            }
            binding.queryResults.setText(stringBuilder.toString());
        });

        viewModel.getCaloriesSum().observe(this, caloriesDailyData -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (DailyCaloriesData datum : caloriesDailyData) {
                stringBuilder
                        .append(getString(R.string.calories_daily_sums,
                                datum.getDate(),
                                datum.getCalories()))
                        .append("\n");
            }
            binding.queryResults.setText(stringBuilder.toString());
        });
    }

    private void setupOnClickListeners() {
        binding.getDailyStepsData.setOnClickListener(v -> {
            cleanQueryResults();
            viewModel.fetchStepSum(getQuerySinceDate(), getQueryTillDate());
        });

        binding.geCaloriesData.setOnClickListener(v -> {
            cleanQueryResults();
            viewModel.fetchCaloriesSum(getQuerySinceDate(), getQueryTillDate());
        });

    }

    private void cleanQueryResults() {
        binding.queryResults.setText(null);
    }

    private Date getQuerySinceDate() {
        return nDaysAgo(binding.querySince);
    }

    private Date getQueryTillDate() {
        return nDaysAgo(binding.queryTill);
    }

    void askForPermissions() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permissions_dialog_title)
                .setMessage(R.string.permissions_dialog_message)
                .setPositiveButton(R.string.settings, (dialog, which) -> viewModel.requestAuthorization())
                .setNegativeButton(android.R.string.no, (dialog, which) -> finish())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
