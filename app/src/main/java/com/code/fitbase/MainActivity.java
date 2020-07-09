//package com.code.fitbase;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.bumptech.glide.Glide;
//import com.code.fitbase.challenges.ChallengesFragment;
//import com.code.fitbase.room.FitBaseAppDatabase;
//import com.code.fitbase.room.profile.Profile;
//import com.code.fitbase.workouts.WorkoutsFragment;
//
//import com.code.fitbase.databinding.ActivityMainBinding;
//import com.code.fitbase.databinding.FragmentWorkoutsBinding;
//import com.code.fitbase.model.DailyCaloriesData;
//import com.code.fitbase.model.DailyStepsData;
//
//import java.util.List;
//import java.util.Stack;
//import java.util.Date;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//import static com.code.fitbase.util.Utils.nDaysAgo;
//
//public class MainActivity extends AppCompatActivity implements PointsFragment.OnPointsChallengesFragmentInteractionListener, ProfileFragment.OnProfileFragmentInteractionListener, ChallengesFragment.OnChallengesFragmentInteractionListener, WorkoutsFragment.OnFragmentInteractionListenerWorkOut {
//
//    private MainViewModel viewModel;
//    private ActivityMainBinding binding;
//    private FragmentWorkoutsBinding binding2;
//
//    @BindView(R.id.profileImage)
//    ImageView profileImage;
//    @BindView(R.id.challenges_label)
//    TextView challengesLabel;
//    @BindView(R.id.points_label)
//    TextView pointsLabel;
//    @BindView(R.id.frameLayout)
//    FrameLayout frameLayout;
//
//    @BindView(R.id.progress)
//    ProgressBar progressBar;
//
//    ProfileFragment profileFragment = new ProfileFragment();
//    ChallengesFragment challengesFragment = new ChallengesFragment();
//    PointsFragment pointsFragment = new PointsFragment();
//
//    public static String ImageFIle = "";
//    public static String userCurrentPoints = "0";
//
//    public Stack<Fragment> fragmentStack;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        binding2 = FragmentWorkoutsBinding.inflate(getLayoutInflater());
//        setContentView(binding2.getRoot());
//        setContentView(R.layout.activity_test_main);
//        ButterKnife.bind(this);
//
//        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
//
//        setupOnClickListeners();
//
//        setupAuthObservers();
//        setupQueryObservers();
//
//        try {
//            getSupportActionBar().hide();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        new updateData().execute();
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        viewModel.checkAuthorizationStatus();
//    }
//
//    private void setupAuthObservers() {
//        viewModel.getAuthStatus().observe(this, permissionStatus -> {
//            switch (permissionStatus) {
//                case REQUEST_AUTHORIZATION:
//                case PERMISSION_NOT_GRANTED:
//                    askForPermissions();
//                    break;
//                case GOT_PERMISSION:
//                    viewModel.fetchHealthInfo();
//                    break;
//                default:
//                    finish();
//                    break;
//            }
//        });
//    }
//
//    private void setupQueryObservers() {
//
//        viewModel.getHeartRate().observe(this, value -> binding.heartRate.setText(getString(R.string.heart_rate, value.getHeartBitRate())));
//        viewModel.getHeartRate().observe(this, value -> binding2.heartRate.setText(getString(R.string.heart_rate, value.getHeartBitRate())));
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
//    }
//
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
//
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
//
//    void askForPermissions() {
//        new AlertDialog.Builder(this)
//                .setTitle(R.string.permissions_dialog_title)
//                .setMessage(R.string.permissions_dialog_message)
//                .setPositiveButton(R.string.settings, (dialog, which) -> viewModel.requestAuthorization())
//                .setNegativeButton(android.R.string.no, (dialog, which) -> finish())
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//    }
//
//    private class updateData extends AsyncTask<Void,Void,Void>{
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
//
//        }
//
//
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            FitBaseAppDatabase fitBaseAppDatabase = FitBaseAppDatabase.getDatabase(MainActivity.this);
//            List<Profile> list = fitBaseAppDatabase.getUserDao().getAll();
//            MainActivity.ImageFIle = list.get(0).getProfilePicture();
//            MainActivity.userCurrentPoints = list.get(0).getAccumulatedPoints();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//
//            progressBar.setVisibility(View.GONE);
//            addChallengesFragment();
//
//
//
//            Glide.with(MainActivity.this)
//                    .load(ImageFIle)
//                    .circleCrop()
//                    .placeholder(R.drawable.user)
//                    .into(profileImage);
//
//            pointsLabel.setText(MainActivity.userCurrentPoints);
//        }
//    }
//
//    @OnClick({R.id.profileImage, R.id.challenges_label, R.id.points_label})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.profileImage:
//                addProfileFragment();
//                break;
//            case R.id.challenges_label:
//                addChallengesFragment();
//                break;
//            case R.id.points_label:
//                addPointsFragment();
//                break;
//        }
//    }
//
//    private void addProfileFragment(){
//        profileFragment = new ProfileFragment();
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.frameLayout, profileFragment);
//        ft.commit();
//    }
//
//    private void addPointsFragment(){
//        pointsFragment = new PointsFragment();
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.frameLayout, new PointsFragment());
//        ft.commit();
//    }
//
//    private void addChallengesFragment(){
//        challengesFragment = new ChallengesFragment();
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.frameLayout, new ChallengesFragment());
//        ft.commit();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //
//        if (challengesFragment!=null) {
//            challengesFragment.onActivityResult(requestCode, resultCode, data);
//        }
//        if (profileFragment!=null) {
//            profileFragment.onActivityResult(requestCode, resultCode, data);
//        }
//        if (pointsFragment!=null) {
//            pointsFragment.onActivityResult(requestCode, resultCode, data);
//        }
//    }
//
//
//    @Override
//    public void onChallengesFragmentInteraction(Uri uri) {
//
//    }
//
//    @Override
//    public void onPointsFragmentInteraction(Uri uri) {
//
//    }
//
//    @Override
//    public void onProfileFragmentInteraction(Uri uri) {
//
//    }
//
//    @Override
//    public void onProfileImageChange(String image) {
//        Glide.with(this)
//                .load(image)
//                .circleCrop()
//                .placeholder(R.drawable.user)
//                .into(profileImage);
//    }
//
//    @Override
//    public void onFragmentInteractionWorkOut(String uri) {
//        pointsLabel.setText(uri);
//    }
//}
