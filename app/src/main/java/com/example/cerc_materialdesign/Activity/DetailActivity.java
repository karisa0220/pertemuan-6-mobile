package com.example.cerc_materialdesign.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cerc_materialdesign.Model.ItemModel;
import com.example.cerc_materialdesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class DetailActivity extends AppCompatActivity
        implements EventListener<DocumentSnapshot> {

    public static final String KEY_PRODUCT_ID = "key_product_id";
    private static final String TAG = "Detail Activity";

    protected String [] favorite;

    private FirebaseFirestore mFirestore;
    private DocumentReference mProdukRef;
    private DocumentReference mWishlistRef;
    private FirebaseAuth mAuth;
    private ListenerRegistration mProductRegistration;
    private String userId;

    private ImageButton addFavorite;

    private TextView tv_nama, tv_gaji, tv_desc, tv_req1, tv_req2, tv_req3, tv_req4, tv_req5, tv_req6, tv_req7, tv_req8, tv_req9, tv_req10;
    private String image, productId;
    private ImageView iv_image;
    private Button btn_share;

    boolean isWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_nama = findViewById(R.id.tv_nama);
        tv_gaji= findViewById(R.id.tv_price);
        tv_desc = findViewById(R.id.tv_desc);
        iv_image = findViewById(R.id.iv_image);
        tv_req1 = findViewById(R.id.tv_req1);
        tv_req2 = findViewById(R.id.tv_req2);
        tv_req3 = findViewById(R.id.tv_req3);
        tv_req4 = findViewById(R.id.tv_req4);
        tv_req5 = findViewById(R.id.tv_req5);
        tv_req6 = findViewById(R.id.tv_req6);
        tv_req7 = findViewById(R.id.tv_req7);
        tv_req8 = findViewById(R.id.tv_req8);
        tv_req9 = findViewById(R.id.tv_req9);
        tv_req10 = findViewById(R.id.tv_req10);
        btn_share = findViewById(R.id.btn_share);
        addFavorite = findViewById(R.id.add_favorite);

        productId = getIntent().getExtras().getString(KEY_PRODUCT_ID);
        if (productId == null) {
            throw new IllegalArgumentException("Must pass extra " + KEY_PRODUCT_ID);
        }

        // Initialize Firestore
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        // Get reference to the db
        mProdukRef = mFirestore.collection("Item")
                .document(productId);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        mProductRegistration = mProdukRef.addSnapshotListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mProductRegistration != null) {
            mProductRegistration.remove();
            mProductRegistration = null;
        }
    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {

        if (e != null) {
            Log.w(TAG, "product:onEvent", e);
            return;
        }

        onProductLoaded(Objects.requireNonNull(Objects.requireNonNull(snapshot).toObject(ItemModel.class)));
    }


    private void onProductLoaded(final ItemModel product) {
        if (getIntent().hasExtra("no-Button")) {
            btn_share.setVisibility(View.INVISIBLE);
        }

        tv_nama.setText(product.getName());
        tv_gaji.setText(product.getGaji());
        tv_desc.setText(product.getDesc());
        tv_req1.setText(product.getReq1());
        tv_req2.setText(product.getReq2());
        tv_req3.setText(product.getReq3());
        tv_req4.setText(product.getReq4());
        tv_req5.setText(product.getReq5());
        tv_req6.setText(product.getReq6());
        tv_req7.setText(product.getReq7());
        tv_req8.setText(product.getReq8());
        tv_req9.setText(product.getReq9());
        tv_req10.setText(product.getReq10());

        Glide.with(this)
                .asBitmap()
                .load(product.getImage())
                .into(iv_image);

        wishListState();



        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this, "Share Success",
                    Toast.LENGTH_LONG).show();

            }
    });

        addFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWishlist) {
                    mWishlistRef.delete();
                    Toast.makeText(DetailActivity.this, "Product with ID" + productId + "Deleted From Favorite",
                            Toast.LENGTH_LONG).show();

                } else {
                    Map<String, Object> userFavorite = new HashMap<>();
                    userFavorite.put("name", product.getName());
                    userFavorite.put("category", product.getCategory());
                    userFavorite.put("gaji", product.getGaji());
                    userFavorite.put("image", product.getImage());
                    userFavorite.put("userId", userId);
                    mWishlistRef.set(userFavorite).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(DetailActivity.this, "Successfully Add To Favorite",
                                    Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DetailActivity.this, "Failed Add To Favorite",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
                isWishlist = !isWishlist;
                wishListState();
            }
        });

    }

    private void wishListState() {

        mWishlistRef = mFirestore.collection("Favorite").document(userId + productId);
        mWishlistRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        addFavorite.setBackgroundResource(R.drawable.ic_love);
                        isWishlist = true;
                    } else {
                        Log.d(TAG, "Document does not exist!");
                        addFavorite.setBackgroundResource(R.drawable.ic_default_love);
                        isWishlist = false;
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }
}
