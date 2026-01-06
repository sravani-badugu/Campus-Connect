package com.example.campusconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;


public class FacultyDashboardActivity extends AppCompatActivity {

    private Button btnUpcomingEvents;
    private Button btnNews;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dashboard);

        // Initialize Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get faculty email from login/register
        String facultyEmail = getIntent().getStringExtra("email");

        // Initialize Upcoming Events button
        btnUpcomingEvents = findViewById(R.id.btnEvents);

        btnUpcomingEvents.setOnClickListener(v -> {
            Intent intent = new Intent(FacultyDashboardActivity.this, FacultyUpcomingEventsActivity.class);
            intent.putExtra("email", facultyEmail);
            intent.putExtra("isFaculty", true); // faculty mode
            startActivity(intent);
        });
        Button btnIssue = findViewById(R.id.btnIssue);
        btnIssue.setOnClickListener(v -> {
            // Open IssueReportingActivity (create this activity if not already)
            Intent intent = new Intent(FacultyDashboardActivity.this, IssueReportingActivity.class);
            startActivity(intent);
        });
        Button btnLost = findViewById(R.id.btnLost);
        btnLost.setOnClickListener(v -> {
            Intent intent = new Intent(FacultyDashboardActivity.this, LostFoundActivity.class);
            startActivity(intent);
        });
        Button btnPlacements = findViewById(R.id.btnPlacements);
        btnPlacements.setOnClickListener(v -> {
            Intent intent = new Intent(FacultyDashboardActivity.this, PlacementActivity.class);
            startActivity(intent);
        });

        }

    // Inflate the dashboard menu (Profile / Logout)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    // Handle menu item clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_logout) {
            // Clear stored login info
            SharedPreferences prefs = getSharedPreferences("CampusConnectPrefs", MODE_PRIVATE);
            prefs.edit().clear().apply();

            startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        } else if (id == R.id.menu_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
