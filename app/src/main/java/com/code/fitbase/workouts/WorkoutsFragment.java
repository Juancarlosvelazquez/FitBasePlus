package com.code.fitbase.workouts;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.code.fitbase.MainActivity;
import com.code.fitbase.MainViewModel;
import com.code.fitbase.R;
import com.code.fitbase.databinding.ActivityMainBinding;
import com.code.fitbase.room.FitBaseAppDatabase;
import com.code.fitbase.room.challenges.Challenges;
import com.code.fitbase.room.profile.Profile;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.code.fitbase.databinding.FragmentWorkoutsBinding;
import com.code.fitbase.model.DailyCaloriesData;
import com.code.fitbase.model.DailyStepsData;



import java.util.Date;

import static com.code.fitbase.util.Utils.nDaysAgo;



public class WorkoutsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    @BindView(R.id.label_work_out_name)
    TextView labelWorkOutName;
    @BindView(R.id.header_work_out)
    LinearLayout headerWorkOut;
    @BindView(R.id.video_view)
    PlayerView videoView;
    @BindView(R.id.layout_video)
    LinearLayout layoutVideo;
    @BindView(R.id.clock)
    ImageView clock;
    @BindView(R.id.label_timer)
    TextView labelTimer;
    @BindView(R.id.timer_layout)
    LinearLayout timerLayout;
    @BindView(R.id.layout_timer_task)
    RelativeLayout layoutTimerTask;
    @BindView(R.id.label_repetitions_value)
    TextView labelRepetitionsValue;
    @BindView(R.id.label_repetitions)
    TextView labelRepetitions;
    @BindView(R.id.layout_repetitions)
    RelativeLayout layoutRepetitions;
    @BindView(R.id.buttonStart)
    Button buttonStart;
    @BindView(R.id.buttonNext)
    Button buttonNext;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    int index;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private MainViewModel viewModel;
    private FragmentWorkoutsBinding binding;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParamURL;
    private ArrayList<Challenges> challenges;
    private SimpleExoPlayer player;


    private OnFragmentInteractionListenerWorkOut mListener;

    public WorkoutsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutsFragment newInstance(String param1, String param2, String param3, ArrayList<Challenges> challenges, String url) {
        WorkoutsFragment fragment = new WorkoutsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM4, param3);
        args.putSerializable(ARG_PARAM3, (ArrayList<? extends Serializable>) challenges);
        args.putString(ARG_PARAM5, url);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentWorkoutsBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupOnClickListeners();

