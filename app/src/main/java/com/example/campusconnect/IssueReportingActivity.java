package com.example.campusconnect;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueReportingActivity extends AppCompatActivity {

    private EditText etIssueName, etClassRoom, etBlock, etRegdNumber;
    private Button btnSubmit;
    private RecyclerView rvIssues;

    private FirebaseFirestore firestore;
    private List<IssueModel> issueList;
    private IssueAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_reporting);

        etIssueName = findViewById(R.id.etIssueName);
        etClassRoom = findViewById(R.id.etClassRoom);
        etBlock = findViewById(R.id.etBlock);
        etRegdNumber = findViewById(R.id.etRegdNumber);
        btnSubmit = findViewById(R.id.btnSubmitIssue);
        rvIssues = findViewById(R.id.rvIssues);

        firestore = FirebaseFirestore.getInstance();
        issueList = new ArrayList<>();
        adapter = new IssueAdapter(this, issueList);
        rvIssues.setLayoutManager(new LinearLayoutManager(this));
        rvIssues.setAdapter(adapter);

        loadIssues();

        btnSubmit.setOnClickListener(v -> submitIssue());
    }

    private void submitIssue() {
        String issueName = etIssueName.getText().toString().trim();
        String classRoom = etClassRoom.getText().toString().trim();
        String block = etBlock.getText().toString().trim();
        String regdNumber = etRegdNumber.getText().toString().trim();

        if(issueName.isEmpty() || block.isEmpty() || regdNumber.isEmpty()){
            Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String,Object> issue = new HashMap<>();
        issue.put("issueName", issueName);
        issue.put("classRoom", classRoom);
        issue.put("block", block);
        issue.put("regdNumber", regdNumber);
        issue.put("timestamp", System.currentTimeMillis());

        firestore.collection("issues")
                .add(issue)
                .addOnSuccessListener(doc -> {
                    Toast.makeText(this, "Issue reported successfully", Toast.LENGTH_SHORT).show();
                    etIssueName.setText("");
                    etClassRoom.setText("");
                    etBlock.setText("");
                    etRegdNumber.setText("");
                    loadIssues();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to report issue: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void loadIssues() {
        firestore.collection("issues")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    issueList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        IssueModel issue = doc.toObject(IssueModel.class);
                        issueList.add(issue);
                    }
                    adapter.notifyDataSetChanged();
                });
    }
}
