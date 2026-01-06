package com.example.heathub_splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class home extends AppCompatActivity {

    ListView listView;

    String[] items = {
            "Popcorn", "Reheat", "Grill", "Defrost", "Warm Milk",
            "Bake", "Steam Vegetables", "Pizza", "Chicken Roast", "French Fries"
    };


    int[] icons = {
            R.drawable.popcorn,
            R.drawable.reheat,
            R.drawable.grill,
            R.drawable.defrost,
            R.drawable.warm_milk,
            R.drawable.bake,
            R.drawable.steam,
            R.drawable.pizza,
            R.drawable.chicken,
            R.drawable.fries
    };

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_home);

        listView = findViewById(R.id.listView);


        MenuAdapter ad = new MenuAdapter(this, items, icons);
        listView.setAdapter(ad);

        listView.setOnItemClickListener((AdapterView<?> parent, android.view.View v, int pos, long id) -> {

            Toast.makeText(this, "Selected: " + items[pos], Toast.LENGTH_SHORT).show();


            Intent i = new Intent(home.this, ModeActivity.class);
            i.putExtra("mode", items[pos]);
            startActivity(i);
        });
    }
}
