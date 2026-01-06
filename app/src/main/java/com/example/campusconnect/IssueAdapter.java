package com.example.campusconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.IssueViewHolder> {

    private Context context;
    private List<IssueModel> issueList;

    public IssueAdapter(Context context, List<IssueModel> issueList) {
        this.context = context;
        this.issueList = issueList;
    }

    @NonNull
    @Override
    public IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_issue, parent, false);
        return new IssueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueViewHolder holder, int position) {
        IssueModel issue = issueList.get(position);
        holder.tvIssueName.setText(issue.getIssueName());
        holder.tvClassRoom.setText("ClassRoom: " + issue.getClassRoom());
        holder.tvBlock.setText("Block: " + issue.getBlock());
        holder.tvRegdNumber.setText("Regd No: " + issue.getRegdNumber());
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    public static class IssueViewHolder extends RecyclerView.ViewHolder {
        TextView tvIssueName, tvClassRoom, tvBlock, tvRegdNumber;

        public IssueViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIssueName = itemView.findViewById(R.id.tvIssueName);
            tvClassRoom = itemView.findViewById(R.id.tvClassRoom);
            tvBlock = itemView.findViewById(R.id.tvBlock);
            tvRegdNumber = itemView.findViewById(R.id.tvRegdNumber);
        }
    }
}
