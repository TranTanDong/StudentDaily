package com.example.woo.studentdaily.Subject.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Model.Study;


import java.util.ArrayList;

public class StudyAdapter extends RecyclerView.Adapter<StudyAdapter.StudyViewHolder> {
    Context context;
    ArrayList<Study> studyList;
    IStudy iStudy;

    public StudyAdapter(Context context, ArrayList<Study> studyList, IStudy iStudy) {
        this.context = context;
        this.studyList = studyList;
        this.iStudy = iStudy;
    }

    @NonNull
    @Override
    public StudyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_child_study_view, parent, false);
        return new StudyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudyViewHolder holder, int position) {
        holder.tvDayOfWeek.setText(studyList.get(position).getDayOfWeek());
        holder.tvTimeOfDay.setText("Tiết " + studyList.get(position).getLesson());
        holder.tvPlace.setText("Phòng " + studyList.get(position).getPlace());
        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iStudy.onClickItemStudy(Integer.parseInt(v.getTag().toString()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return studyList.size();
    }

    public class StudyViewHolder extends RecyclerView.ViewHolder{
        TextView tvDayOfWeek;
        TextView tvTimeOfDay;
        TextView tvPlace;

        public StudyViewHolder(View itemView) {
            super(itemView);
            tvDayOfWeek = itemView.findViewById(R.id.tv_day_of_week);
            tvTimeOfDay = itemView.findViewById(R.id.tv_time_of_day);
            tvPlace     = itemView.findViewById(R.id.tv_place);
        }

    }

    public void refreshAdapter(ArrayList<Study> studyNew){
        studyList.clear();
        studyList.addAll(studyNew);
        notifyDataSetChanged();
    }

    public interface IStudy{
        void onClickItemStudy(int position);
    }
}
