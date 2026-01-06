package com.example.campusconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentLoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etEmail = findViewById(R.id.etStudentEmail);
        etPassword = findViewById(R.id.etStudentPassword);
        btnLogin = findViewById(R.id.btnStudentLogin);

        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = mAuth.getCurrentUser().getUid();
                        db.collection("users").document(uid).get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (documentSnapshot.exists()) {
                                        String role = documentSnapshot.getString("role");
                                        if ("student".equals(role)) {
                                            SharedPreferences prefs = getSharedPreferences("CampusConnectPrefs", MODE_PRIVATE);
                                            prefs.edit()
                                                    .putBoolean("isLoggedIn", true)
                                                    .putString("userType", "student")
                                                    .putString("email", email)
                                                    .putString("name", documentSnapshot.getString("name"))
                                                    .putString("branch", documentSnapshot.getString("branch"))
                                                    .apply();

                                            startActivity(new Intent(StudentLoginActivity.this, StudentDashboardActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(this, "Not a student account", Toast.LENGTH_SHORT).show();
                                            mAuth.signOut();
                                        }
                                    } else {
                                        Toast.makeText(this, "User profile not found", Toast.LENGTH_SHORT).show();
                                        mAuth.signOut();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Error fetching profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();
                                });
                    } else {
                        Toast.makeText(this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
