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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class ReportLostActivity extends AppCompatActivity {

    private EditText etItemName, etDescription, etLocation, etRegNumber;
    private Button btnSubmitLost;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_lost);

        etItemName = findViewById(R.id.etLostItemName);
        etDescription = findViewById(R.id.etLostDescription);
        etLocation = findViewById(R.id.etLostLocation);
        etRegNumber = findViewById(R.id.etRegNumber);
        btnSubmitLost = findViewById(R.id.btnSubmitLost);

        db = FirebaseFirestore.getInstance();

        btnSubmitLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLostItem();
            }
        });
    }

    private void saveLostItem() {
        String itemName = etItemName.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String regNumber = etRegNumber.getText().toString().trim();

        if (itemName.isEmpty() || description.isEmpty() || location.isEmpty() || regNumber.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> lostItem = new HashMap<>();
        lostItem.put("itemName", itemName);
        lostItem.put("description", description);
        lostItem.put("location", location);
        lostItem.put("regNumber", regNumber);
        lostItem.put("timestamp", System.currentTimeMillis());

        db.collection("lost_items")
                .add(lostItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ReportLostActivity.this, "Lost item reported successfully", Toast.LENGTH_SHORT).show();
                        // Optional: clear inputs after success
                        etItemName.setText("");
                        etDescription.setText("");
                        etLocation.setText("");
                        etRegNumber.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ReportLostActivity.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }); // <-- Missing semicolon fixed here ✅
    }
}
