package com.example.campusconnect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PostEventActivity extends AppCompatActivity {

    private EditText etTitle, etDesc, etFormLink;
    private Button btnPostEvent, btnSelectImage;
    private ImageView ivSelectedImage;
    private Uri selectedImageUri = null;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private static final int IMAGE_PICK_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_events);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        etTitle = findViewById(R.id.etEventTitle);
        etDesc = findViewById(R.id.etEventDesc);
        etFormLink = findViewById(R.id.etFormLink);
        btnPostEvent = findViewById(R.id.btnPostEvent);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        ivSelectedImage = findViewById(R.id.ivSelectedImage);

        // Select image from gallery
        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_PICK_CODE);
        });

        // Post event
        btnPostEvent.setOnClickListener(v -> postEvent());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            ivSelectedImage.setImageURI(selectedImageUri);
        }
    }

    private void postEvent() {
        String title = etTitle.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String formLink = etFormLink.getText().toString().trim();

        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "You must be logged in to post events", Toast.LENGTH_SHORT).show();
            return;
        }

        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Please enter title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        // If image selected, upload to Cloudinary
        if (selectedImageUri != null) {
            new Thread(() -> {
                try {
                    Cloudinary cloudinary = CloudinaryConfig.getInstance();
                    File file = FileUtils.getFileFromUri(this, selectedImageUri);
                    Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                    String imageUrl = uploadResult.get("secure_url").toString();

                    // Save event with image
                    saveEventToFirestore(title, desc, formLink, imageUrl, user.getEmail());
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }).start();
        } else {
            // Save event without image
            saveEventToFirestore(title, desc, formLink, null, user.getEmail());
        }
    }

    private void saveEventToFirestore(String title, String desc, String formLink, String imageUrl, String postedBy) {
        Map<String, Object> event = new HashMap<>();
        event.put("title", title);
        event.put("description", desc);
        event.put("postedBy", postedBy != null ? postedBy : "Unknown");
        event.put("photoUri", imageUrl); // Can be null
        event.put("formLink", formLink.isEmpty() ? null : formLink);
        event.put("timestamp", Timestamp.now());

        db.collection("events").add(event)
                .addOnSuccessListener(docRef -> runOnUiThread(() -> {
                    Toast.makeText(this, "Event posted successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }))
                .addOnFailureListener(e -> runOnUiThread(() -> Toast.makeText(this, "Failed to post event: " + e.getMessage(), Toast.LENGTH_LONG).show()));
    }
}
