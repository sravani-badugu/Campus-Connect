package com.example.campusconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class StudentDashboardActivity extends AppCompatActivity {

    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvWelcome = findViewById(R.id.tvWelcome);

        // Load saved profile data
        SharedPreferences prefs = getSharedPreferences("CampusConnectPrefs", MODE_PRIVATE);
        String name = prefs.getString("name", "");
        if (!name.isEmpty()) {
            tvWelcome.setText("Welcome, " + name);
        }

        Button btnEvents = findViewById(R.id.btnEvents);
        Button btnIssue = findViewById(R.id.btnIssue);
        Button btnPlacements = findViewById(R.id.btnPlacements);
        Button btnLost = findViewById(R.id.btnLost);
        btnIssue.setOnClickListener(v -> Toast.makeText(this, "Issue Reporting", Toast.LENGTH_SHORT).show());
        btnPlacements.setOnClickListener(v -> Toast.makeText(this, "Placements", Toast.LENGTH_SHORT).show());
        btnLost.setOnClickListener(v -> Toast.makeText(this, "Lost and Found", Toast.LENGTH_SHORT).show());

        btnEvents.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, FacultyUpcomingEventsActivity.class);
            intent.putExtra("isFaculty", false); // student mode
            startActivity(intent);
        });
        btnIssue.setOnClickListener(v -> {
            // Open IssueReportingActivity (create this activity if not already)
            Intent intent = new Intent(StudentDashboardActivity.this, IssueReportingActivity.class);
            startActivity(intent);

        });
        btnLost.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, LostFoundActivity.class);
            startActivity(intent);
        });
        btnPlacements.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, PlacementActivity.class);
            startActivity(intent);
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            SharedPreferences prefs = getSharedPreferences("CampusConnectPrefs", MODE_PRIVATE);
            prefs.edit().clear().apply();

            startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        } else if (item.getItemId() == R.id.menu_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
