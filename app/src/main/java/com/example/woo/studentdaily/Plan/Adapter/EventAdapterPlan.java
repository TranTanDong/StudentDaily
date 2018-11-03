package com.example.woo.studentdaily.Plan.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.woo.studentdaily.Plan.Model.Event;
import com.example.woo.studentdaily.R;

import java.util.ArrayList;

public class EventAdapterPlan extends RecyclerView.Adapter<EventAdapterPlan.EventViewHolder>  {
    Context context;
    ArrayList<Event> events;

    public EventAdapterPlan(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_tab_plan, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.tvSubTimeEvent.setText("BÂY GIỜ");
        holder.tvTimeEvent.setText(events.get(position).getStartTime());
        holder.tvContentEvent.setText(events.get(position).getName());
        holder.tvPlaceEvent.setText(events.get(position).getPlace());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{
        TextView tvSubTimeEvent;
        TextView tvTimeEvent;
        TextView tvContentEvent;
        TextView tvPlaceEvent;

        public EventViewHolder(View itemView) {
            super(itemView);
            tvSubTimeEvent = itemView.findViewById(R.id.tv_sub_time_event);
            tvTimeEvent    = itemView.findViewById(R.id.tv_time_event);
            tvContentEvent = itemView.findViewById(R.id.tv_content_event);
            tvPlaceEvent   = itemView.findViewById(R.id.tv_place_event);
        }

    }
}
