package com.example.cerc_materialdesign.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.cerc_materialdesign.Adapter.ItemAdapter;
import com.example.cerc_materialdesign.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity implements ItemAdapter.OnProductSelectedListener{

    private static final String TAG = "MainActivity";
    private static final int LIMIT = 50;

    private SearchView search;

    BottomNavigationView bottomNavigationView;
    private ImageView img_profile, img_software, img_hardware, img_fav, img_network, img_multimedia, imgEmptySearch;
    private TextView hello_user, titleCategory, textEmptySearch, textEmptySearch2;
    private RecyclerView discover_recyclerview;


    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private Query mQuery;

    private ItemAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        img_profile = findViewById(R.id.img_profile);
        img_software = findViewById(R.id.category_software);
        img_hardware = findViewById(R.id.category_hardware);
        img_network = findViewById(R.id.category_network);
        img_multimedia = findViewById(R.id.category_multimedia);
        img_fav = findViewById(R.id.all_category);
        titleCategory = findViewById(R.id.tvTitleCategory);
        search = findViewById(R.id.searchView);


        discover_recyclerview = findViewById(R.id.discover_recyclerview);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mQuery = mFirestore.collection("Item");

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        categorySoftware();
        categoryHardware();
        categoryNetwork();
        categoryMultimedia();
        categoryFav();
        updateUI(currentUser);


        bottomNav();

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(MainActivity.this, ProfileActivity.class);
                gotoprofile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(gotoprofile);
                finish();
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.w("Query", query);
                Intent catalog = new Intent(MainActivity.this, CategoryActivity.class);
                catalog.putExtra(CategoryActivity.KEY_PRODUCT_CATEGORY, "Search Result");
                catalog.putExtra(CategoryActivity.KEY_SEARCH_QUERY, query);
                startActivity(catalog);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

    }

    private void categoryHardware() {

        img_hardware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent category = new Intent(MainActivity.this, CategoryActivity.class);
                category.putExtra(CategoryActivity.KEY_PRODUCT_CATEGORY, "Hardware");
                startActivity(category);

            }
        });
    }

    private void categorySoftware() {

        img_software.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent category = new Intent(MainActivity.this, CategoryActivity.class);
                category.putExtra(CategoryActivity.KEY_PRODUCT_CATEGORY, "Software");
                startActivity(category);

            }
        });
    }

    private void categoryNetwork() {

        img_network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent category = new Intent(MainActivity.this, CategoryActivity.class);
                category.putExtra(CategoryActivity.KEY_PRODUCT_CATEGORY, "Network");
                startActivity(category);

            }
        });
    }

    private void categoryMultimedia() {

        img_multimedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent category = new Intent(MainActivity.this, CategoryActivity.class);
                category.putExtra(CategoryActivity.KEY_PRODUCT_CATEGORY, "Multimedia");
                startActivity(category);

            }
        });
    }

    private void categoryFav() {

        img_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent fav = new Intent(MainActivity.this, WishlistActivity.class);
               // catalog.putExtra(CategoryActivity.KEY_PRODUCT_CATEGORY, "All");
                startActivity(fav);

            }
        });
    }

    private void initRecyclerView() {

        if (mQuery == null) {
            Log.w(TAG, "No query, not initializing RecyclerView");
        }

        mAdapter = new ItemAdapter(mQuery, this) {

            @Override
            protected void onDataChanged() {
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0) {
                    discover_recyclerview.setVisibility(View.GONE);

                    Log.w(TAG, "ItemCount = 0");
                } else {
                    discover_recyclerview.setVisibility(View.VISIBLE);
                    Log.w(TAG, "Show Item");
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                Snackbar.make(findViewById(android.R.id.content),
                        "Error: check logs for info.", Snackbar.LENGTH_LONG).show();
            }
        };

        discover_recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        discover_recyclerview.setAdapter(mAdapter);
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


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    private void updateUI(FirebaseUser user) {

            initRecyclerView();
            hello_user = findViewById(R.id.hello_user);
            hello_user.setText(
                    String.format("%s%s", "Hello, ", user.getDisplayName()));

    }

    private void bottomNav() {
        //inisialisasi
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // set selected home
        bottomNavigationView.setSelectedItemId(R.id.beranda);
        // item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.beranda:
                        return true;
                    //change infoemasi
                    case R.id.informasi:
                        startActivity(new Intent(getApplicationContext(), InformasiActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                    //change akun
                    case R.id.akun:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onProductSelected(DocumentSnapshot itemModel) {
        // Go to the details page for the selected restaurant
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.KEY_PRODUCT_ID, itemModel.getId());

        startActivity(intent);

    }
}