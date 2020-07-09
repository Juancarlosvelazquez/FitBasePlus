package com.code.fitbase;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.code.fitbase.challenges.ChallengesFragment;
import com.code.fitbase.room.FitBaseAppDatabase;
import com.code.fitbase.room.profile.Profile;
import com.code.fitbase.workouts.WorkoutsFragment;

import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements PointsFragment.OnPointsChallengesFragmentInteractionListener, ProfileFragment.OnProfileFragmentInteractionListener, ChallengesFragment.OnChallengesFragmentInteractionListener, WorkoutsFragment.OnFragmentInteractionListenerWorkOut {

    @BindView(R.id.profileImage)
    ImageView profileImage;
    @BindView(R.id.challenges_label)
    TextView challengesLabel;
    @BindView(R.id.points_label)
    TextView pointsLabel;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    ProfileFragment profileFragment = new ProfileFragment();
    ChallengesFragment challengesFragment = new ChallengesFragment();
    PointsFragment pointsFragment = new PointsFragment();

    public static String ImageFIle = "";
    public static String userCurrentPoints = "0";

    public Stack<Fragment> fragmentStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        ButterKnife.bind(this);

        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new updateData().execute();
    }

    private class updateData extends AsyncTask<Void,Void,Void>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            FitBaseAppDatabase fitBaseAppDatabase = FitBaseAppDatabase.getDatabase(MainActivity.this);
            List<Profile> list = fitBaseAppDatabase.getUserDao().getAll();
            MainActivity.ImageFIle = list.get(0).getProfilePicture();
            MainActivity.userCurrentPoints = list.get(0).getAccumulatedPoints();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setVisibility(View.GONE);
            addChallengesFragment();



            Glide.with(MainActivity.this)
                    .load(ImageFIle)
                    .circleCrop()
                    .placeholder(R.drawable.user)
                    .into(profileImage);

            pointsLabel.setText(MainActivity.userCurrentPoints);
        }

    }


    @OnClick({R.id.profileImage, R.id.challenges_label, R.id.points_label})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profileImage:
                addProfileFragment();
                break;
            case R.id.challenges_label:
                addChallengesFragment();
                break;
            case R.id.points_label:
                addPointsFragment();
                break;
        }
    }

    private void addProfileFragment(){
        profileFragment = new ProfileFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, profileFragment);
        ft.commit();
    }

    private void addPointsFragment(){
        pointsFragment = new PointsFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, new PointsFragment());
        ft.commit();
    }

    private void addChallengesFragment(){
        challengesFragment = new ChallengesFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, new ChallengesFragment());
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        if (challengesFragment!=null) {
            challengesFragment.onActivityResult(requestCode, resultCode, data);
        }
        if (profileFragment!=null) {
            profileFragment.onActivityResult(requestCode, resultCode, data);
        }
        if (pointsFragment!=null) {
            pointsFragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onChallengesFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onProfileFragmentInteraction(Uri uri) {

    }

    @Override
    public void onProfileImageChange(String image) {
        Glide.with(this)
                .load(image)
                .circleCrop()
                .placeholder(R.drawable.user)
                .into(profileImage);
    }

    @Override
    public void onFragmentInteractionWorkOut(String uri) {
        pointsLabel.setText(uri);
    }
}
