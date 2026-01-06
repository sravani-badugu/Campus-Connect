package com.example.campusconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LostFoundActivity extends AppCompatActivity {

    private Button btnLost, btnFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_found);

        btnLost = findViewById(R.id.btnLostOnly);
        btnFound = findViewById(R.id.btnFoundOnly);

        btnLost.setOnClickListener(v -> {
            // Open Lost reporting activity
            Intent intent = new Intent(LostFoundActivity.this, ReportLostActivity.class);
            startActivity(intent);
        });

        btnFound.setOnClickListener(v -> {
            // Open Found reporting activity
            Intent intent = new Intent(LostFoundActivity.this, ReportFoundActivity.class);
            startActivity(intent);
        });
    }
}
