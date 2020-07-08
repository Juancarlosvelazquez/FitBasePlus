package com.code.fitbase.challenges;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.allattentionhere.autoplayvideos.AAH_CustomViewHolder;
import com.allattentionhere.autoplayvideos.AAH_VideosAdapter;
import com.code.fitbase.IonWorkoutItemClicked;
import com.code.fitbase.R;
import com.code.fitbase.room.challenges.Challenges;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyVideosAdapter extends AAH_VideosAdapter {

    private final List<Challenges> challengesList;
    private final Picasso picasso;
    private static final int TYPE_VIDEO = 0, TYPE_TEXT = 1;

    Context context;
    private IonWorkoutItemClicked ionWorkoutItemClicked;

    public void setIonWorkoutItemClicked(IonWorkoutItemClicked ionWorkoutItemClicked){
        this.ionWorkoutItemClicked = ionWorkoutItemClicked;
    }

    public ArrayList<Challenges> getAll(){
        ArrayList<Challenges> challengesArrayList = new ArrayList<>();

        for (int i =0; i<challengesList.size();i++){
            challengesArrayList.add(challengesList.get(i));
        }

        return challengesArrayList;
    }


    public class MyViewHolder extends AAH_CustomViewHolder {
        // final TextView tv;
        //final ImageView img_vol, img_playback;
        //to mute/un-mute video (optional)
        boolean isMuted;

        //public @BindView(R.id.tv)
        // tv;
        public @BindView(R.id.img_vol)
        ImageView img_vol;
        public @BindView(R.id.img_playback)
        ImageView img_playback;


        public @BindView(R.id.video_layout)
        FrameLayout videoLayout;
        public @BindView(R.id.hear)
        ImageView hear;
        public @BindView(R.id.insgram)
        ImageView insgram;
        public @BindView(R.id.tiktok)
        ImageView tiktok;
        public @BindView(R.id.label_nominatedNy)
        TextView labelNominatedNy;
        public @BindView(R.id.label_work_out_name)
        TextView labelWorkOutName;
        public @BindView(R.id.label_work_out_points)
        TextView labelWorkOutPoints;
        public @BindView(R.id.label_work_out_repetitaions)
        TextView labelWorkOutRepetitaions;
        public @BindView(R.id.card_view)
        androidx.cardview.widget.CardView cardView;


        public MyViewHolder(View x) {
            super(x);
            ButterKnife.bind(this, x);
            //tv = ButterKnife.(x, R.id.tv);
            //img_vol = ButterKnife.findById(x, R.id.img_vol);
            //img_playback = ButterKnife.findById(x, R.id.img_playback);
        }

        //override this method to get callback when video starts to play
        @Override
        public void videoStarted() {
            super.videoStarted();
            img_playback.setImageResource(R.drawable.ic_play);
        }

        @Override
        public void pauseVideo() {
            super.pauseVideo();
            img_playback.setImageResource(R.drawable.ic_play);
        }
    }


//    public class MyTextViewHolder extends AAH_CustomViewHolder {
//        final TextView tv;
//
//        public MyTextViewHolder(View x) {
//            super(x);
//            tv = ButterKnife.findById(x, R.id.tv);
//        }
//    }

    public MyVideosAdapter(Context context, List<Challenges> challengesList, Picasso p) {
        this.challengesList = challengesList;
        this.picasso = p;
        this.context = context;
    }

    @Override
    public AAH_CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == TYPE_TEXT) {
//            View itemView1 = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.single_text, parent, false);
//            return new MyTextViewHolder(itemView1);
//        } else {
//            View itemView = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.single_card, parent, false);
//            return new MyViewHolder(itemView);
//        }

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_workout_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final AAH_CustomViewHolder holder, int position) {
//        if (challengesList.get(position).getName().startsWith("text")) {
//            ((MyTextViewHolder) holder).tv.setText(challengesList.get(position).getName());
//        } else {
        //((MyViewHolder) holder).tv.setText(challengesList.get(position).getName());

        //todo
        //holder.setImageUrl(challengesList.get(position).getImage_url());
        holder.setVideoUrl(challengesList.get(position).getVideoFile());

        ((MyViewHolder) holder).labelNominatedNy.setText("Nominated by: \n"+challengesList.get(position).getUserName());
        ((MyViewHolder) holder).labelWorkOutName.setText(challengesList.get(position).getWorkOutName());
        ((MyViewHolder) holder).labelWorkOutPoints.setText(challengesList.get(position).getPoints()+" Points");
        ((MyViewHolder) holder).labelWorkOutRepetitaions.setText(challengesList.get(position).getRepetitions() + " \nRepetitions");



        ((MyViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ionWorkoutItemClicked.onWorkoutItemClicked(challengesList.get(position));
            }
        });




        ((MyViewHolder) holder).insgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://instagram.com/_u/"+challengesList.get(position).getUserName());
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    context.startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/"+challengesList.get(position).getUserName())));
                }
            }
        });


        ((MyViewHolder) holder).tiktok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                PackageManager manager = context.getPackageManager();
                try {
                    i = manager.getLaunchIntentForPackage("com.zhiliaoapp.musically");
                    if (i == null)
                        throw new PackageManager.NameNotFoundException();
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    context.startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {

                }
            }
        });



        holder.playVideo();

        //load image into imageview
//        if (challengesList.get(position).getImage_url() != null && !challengesList.get(position).getImage_url().isEmpty()) {
//            picasso.load(holder.getImageUrl()).config(Bitmap.Config.RGB_565).into(holder.getAAH_ImageView());
//        }

        holder.setLooping(true); //optional - true by default

        //to play pause videos manually (optional)
        ((MyViewHolder) holder).img_playback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isPlaying()) {
                    holder.pauseVideo();
                    holder.setPaused(true);
                } else {
                    holder.playVideo();
                    holder.setPaused(false);
                }
            }
        });

        //to mute/un-mute video (optional)
        ((MyViewHolder) holder).img_vol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MyViewHolder) holder).isMuted) {
                    holder.unmuteVideo();
                    ((MyViewHolder) holder).img_vol.setImageResource(R.drawable.ic_unmute);
                } else {
                    holder.muteVideo();
                    ((MyViewHolder) holder).img_vol.setImageResource(R.drawable.ic_mute);
                }
                ((MyViewHolder) holder).isMuted = !((MyViewHolder) holder).isMuted;
            }
        });

        if (challengesList.get(position).getVideoFile() == null) {
            ((MyViewHolder) holder).img_vol.setVisibility(View.GONE);
            ((MyViewHolder) holder).img_playback.setVisibility(View.GONE);
        } else {
            ((MyViewHolder) holder).img_vol.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).img_playback.setVisibility(View.VISIBLE);
        }
        //}
    }


    @Override
    public int getItemCount() {
        return challengesList.size();
    }


    @Override
    public int getItemViewType(int position) {
//        if (challengesList.get(position).getName().startsWith("text")) {
//            return TYPE_TEXT;
//        } else
        return TYPE_VIDEO;
    }

}

