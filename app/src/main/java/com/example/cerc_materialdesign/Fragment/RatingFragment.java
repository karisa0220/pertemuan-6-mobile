package com.example.cerc_materialdesign.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cerc_materialdesign.Activity.MainActivity;
import com.example.cerc_materialdesign.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RatingFragment extends Fragment {
    private DocumentReference mWishlistRef;
    private FirebaseFirestore mFirestore;
    private String userId;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;


    // Method onCreateView dipanggil saat Fragment harus menampilkan layoutnya      // dengan membuat layout tersebut secara manual lewat objek View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Layout tampilan untuk fragment ini
        View view = inflater.inflate(R.layout.fragment_rating, container, false);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        final RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        MaterialButton btnRating = (MaterialButton) view.findViewById(R.id.btnRating);
        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                String rate = Float.toString(rating);


                mWishlistRef = mFirestore.collection("Rating").document(userId);
                Map<String, Object> userRating = new HashMap<>();
                userRating.put("rating", rate);
                userRating.put("userId", userId);


                mWishlistRef.set(userRating).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        String rating = "Rating yang anda berikan adalah " + ratingBar.getRating() + "\nTerima kasih telah memberikan review pada mentor kami.";
                        Toast.makeText(getActivity(), rating, Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Maaf pemberian rating gagal", Toast.LENGTH_LONG).show();

                    }
                });


                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);

            }
        });

        return view;
    }


}
