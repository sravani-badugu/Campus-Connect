package com.example.campusconnect;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ReportFoundActivity extends AppCompatActivity {

    private EditText etItemName, etLocation, etDescription;
    private Button btnSubmitFound;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_found);

        etItemName = findViewById(R.id.etItemName);
        etLocation = findViewById(R.id.etLocation);
        etDescription = findViewById(R.id.etDescription);
        btnSubmitFound = findViewById(R.id.btnSubmitFound);

        firestore = FirebaseFirestore.getInstance();

        btnSubmitFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFoundItem();
            }
        });
    }

    private void submitFoundItem() {
        String itemName = etItemName.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (itemName.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> foundItem = new HashMap<>();
        foundItem.put("itemName", itemName);
        foundItem.put("location", location);
        foundItem.put("description", description);
        foundItem.put("timestamp", System.currentTimeMillis());

        firestore.collection("foundItems")
                .add(foundItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ReportFoundActivity.this, "Found item reported successfully", Toast.LENGTH_SHORT).show();
                        etItemName.setText("");
                        etLocation.setText("");
                        etDescription.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ReportFoundActivity.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
