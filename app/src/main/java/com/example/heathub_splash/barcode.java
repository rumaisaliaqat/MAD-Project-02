package com.example.heathub_splash;
import android.os.Bundle;

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