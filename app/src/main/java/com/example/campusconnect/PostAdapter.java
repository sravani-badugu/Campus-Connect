package com.example.campusconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Map<String, Object>> postList;

    public PostAdapter(Context context, List<Map<String, Object>> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Map<String, Object> post = postList.get(position);

        // Show text
        holder.tvMessage.setText(post.get("message").toString());

        // Show image if exists
        if (post.containsKey("image")) {
            String imageName = post.get("image").toString();
            int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            if (resId != 0) {
                holder.ivPostImage.setImageResource(resId);
                holder.ivPostImage.setVisibility(View.VISIBLE);
            }
        } else {
            holder.ivPostImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage;
        ImageView ivPostImage;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
        }
    }
}
