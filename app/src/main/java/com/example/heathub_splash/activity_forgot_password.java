package com.example.heathub_splash;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class activity_forgot_password extends AppCompatActivity {

    EditText emailEdit;
    Button resetBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEdit = findViewById(R.id.etEmailReset);
        resetBtn = findViewById(R.id.btnReset);
        mAuth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(v -> {
            String email = emailEdit.getText().toString().trim();

            if (email.isEmpty()) {
                emailEdit.setError("Email is required");
                return;
            }

            // Firebase Magic: Reset Email bhejna
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(activity_forgot_password.this, "Check your Email inbox!", Toast.LENGTH_LONG).show();
                            finish(); // Screen band ho jayegi aur user login par wapis chala jayega
                        } else {
                            Toast.makeText(activity_forgot_password.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}