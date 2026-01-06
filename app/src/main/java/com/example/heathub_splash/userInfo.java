package com.example.heathub_splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class userInfo extends AppCompatActivity {

    TextView tvResult;
    Button btnBack, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        tvResult = findViewById(R.id.tvResult);
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);


        String name = getIntent().getStringExtra("username");
        int age = getIntent().getIntExtra("age", 0);

        tvResult.setText("Username: " + name + "\nAge: " + age);

        Toast.makeText(userInfo.this, "Info saved successfully", Toast.LENGTH_SHORT).show();


        btnBack.setOnClickListener(v -> finish());


        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(userInfo.this, activity_drawer.class);
            intent.putExtra("username", name);
            intent.putExtra("age", age);
            startActivity(intent);
        });
    }
}
