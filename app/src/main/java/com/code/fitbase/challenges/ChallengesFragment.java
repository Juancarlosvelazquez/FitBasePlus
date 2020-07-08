package com.code.fitbase.challenges;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.allattentionhere.autoplayvideos.AAH_CustomRecyclerView;
import com.code.fitbase.IonWorkoutItemClicked;
import com.code.fitbase.R;
import com.code.fitbase.room.FitBaseAppDatabase;
import com.code.fitbase.room.challenges.Challenges;
import com.code.fitbase.room.products.Prizes;
import com.code.fitbase.workouts.WorkoutsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnChallengesFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChallengesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChallengesFragment extends Fragment implements IonWorkoutItemClicked {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.rv_home)
    com.allattentionhere.autoplayvideos.AAH_CustomRecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnChallengesFragmentInteractionListener mListener;

    private final List<MyModel> modelList = new ArrayList<>();
    private final List<Challenges> challengesList = new ArrayList<>();

    MyVideosAdapter mAdapter;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    public ChallengesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChallengesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChallengesFragment newInstance(String param1, String param2) {
        ChallengesFragment fragment = new ChallengesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



//        Challenges challenge1 = new Challenges(
//                "Juan",
//                "Plank + knee jumps",
//                "2000",
//                "10",
//                "120",
//                "https://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1481795676/4_nvnzry.mp4");
//
//
//        Challenges challenge2 = new Challenges(
//                "Calos",
//                "Push ups",
//                "2000",
//                "15",
//                "40",
//                "https://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70/v1491561340/hello_cuwgcb.mp4");
//
//
//
//        Challenges challenge3 = new Challenges(
//                "Velaz",
//                "Flying",
//                "4000",
//                "25",
//                "60",
//                "https://firebasestorage.googleapis.com/v0/b/flickering-heat-5334.appspot.com/o/demo1.mp4?alt=media&token=f6d82bb0-f61f-45bc-ab13-16970c7432c4");
//
//
//        challengesList.add(challenge1);
//        challengesList.add(challenge2);
//        challengesList.add(challenge3);


    }

    public class getChallengesDataFromDatabase extends AsyncTask<Void, Void, Boolean> {

        FitBaseAppDatabase fitBaseAppDatabase;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            fitBaseAppDatabase = FitBaseAppDatabase.getDatabase(getActivity());
            if (fitBaseAppDatabase != null) {
                List<Challenges> list = fitBaseAppDatabase.getChallengesDao().getAll();
                challengesList.addAll(list);
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            setDataAdapter();
            progressBar.setVisibility(View.GONE);
        }
    }


    public void setDataAdapter(){

        Picasso p = Picasso.with(getActivity());
        mAdapter = new MyVideosAdapter(getActivity() ,challengesList, p);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        mAdapter.setIonWorkoutItemClicked(this);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //todo before setAdapter
        recyclerView.setActivity(getActivity());

        //optional - to play only first visible video
        recyclerView.setPlayOnlyFirstVideo(false); // false by default



        //optional - by default we check if url ends with ".mp4". If your urls do not end with mp4, you can set this param to false and implement your own check to see if video points to url
        recyclerView.setCheckForMp4(false); //true by default

        //optional - download videos to local storage (requires "android.permission.WRITE_EXTERNAL_STORAGE" in manifest or ask in runtime)
        //recyclerView.setDownloadPath(Environment.getExternalStorageDirectory() + "/MyVideo"); // (Environment.getExternalStorageDirectory() + "/Video") by default

        // recyclerView.setDownloadVideos(true); // false by default

        recyclerView.setVisiblePercent(50); // percentage of View that needs to be visible to start playing

//        //extra - start downloading all videos in background before loading RecyclerView
//        List<String> urls = new ArrayList<>();
//        for (MyModel object : modelList) {
//            if (object.getVideo_url() != null && object.getVideo_url().contains("http"))
//                urls.add(object.getVideo_url());
//        }
//        recyclerView.preDownload(urls);

        recyclerView.setAdapter(mAdapter);
        //call this functions when u want to start autoplay on loading async lists (eg firebase)
        recyclerView.smoothScrollBy(0,1);
        recyclerView.smoothScrollBy(0,-1);


        recyclerView.playAvailableVideos(0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_challenges, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new getChallengesDataFromDatabase().execute();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onChallengesFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChallengesFragmentInteractionListener) {
            mListener = (OnChallengesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPointsChallengesFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        recyclerView.stopVideos();
    }

    @Override
    public void onWorkoutItemClicked(Challenges challenge) {

        WorkoutsFragment workoutsFragment = WorkoutsFragment.newInstance(challenge.getTimeInSeconds(),
                challenge.getRepetitions(),challenge.getPoints(),mAdapter.getAll(),challenge.getVideoFile());


        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, workoutsFragment);
        //ft.addToBackStack("workout");
        ft.commitAllowingStateLoss();

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
    public interface OnChallengesFragmentInteractionListener {
        // TODO: Update argument type and name
        void onChallengesFragmentInteraction(Uri uri);
    }
}