//        setupAuthObservers();
        setupQueryObservers();


        index = 0;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM4);

            mParamURL = getArguments().getString(ARG_PARAM5);

            challenges = (ArrayList<Challenges>) getArguments().getSerializable(ARG_PARAM3);
            String a = "";
            mTimeLeftInMillis = (1000 * Integer.parseInt(mParam1));

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.checkAuthorizationStatus();
    }

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

    private void setupQueryObservers() {

        viewModel.getHeartRate().observe(this, value -> binding.heartRate.setText(getString(R.string.heart_rate, value.getHeartBitRate())));

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

    private void setupOnClickListeners() {
//        binding.getDailyStepsData.setOnClickListener(v -> {
//            cleanQueryResults();
//            viewModel.fetchStepSum(getQuerySinceDate(), getQueryTillDate());
//        });
//
//        binding.geCaloriesData.setOnClickListener(v -> {
//            cleanQueryResults();
//            viewModel.fetchCaloriesSum(getQuerySinceDate(), getQueryTillDate());
//        });

    }

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

//    void askForPermissions() {
//        new AlertDialog.Builder(this)
//                .setTitle(R.string.permissions_dialog_title)
//                .setMessage(R.string.permissions_dialog_message)
//                .setPositiveButton(R.string.settings, (dialog, which) -> viewModel.requestAuthorization())
//                .setNegativeButton(android.R.string.no, (dialog, which) -> finish())
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//    }

    private void initializePlayer(String url) {
        player = ExoPlayerFactory.newSimpleInstance(getActivity());
        videoView.setPlayer(player);

        //player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        Uri uri = Uri.parse(url);
        // MediaSource mediaSource = buildMediaSource(uri);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), "exoplayer-codelab");
        ProgressiveMediaSource.Factory mediaSourceFactory = new ProgressiveMediaSource.Factory(dataSourceFactory);
        MediaSource mediaSource = mediaSourceFactory.createMediaSource(uri);
        player.prepare(mediaSource, false, false);
        videoView.onPause();

    }

//    private MediaSource buildMediaSource(Uri uri) {
//        // These factories are used to construct two media sources below
//        DataSource.Factory dataSourceFactory =
//                new DefaultDataSourceFactory(getActivity(), "exoplayer-codelab");
//        ProgressiveMediaSource.Factory mediaSourceFactory =
//                new ProgressiveMediaSource.Factory(dataSourceFactory);
//
//        // Create a media source using the supplied URI
//        MediaSource mediaSource1 = mediaSourceFactory.createMediaSource(uri);
//
//        // Additionally create a media source using an MP3
//        Uri audioUri = Uri.parse(getString(R.string.media_url_mp3));
//        MediaSource mediaSource2 = mediaSourceFactory.createMediaSource(audioUri);
//
//        return new ConcatenatingMediaSource(mediaSource1, mediaSource2);
//    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }

    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_workouts, container, false);

        View view = inflater.inflate(R.layout.fragment_workouts, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        labelRepetitionsValue.setText(mParam2);
        initializePlayer(mParamURL);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteractionWorkOut(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListenerWorkOut) {
            mListener = (OnFragmentInteractionListenerWorkOut) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick({R.id.buttonStart, R.id.buttonNext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonStart:
                startCounter();
                break;
            case R.id.buttonNext:
                buttonNextClicked();
                break;
        }
    }

    private void buttonNextClicked() {
        if (index < (challenges.size() - 1)) {
            index++;
            mParam3 = challenges.get(index).getPoints();
            mTimeLeftInMillis = (1000 * Integer.parseInt(challenges.get(index).getTimeInSeconds()));
            mParam2 = challenges.get(index).getRepetitions();
            mParamURL = challenges.get(index).getVideoFile();
            labelRepetitionsValue.setText(mParam2);
            if (mCountDownTimer != null) {
                mCountDownTimer.cancel();
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", 0, 0);
                labelTimer.setText(timeLeftFormatted);

            }
            //startCounter();
            initializePlayer(mParamURL);
        }
    }

    private void startCounter() {

        if (player != null) {
            player.setPlayWhenReady(true);
        }
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                long earnedPoints = Long.parseLong(mParam3);
                //Long number = Long.parseLong(prizes.getPointsNeeded().replace(",",""));
                Long currentNumber = Long.parseLong(MainActivity.userCurrentPoints);
                long sum = earnedPoints + currentNumber;
                MainActivity.userCurrentPoints = String.valueOf(sum);
                if (mListener != null) {
                    mListener.onFragmentInteractionWorkOut(MainActivity.userCurrentPoints);
//                    FitBaseAppDatabase fitBaseAppDatabase = FitBaseAppDatabase.getDatabase(getActivity());
//                    Profile profile = new Profile(
//                            "Juan@gmail.com",
//                            "Juan",
//                            MainActivity.userCurrentPoints,
//                            MainActivity.ImageFIle,
//                            "Juan",
//                            "xx",
//                            "123"
//                    );
//                    fitBaseAppDatabase.getUserDao().update(profile);

                    new updateProfile().execute();
                }


                // mButtonStartPause.setText("Start");
                // mButtonStartPause.setVisibility(View.INVISIBLE);
                //mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();
        //mTimerRunning = true;
        //mButtonStartPause.setText("pause");
        //mButtonReset.setVisibility(View.INVISIBLE);
    }

    private class updateProfile extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            FitBaseAppDatabase fitBaseAppDatabase = FitBaseAppDatabase.getDatabase(getActivity());
            Profile profile = new Profile(
                    "Juan@gmail.com",
                    "Juan",
                    MainActivity.userCurrentPoints,
                    MainActivity.ImageFIle,
                    "Juan",
                    "xx",
                    "123"
            );
            fitBaseAppDatabase.getUserDao().update(profile);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getActivity(),"Points updated and progress saved",Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        labelTimer.setText(timeLeftFormatted);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListenerWorkOut {
        // TODO: Update argument type and name
        void onFragmentInteractionWorkOut(String uri);
    }
}
