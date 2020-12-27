package com.example.cerc_materialdesign.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cerc_materialdesign.Adapter.ItemAdapter;
import com.example.cerc_materialdesign.Model.CategoryModel;
import com.example.cerc_materialdesign.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CategoryActivity extends AppCompatActivity {
 //       implements EventListener<DocumentSnapshot> {
    private static final String TAG = "CatalogActivity";
    public static final String KEY_PRODUCT_CATEGORY = "key_product_category";
    public static final String KEY_SEARCH_QUERY = "key_search_query";


    String cover;
    private View view;
    private TextView tvSortby;
    private Button btn_cancel, btn_confirm;

    private RecyclerView catalog_recyclerview;


    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private Query mQuery;

    private ItemAdapter mAdapter;
    private String productCategory, searchQuery ;
    private ImageView imgEmptySearch;
    private TextView tvTitle, textEmptySearch, textEmptySearch2;

    private TextView tv_nama, tv_desc, tv_req1, tv_req2, tv_req3, tv_req4, tv_req5, tv_req6, tv_req7, tv_req8, tv_req9, tv_req10;
    private String image, productId;
    private ImageView iv_image;
    private Button btn_disc;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productCategory = getIntent().getExtras().getString(KEY_PRODUCT_CATEGORY);
        searchQuery = getIntent().getExtras().getString(KEY_SEARCH_QUERY);

        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(productCategory);
//        textEmptySearch = findViewById(R.id.textEmptySearch);
//        textEmptySearch2 = findViewById(R.id.textEmptySearch2);
//        imgEmptySearch = findViewById(R.id.imageEmptySearch);

        tv_nama = findViewById(R.id.tv_nama);
        tv_desc = findViewById(R.id.tv_desc);
//        iv_image = findViewById(R.id.iv_image);
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
        btn_disc = findViewById(R.id.btn_share);

        initFirestore();

    }

    private void initFirestore() {

        mFirestore = FirebaseFirestore.getInstance();


        if (productCategory.equals("Hardware")) {
            cover = "https://www.techopedia.com/images/uploads/dreamstime_m_90633667.jpg";

            getSupportActionBar().setTitle(productCategory);

            DocumentReference docRef = db.collection("kelompok").document("Ecfv2CNmkK0niHm4cJrC");
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    CategoryModel cat = documentSnapshot.toObject(CategoryModel.class);
                    onProductLoaded(cat);
                }
            });

        }
        if (productCategory.equals("Network")) {

            getSupportActionBar().setTitle(productCategory);

            DocumentReference docRef = db.collection("kelompok").document("rYaz7IH2NpPjGU6kHKOp");
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    CategoryModel cat = documentSnapshot.toObject(CategoryModel.class);
                    onProductLoaded(cat);
                }
            });

        }
        if (productCategory.equals("Software")) {

            DocumentReference docRef = db.collection("kelompok").document("u98iiLI4bTFoABrN9uoa");
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    CategoryModel cat = documentSnapshot.toObject(CategoryModel.class);
                    onProductLoaded(cat);

                }
            });

        }
        if (productCategory.equals("Multimedia")) {

            DocumentReference docRef = db.collection("kelompok").document("wmCNHHyf2OZO5kQJIxab");
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    CategoryModel cat = documentSnapshot.toObject(CategoryModel.class);
                    onProductLoaded(cat);
                }
            });


        }

    }


    private void onProductLoaded(CategoryModel product) {


        tv_nama.setText(product.getName());
        tv_desc.setText(product.getDesc());
        tv_req1.setText(product.getJob1());
        tv_req2.setText(product.getJob2());
        tv_req3.setText(product.getJob3());
        tv_req4.setText(product.getJob4());
        tv_req5.setText(product.getJob5());
        tv_req6.setText(product.getJob6());
        tv_req7.setText(product.getJob7());
        tv_req8.setText(product.getJob8());
        tv_req9.setText(product.getJob9());
        tv_req10.setText(product.getJob10());

//        Glide.with(this)
//                .asBitmap()
//                .load(product.getImage())
//                .into(iv_image);


        btn_disc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CategoryActivity.this, MainActivity.class);
                startActivity(i);

                Toast.makeText(CategoryActivity.this, "Back to Discover",
                        Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Start listening for Firestore updates
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
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
}
