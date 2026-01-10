package com.example.heathub_splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ListView listView;

    // AdMob Variable
    private InterstitialAd mInterstitialAd;

    // Menu items
    TextView navHome, navMode, navUser, navQR, navLogout, nvAi;

    String[] items = {"Popcorn", "Reheat", "Grill", "Defrost", "Warm Milk", "Bake", "Steam Vegetables", "Pizza", "Chicken Roast", "French Fries"};
    int[] icons = {R.drawable.pop, R.drawable.reheat, R.drawable.grill, R.drawable.defrost, R.drawable.warm_milk, R.drawable.bake, R.drawable.steam, R.drawable.pizz, R.drawable.chicken, R.drawable.fries};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // --- AdMob Initialization ---
        MobileAds.initialize(this, initializationStatus -> {});
        loadMyAd(); // Ad load karna start karein

        // 1. Setup Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // 2. Setup Drawer
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));

        // 3. Initialize Views
        navHome = findViewById(R.id.nav_home);
        navMode = findViewById(R.id.nav_mode);
        navUser = findViewById(R.id.nav_user);
        navQR = findViewById(R.id.nav_qr);
        navLogout = findViewById(R.id.nav_logout);
        nvAi = findViewById(R.id.nav_Ai);

        // 4. Initialize ListView
        listView = findViewById(R.id.listView);
        MenuAdapter ad = new MenuAdapter(this, items, icons);
        listView.setAdapter(ad);

        // --- Drawer Navigation Clicks ---
        navHome.setOnClickListener(v -> drawerLayout.closeDrawer(GravityCompat.START));

        navMode.setOnClickListener(v -> {
            startActivity(new Intent(home.this, ModeActivity.class));
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        navUser.setOnClickListener(v -> {
            startActivity(new Intent(home.this, userInfo.class));
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        navQR.setOnClickListener(v -> {
            startActivity(new Intent(home.this, barcode.class));
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        nvAi.setOnClickListener(v -> {
            startActivity(new Intent(home.this, chatbot.class));
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        navLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(home.this, login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // --- ListView Clicks (Yahan Ad Show Hogi) ---
        listView.setOnItemClickListener((parent, v, pos, id) -> {
            String selectedMode = items[pos];

            if (mInterstitialAd != null) {
                // Ad show karein
                mInterstitialAd.show(home.this);

                // Ad close hone ke baad kya karna hai, wo yahan handle karein (Optional but better)
                // Lekin filhaal direct logic:
                Intent i = new Intent(home.this, ModeActivity.class);
                i.putExtra("mode", selectedMode);
                startActivity(i);

                // Agli baar ke liye naya ad load karein
                loadMyAd();
            } else {
                // Agar ad load nahi hui to direct activity khol dein
                Intent i = new Intent(home.this, ModeActivity.class);
                i.putExtra("mode", selectedMode);
                startActivity(i);

                // Dubara koshish karein load karne ki
                loadMyAd();
            }
        });
    }

    // --- Ad Loading Method ---
    private void loadMyAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        // Yeh test ID hai
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}