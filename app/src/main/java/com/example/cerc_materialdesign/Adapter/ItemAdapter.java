package com.example.cerc_materialdesign.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cerc_materialdesign.Model.ItemModel;
import com.example.cerc_materialdesign.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class ItemAdapter extends FirestoreAdapter<ItemAdapter.ViewHolder> {

    public interface OnProductSelectedListener {

        void onProductSelected(DocumentSnapshot itemModel);

    }

    private OnProductSelectedListener mListener;

    public ItemAdapter(Query query, OnProductSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_discover, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView discover_img;
        private TextView title;
        private TextView subtitle;

        public ViewHolder(View itemView) {
            super(itemView);
            discover_img = itemView.findViewById(R.id.discover_img);
            title = itemView.findViewById(R.id.discover_title);
            subtitle = itemView.findViewById(R.id.discover_subtitle);
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnProductSelectedListener listener) {

            ItemModel itemModel = snapshot.toObject(ItemModel.class);

            // Load image
            Glide.with(discover_img.getContext())
                    .load(itemModel.getImage())
                    .into(discover_img);

            title.setText(itemModel.getName());
            subtitle.setText(itemModel.getGaji());

            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onProductSelected(snapshot);
                    }
                }
            });
        }

    }
}
