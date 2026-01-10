package com.example.heathub_splash;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class barcode extends AppCompatActivity {

    Button btnScanQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        // Scanner line animation logic
        View scanLine = findViewById(R.id.scanLine);
        android.view.animation.Animation animation = new android.view.animation.TranslateAnimation(
                0, 0, 0, 750); // Line move distance
        animation.setDuration(2000);
        animation.setRepeatCount(android.view.animation.Animation.INFINITE);
        scanLine.startAnimation(animation);

        btnScanQR = findViewById(R.id.btnScan);

        btnScanQR.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions();
            options.setPrompt("Scan QR Code");
            options.setBeepEnabled(true);
            options.setOrientationLocked(false);

            barcodeLauncher.launch(options);
        });
    }

    ActivityResultLauncher<ScanOptions> barcodeLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() != null) {
                    Toast.makeText(this,
                            "Scanned Data: " + result.getContents(),
                            Toast.LENGTH_LONG).show();
                }
            });
}