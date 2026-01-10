package com.example.heathub_splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ListView listView;

    // Menu items for side drawer (TextViews instead of Buttons)
    TextView navHome, navMode, navUser, navQR, navLogout;

    String[] items = {"Popcorn", "Reheat", "Grill", "Defrost", "Warm Milk", "Bake", "Steam Vegetables", "Pizza", "Chicken Roast", "French Fries"};
    int[] icons = {R.drawable.pop, R.drawable.reheat, R.drawable.grill, R.drawable.defrost, R.drawable.warm_milk, R.drawable.bake, R.drawable.steam, R.drawable.pizz, R.drawable.chicken, R.drawable.fries};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 1. Setup Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Hide default title so our custom one shows

        // 2. Setup Drawer and Hamburger Icon
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white)); // Set hamburger color to white

        // 3. Initialize Drawer TextViews
        navHome = findViewById(R.id.nav_home);
        navMode = findViewById(R.id.nav_mode);
        navUser = findViewById(R.id.nav_user);
        navQR = findViewById(R.id.nav_qr);
        navLogout = findViewById(R.id.nav_logout);

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

        navLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(home.this, login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // --- ListView Clicks ---
        listView.setOnItemClickListener((parent, v, pos, id) -> {
            Intent i = new Intent(home.this, ModeActivity.class);
            i.putExtra("mode", items[pos]);
            startActivity(i);
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