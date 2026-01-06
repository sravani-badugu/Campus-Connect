package com.example.campusconnect;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

public class FacultyUpcomingEventsActivity extends AppCompatActivity {

    private RecyclerView recyclerEvents;
    private FloatingActionButton fabAddEvent;
    private ArrayList<EventModel> eventList;
    private EventsAdapter adapter;
    private boolean isFaculty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_events);

        recyclerEvents = findViewById(R.id.recyclerEvents);
        fabAddEvent = findViewById(R.id.fabAddEvent);

        isFaculty = getIntent().getBooleanExtra("isFaculty", false);

        if (!isFaculty) {
            fabAddEvent.setVisibility(android.view.View.GONE);
        }

        eventList = new ArrayList<>();
        adapter = new EventsAdapter(this, eventList);
        recyclerEvents.setLayoutManager(new LinearLayoutManager(this));
        recyclerEvents.setAdapter(adapter);

        fabAddEvent.setOnClickListener(v -> startActivity(new Intent(this, PostEventActivity.class)));

        loadEvents();
    }

    private void loadEvents() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("events")
                .orderBy("timestamp")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    eventList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String title = doc.getString("title");
                        String description = doc.getString("winners");
                        String postedBy = doc.getString("ConductedBy");
                        String date = doc.getString("date");
                        String formLink = doc.getString("formLink");
                        String photoUri = doc.getString("photoUri"); // <--- FIXED

                        EventModel event = new EventModel(title, description, postedBy, date, photoUri, formLink);
                        eventList.add(event);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    android.widget.Toast.makeText(this, "Failed to load events: " + e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
                });
    }
}
