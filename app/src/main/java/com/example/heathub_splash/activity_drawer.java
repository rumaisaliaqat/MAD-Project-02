package com.example.heathub_splash;

import static com.example.heathub_splash.R.id.btnuserinfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;

import com.google.firebase.auth.FirebaseAuth;

public class activity_drawer extends AppCompatActivity {

    DrawerLayout drawerLayout;
    FirebaseAuth mAuth;
    Button btnOpenDrawer;
    Button btnHome, btnMode, btnuserinfo,btnlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
         btnlogout=findViewById(R.id.btnlogout);
        mAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawerLayout);
        btnOpenDrawer = findViewById(R.id.btnOpenDrawer);
        btnHome = findViewById(R.id.btnHome);
        btnMode = findViewById(R.id.btnMode);
        btnuserinfo = findViewById(R.id.btnuserinfo);


        btnOpenDrawer.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));


        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(activity_drawer.this, home.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        });


        btnMode.setOnClickListener(v -> {
            Intent intent = new Intent(activity_drawer.this, ModeActivity.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        });


        btnuserinfo.setOnClickListener(v -> {
            Intent intent = new Intent(activity_drawer.this, userInfo.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        btnlogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(activity_drawer.this, login.class);
            startActivity(intent);

        });
    }
}
