package com.example.campusconnect;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StudentRegisterActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etBranch, etSection, etEmail, etPassword;
    private Button btnRegister;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etBranch = findViewById(R.id.etBranch);
        etSection = findViewById(R.id.etSection);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> registerStudent());
    }

    private void registerStudent() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String branch = etBranch.getText().toString().trim();
        String section = etSection.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validate fields
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) ||
                TextUtils.isEmpty(branch) || TextUtils.isEmpty(section) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 6){
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create user in Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        if(user != null){
                            // Save additional details in Firestore
                            Map<String, Object> studentData = new HashMap<>();
                            studentData.put("name", firstName + " " + lastName); // ✅ fixed: combine first & last name
                            studentData.put("branch", branch);
                            studentData.put("section", section);
                            studentData.put("email", email);
                            studentData.put("role", "student");

                            db.collection("users")
                                    .document(user.getUid())
                                    .set(studentData)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(this, "Student registered successfully!", Toast.LENGTH_SHORT).show();
                                        finish(); // Close activity
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "Firestore Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                        Log.e("FirestoreError", e.getMessage(), e);
                                    });
                        } else {
                            Toast.makeText(this, "User is null after registration", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(this, "Registration failed: " +
                                task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("FirebaseAuth", "Registration error", task.getException());
                    }
                });
    }
}
