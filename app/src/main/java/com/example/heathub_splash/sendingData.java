package com.example.heathub_splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class sendingData extends AppCompatActivity {

    EditText etName;
    DatePicker datePicker;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_data);

        etName = findViewById(R.id.etName);
        datePicker = findViewById(R.id.datePicker);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            int year = datePicker.getYear();
            int currentYear = 2025;
            int age = currentYear - year;


            Intent i = new Intent(sendingData.this, userInfo.class);
            i.putExtra("username", name);
            i.putExtra("age", age);
            startActivity(i);
        });
    }
}
