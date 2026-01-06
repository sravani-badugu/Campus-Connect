package com.example.campusconnect;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Paint; // <-- needed for UNDERLINE_TEXT_FLAG

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnStudent, btnFaculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ✅ Check if already logged in
        SharedPreferences prefs = getSharedPreferences("CampusConnectPrefs", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        String userType = prefs.getString("userType", "");

        if (isLoggedIn) {
            if (userType.equals("student")) {
                startActivity(new Intent(this, StudentDashboardActivity.class));
            } else if (userType.equals("faculty")) {
                startActivity(new Intent(this, FacultyDashboardActivity.class));
            }
            finish();
            return;
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Handle system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize buttons
        btnStudent = findViewById(R.id.btnStudent);
        btnFaculty = findViewById(R.id.btnFaculty);

        // Set click listeners for login
        btnStudent.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, StudentLoginActivity.class));
        });

        btnFaculty.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, FacultyLoginActivity.class));
        });

        // ✅ Insert “Register as Student” and “Register as Faculty” links
        TextView tvRegisterStudent = findViewById(R.id.tvRegisterStudent);
        TextView tvRegisterFaculty = findViewById(R.id.tvRegisterFaculty);

        // Underline text programmatically
        tvRegisterStudent.setPaintFlags(tvRegisterStudent.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvRegisterFaculty.setPaintFlags(tvRegisterFaculty.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Click listeners for registration
        tvRegisterStudent.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, StudentRegisterActivity.class));
        });

        tvRegisterFaculty.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, FacultyRegisterActivity.class));
        });
    }
}
