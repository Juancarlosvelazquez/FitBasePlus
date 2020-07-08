package com.code.fitbase;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.code.fitbase.Products.ProductsAdapter;
import com.code.fitbase.room.FitBaseAppDatabase;
import com.code.fitbase.room.products.Prizes;
import com.code.fitbase.room.profile.Profile;
import com.code.fitbase.workouts.WorkoutsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class PointsFragment extends Fragment implements IOnProductItemClicked{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.label_work_out_name)
    TextView labelWorkOutName;
    @BindView(R.id.header_trade_points)
    LinearLayout headerTradePoints;
    @BindView(R.id.listProducts)
    RecyclerView listProducts;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    private List<Prizes> prizesList = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnPointsChallengesFragmentInteractionListener mListener;

    public PointsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PointsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PointsFragment newInstance(String param1, String param2) {
        PointsFragment fragment = new PointsFragment();
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

//        Prizes prize1 = new Prizes(
//                "123",
//                "2/6/2020",
//                "20/6/2020",
//                "Addidas",
//                "T-Shirt Casual",
//                "https://pasteboard.co/JfH8qxj.png",
//                "20000,000000",
//                "15"
//        );
//
//        Prizes prize2 = new Prizes(
//                "156",
//                "2/6/2020",
//                "25/6/2020",
//                "Nike",
//                "Sports Bottle",
//                "https://ibb.co/GJpVzvF",
//                "10000",
//                "25"
//        );
//
//        Prizes prize3 = new Prizes(
//                "9977",
//                "2/6/2020",
//                "29/6/2020",
//                "Apple",
//                "I Pod",
//                "https://ibb.co/GJpVzvF",
//                "90000,0000000",
//                "5"
//        );
//
//        prizesList.add(prize1);
//        prizesList.add(prize2);
//        prizesList.add(prize3);



    }

    public class getProductDataFromDataBase extends AsyncTask<Void, Void, Boolean> {

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
                List<Prizes> list = fitBaseAppDatabase.getPrizesDao().getAll();
                prizesList.addAll(list);
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

    private void setDataAdapter(){
        ProductsAdapter productsAdapter = new ProductsAdapter(getActivity(),prizesList);
        productsAdapter.setProductListener(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listProducts.setLayoutManager(mLayoutManager);
        listProducts.setItemAnimator(new DefaultItemAnimator());
        listProducts.setAdapter(productsAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_points, container, false);
        ButterKnife.bind(this, view);

        return view;
        //return inflater.inflate(R.layout.fragment_points, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        new getProductDataFromDataBase().execute();


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPointsFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPointsChallengesFragmentInteractionListener) {
            mListener = (OnPointsChallengesFragmentInteractionListener) context;
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

    @Override
    public void onProductItemClicked(Prizes prizes) {
        if (prizes!=null){
            Long number = Long.parseLong(prizes.getPointsNeeded().replace(",",""));
            Long currentNumber = Long.parseLong(MainActivity.userCurrentPoints);
            if (currentNumber<number){
                Toast.makeText(getActivity(),"Your Points are too low to trade",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(),"Congratulation's this is product code "+ prizes.getCode(),Toast.LENGTH_SHORT).show();
//                long sum = number - currentNumber;
//                MainActivity.userCurrentPoints = String.valueOf(sum);
//
//                new updateProfile().execute();
            }
        }
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
    public interface OnPointsChallengesFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPointsFragmentInteraction(Uri uri);
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

    }
}
