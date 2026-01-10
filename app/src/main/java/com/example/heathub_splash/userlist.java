package com.example.heathub_splash;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class userlist extends AppCompatActivity {
    RecyclerView recyclerView;
    UserAdapter adapter;
    ArrayList<UserModel> list;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist ); // Iska XML hum pehle bana chuke hain

        recyclerView = findViewById(R.id.usersRecyclerView);
        db = FirebaseDatabase.getInstance().getReference("Users");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new UserAdapter(this, list);
        recyclerView.setAdapter(adapter);

        // Firebase se users ka data mangwana
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Purani list saaf karein taake double data na ho
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);

                    // Apni ID ko list mein nahi dikhana (taake hum khud se chat na karein)
                    if (!user.uid.equals(FirebaseAuth.getInstance().getUid())) {
                        list.add(user);
                    }
                }
                adapter.notifyDataSetChanged(); // List update karein
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Agar koi error aaye
            }
        });
    }
}