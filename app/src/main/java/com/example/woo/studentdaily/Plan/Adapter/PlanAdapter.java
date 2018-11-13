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
    IPlan iPlan;

    public PlanAdapter(Context context, ArrayList<Plan> plans, IPlan iPlan) {
        this.context = context;
        this.plans = plans;
        this.iPlan = iPlan;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_tab_plan_list, parent, false);
        return new PlanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, final int position) {
        String s = moveDay(plans.get(position).getUpdateDay());
        holder.tvContentPlan.setText(plans.get(position).getName());
        holder.tvUpdateDay.setText(s);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iPlan.onItemClickPlan(Integer.parseInt(view.getTag().toString()));
            }
        });
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

    public String moveDay(String day){
        if (day.contains("-")){
            String arr[] = day.split("-");
            return arr[2] + " thg " + arr[1];
        }
        return day;
    }

    public void refreshAdapter(ArrayList<Plan> planNew){
        plans.clear();
        plans.addAll(planNew);
        notifyDataSetChanged();
    }

    public interface IPlan{
        void onItemClickPlan(int position);
    }
}
