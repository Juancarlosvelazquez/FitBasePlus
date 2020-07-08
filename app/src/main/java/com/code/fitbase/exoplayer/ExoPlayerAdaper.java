package com.code.fitbase.exoplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.code.fitbase.IOnProductItemClicked;
import com.code.fitbase.Products.ProductsAdapter;
import com.code.fitbase.R;
import com.code.fitbase.room.products.Prizes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExoPlayerAdaper extends RecyclerView.Adapter<ExoPlayerAdaper.ViewHolder> {

    private List<Prizes> listPrizes;
    Context context;
    IOnProductItemClicked iOnProductItemClicked;
    private int[] images = {
            R.drawable.tshirt,
            R.drawable.waterbottle,
            R.drawable.sportband,
    };

    public void setProductListener(IOnProductItemClicked iOnProductItemClicked){
        this.iOnProductItemClicked =iOnProductItemClicked;
    }


    // RecyclerView recyclerView;
    public ExoPlayerAdaper(Context context, List<Prizes> listPrizes) {
        this.listPrizes = listPrizes;
        this.context = context;
    }

    @Override
    public ExoPlayerAdaper.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout_product_item, parent, false);
        ExoPlayerAdaper.ViewHolder viewHolder = new ExoPlayerAdaper.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ExoPlayerAdaper.ViewHolder holder, int position) {
        final Prizes prize = listPrizes.get(position);

        if (prize != null) {


            holder.pricePoint.setText(prize.getPointsNeeded());
            holder.productName.setText(prize.getProductName());
            holder.productBy.setText("Brought by: " + prize.getProductOwner());
            holder.availability.setText(prize.getAvailability());
            holder.expirationDate.setText(prize.getExpirationDate());
            holder.productImage.setImageResource(images[position]);

//            Glide.with(context)
//                    .load(prize.getProductImage())
//                    .placeholder(R.drawable.user)
//                    .into(holder.productImage);

//            Glide.with(context).load(prize.getProductImage()).into(new CustomTarget<Drawable>() {
//                @Override
//                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                    //holder.imgIcon.setImageDrawable(resource);
//                    holder.productImage.setImageDrawable(resource);
//                }
//
//                @Override
//                public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                }
//            });


            //Picasso.with(context).load(prize.getProductImage()).into(holder.productImage);



            holder.idMainContainerProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iOnProductItemClicked.onProductItemClicked(prize);
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return listPrizes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.productImage)
        ImageView productImage;
        @BindView(R.id.pricePoint)
        TextView pricePoint;
        @BindView(R.id.productName)
        TextView productName;
        @BindView(R.id.productBy)
        TextView productBy;
        @BindView(R.id.expirationDate)
        TextView expirationDate;
        @BindView(R.id.availability)
        TextView availability;
        @BindView(R.id.id_main_container_product)
        RelativeLayout idMainContainerProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
