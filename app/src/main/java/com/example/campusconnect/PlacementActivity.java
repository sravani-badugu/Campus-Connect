package com.example.campusconnect;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class PlacementActivity extends AppCompatActivity {

    private TextView tvMessage;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement);

        tvMessage = findViewById(R.id.tvPlacementMessage);
        ivImage = findViewById(R.id.ivPlacementImage);

        // Optional: if you want to pass custom text or image from previous activity
        // tvMessage.setText(getIntent().getStringExtra("message"));
        // ivImage.setImageResource(R.drawable.placements);
    }
}
