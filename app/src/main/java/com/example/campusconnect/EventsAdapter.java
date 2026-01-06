package com.example.campusconnect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {
    private Context context;
    private List<EventModel> eventList;

    public EventsAdapter(Context context, List<EventModel> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventModel event = eventList.get(position);

        holder.title.setText(event.getTitle());
        holder.description.setText(event.getDescription());
        holder.postedBy.setText("Posted by: " + event.getPostedBy());
        holder.date.setText(event.getDate());

        // Load image from Cloudinary URL
        Glide.with(context).load(event.getPhotoUri()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, description, postedBy, date;
        Button registerBtn;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.eventImage);
            title = itemView.findViewById(R.id.eventTitle);
            description = itemView.findViewById(R.id.eventDescription);
            postedBy = itemView.findViewById(R.id.eventPostedBy);
            date = itemView.findViewById(R.id.eventDate);
            registerBtn = itemView.findViewById(R.id.registerButton);
        }
    }
}
