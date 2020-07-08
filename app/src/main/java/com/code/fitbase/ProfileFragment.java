package com.code.fitbase;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.allattentionhere.autoplayvideos.AAH_CustomRecyclerView;
import com.bumptech.glide.Glide;
import com.code.fitbase.challenges.MyVideosAdapter;
import com.code.fitbase.room.FitBaseAppDatabase;
import com.code.fitbase.room.challenges.Challenges;
import com.code.fitbase.room.profile.Profile;
import com.squareup.picasso.Picasso;

import net.alhazmy13.mediapicker.Image.ImagePicker;
import net.alhazmy13.mediapicker.Video.VideoPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;



public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.label_work_out_name)
    TextView labelWorkOutName;
    @BindView(R.id.header_profile)
    LinearLayout headerProfile;
    @BindView(R.id.hear)
    ImageView hear;
    @BindView(R.id.insgram)
    ImageView insgram;
    @BindView(R.id.tiktok)
    ImageView tiktok;
    @BindView(R.id.layout_profile)
    LinearLayout layoutProfile;
    @BindView(R.id.label_layout_challenges)
    LinearLayout labelLayoutChallenges;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnProfileFragmentInteractionListener mListener;

    private final List<Challenges> challengesList = new ArrayList<>();

    @BindView(R.id.rv_home)
    AAH_CustomRecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;




    @OnClick(R.id.hear)
    public void onPersonClicked() {
        new ImagePicker.Builder(getActivity())
                .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                .directory(ImagePicker.Directory.DEFAULT)
                .extension(ImagePicker.Extension.PNG)
                .scale(600, 600)
                .allowMultipleImages(false)
                .enableDebuggingMode(true)
                .build();
    }


    @OnClick(R.id.floatingActionButton)
    public void onPlusClick() {

        new VideoPicker.Builder(getActivity())
                .mode(VideoPicker.Mode.CAMERA_AND_GALLERY)
                .directory(VideoPicker.Directory.DEFAULT)
                .extension(VideoPicker.Extension.MP4)
                .enableDebuggingMode(true)
                .build();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
                List<String> mPaths = data.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH);
                //Your Code
                String a = "";
            }
            if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
                List<String> mPaths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
                MainActivity.ImageFIle = mPaths.get(0);
                //Your Code
                Glide.with(getActivity())
                        .load(mPaths.get(0))
                        .circleCrop()
                        .placeholder(R.drawable.user_two)
                        .into(hear);

                new updateProfile().execute();
                if (mListener != null) {
                    mListener.onProfileImageChange(MainActivity.ImageFIle);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class updateProfile extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }


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
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(),"Profile updated",Toast.LENGTH_SHORT).show();
        }
    }


//    private void loadVideo() {
//        if (mPath != null && mPath.size() > 0) {
//            path.setText(mPath.get(0));
//            videoView.setVideoURI(Uri.parse(mPath.get(0)));
//            videoView.start();
//        }
//    }


    @OnClick(R.id.insgram)
    public void onInstaClick() {

        Uri uri = Uri.parse("http://instagram.com/_u/" + "Juan");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/" + "Juan")));
        }
    }


    @OnClick(R.id.tiktok)
    public void titkClicked() {
        Intent i;
        PackageManager manager = getActivity().getPackageManager();
        try {
            i = manager.getLaunchIntentForPackage("com.zhiliaoapp.musically");
            if (i == null)
                throw new PackageManager.NameNotFoundException();
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {

        }

    }


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
                List<Profile> profiles = (List<Profile>) fitBaseAppDatabase.getUserDao().getAll();
                MainActivity.ImageFIle = profiles.get(0).getProfilePicture();
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

    public void setDataAdapter() {
        try {
            Glide.with(getActivity())
                    .load(MainActivity.ImageFIle)
                    .circleCrop()
                    .placeholder(R.drawable.user_two)
                    .into(hear);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Picasso p = Picasso.with(getActivity());
        MyVideosAdapter mAdapter = new MyVideosAdapter(getActivity(), challengesList, p);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

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
        recyclerView.smoothScrollBy(0, 1);
        recyclerView.smoothScrollBy(0, -1);


        recyclerView.playAvailableVideos(0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
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
            mListener.onProfileFragmentInteraction(uri);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProfileFragmentInteractionListener) {
            mListener = (OnProfileFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPointsChallengesFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnProfileFragmentInteractionListener {
        // TODO: Update argument type and name
        void onProfileFragmentInteraction(Uri uri);

        void onProfileImageChange(String image);
    }
}
