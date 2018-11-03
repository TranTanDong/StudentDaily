package com.example.woo.studentdaily.Plan.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.woo.studentdaily.Plan.Model.Plan;
import com.example.woo.studentdaily.R;

import java.util.ArrayList;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {
    Context context;
    ArrayList<Plan> plans;

    public PlanAdapter(Context context, ArrayList<Plan> plans) {
        this.context = context;
        this.plans = plans;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_tab_plan_list, parent, false);
        return new PlanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        holder.tvContentPlan.setText(plans.get(position).getName());
        holder.tvUpdateDay.setText(plans.get(position).getUpdateDay());
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public class PlanViewHolder extends RecyclerView.ViewHolder{
        TextView tvContentPlan;
        TextView tvUpdateDay;
        public PlanViewHolder(View itemView) {
            super(itemView);
            tvContentPlan = itemView.findViewById(R.id.tv_content_plan);
            tvUpdateDay   = itemView.findViewById(R.id.tv_update_day_plan);
        }
    }
}
