package com.example.woo.studentdaily.Plan.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Plan.Model.Event;
import com.example.woo.studentdaily.R;

import java.util.ArrayList;

public class EventAdapterPlan extends RecyclerView.Adapter<EventAdapterPlan.EventViewHolder>  {
    Context context;
    ArrayList<Event> events;
    IEvent iEvent;

    public EventAdapterPlan(Context context, ArrayList<Event> events, IEvent iEvent) {
        this.context = context;
        this.events = events;
        this.iEvent = iEvent;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_tab_plan, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Log.e("LOG_EVENT", events.get(position).toString());
        String startTime = events.get(position).getStartTime();
        String day = Common.moveSlashTo(startTime.substring(0, 10), "-", "/");
        String time = startTime.substring(11, 16);

        holder.tvSubTimeEvent.setText(day);
        holder.tvTimeEvent.setText(time);
        holder.tvContentEvent.setText(events.get(position).getName());
        holder.tvPlaceEvent.setText(events.get(position).getPlace());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iEvent.onItemClickEvent(Integer.parseInt(view.getTag().toString()));
            }
        });
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

    public void refreshAdapter(ArrayList<Event> eventNew){
        events.clear();
        events.addAll(eventNew);
        notifyDataSetChanged();
    }

    public interface IEvent{
        void onItemClickEvent(int position);
    }
}
