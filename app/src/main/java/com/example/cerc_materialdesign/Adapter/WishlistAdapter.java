package com.example.cerc_materialdesign.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cerc_materialdesign.Model.ItemModel;
import com.example.cerc_materialdesign.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;


public class WishlistAdapter extends FirestoreAdapter<WishlistAdapter.ViewHolder> {

    public interface OnProductSelectedListener {

        void onProductSelected(DocumentSnapshot productModel);
        void onDeleteSelected(DocumentSnapshot productModel);

    }

    private OnProductSelectedListener mListener;

    public WishlistAdapter(Query query, OnProductSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_wishlist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView wishlist_img;
        private TextView tvNama;
        private TextView tvPrice;
        private ImageButton btn_wishlist;


        public ViewHolder(View itemView) {
            super(itemView);
            wishlist_img = itemView.findViewById(R.id.wishlist_img);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvPrice = itemView.findViewById(R.id.tv_price);
            btn_wishlist = itemView.findViewById(R.id.btn_wishlist);
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnProductSelectedListener listener) {

            ItemModel productModel = snapshot.toObject(ItemModel.class);


            // Load image
            Glide.with(wishlist_img.getContext())
                    .load(productModel.getImage())
                    .into(wishlist_img);

            tvNama.setText(productModel.getName());
            tvPrice.setText(productModel.getGaji());
            btn_wishlist.setBackgroundResource(R.drawable.ic_love);


            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onProductSelected(snapshot);
                    }
                }
            });

            btn_wishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onDeleteSelected(snapshot);
                    }
                }
            });
        }

    }
}