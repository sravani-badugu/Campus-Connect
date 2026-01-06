package com.example.campusconnect;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class ProfileActivity extends AppCompatActivity {

    private EditText etName, etBranch;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_profile);

        // Make sure your XML has these IDs
        etName = findViewById(R.id.etName);
        etBranch = findViewById(R.id.etBranch);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadProfile();
    }

    private void loadProfile() {
        String uid = mAuth.getCurrentUser().getUid();

        db.collection("users").document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Safely get values
                        String firstName = documentSnapshot.contains("firstName") ?
                                documentSnapshot.getString("firstName") : "";
                        String lastName = documentSnapshot.contains("lastName") ?
                                documentSnapshot.getString("lastName") : "";
                        String branch = documentSnapshot.contains("branch") ?
                                documentSnapshot.getString("branch") : "";

                        // Combine first + last name with a space
                        etName.setText((firstName + " " + lastName).trim());
                        etBranch.setText(branch);

                        etName.setKeyListener(null);
                        etBranch.setKeyListener(null);

                    } else {
                        Toast.makeText(this, "Profile not found", Toast.LENGTH_SHORT).show();
                        etName.setText("");
                        etBranch.setText("");
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error fetching profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    etBranch.setText("");
                });
    }
}
