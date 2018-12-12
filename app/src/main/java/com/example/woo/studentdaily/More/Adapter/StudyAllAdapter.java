package com.example.woo.studentdaily.More.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Model.Study;
import com.example.woo.studentdaily.Subject.Model.Subject;

import java.util.ArrayList;

public class StudyAllAdapter extends RecyclerView.Adapter<StudyAllAdapter.StudyAllViewHolder> {
    Context context;
    ArrayList<Study> studyList;
    ArrayList<Subject> subjects;
    IStudyAll iStudyAll;

    public StudyAllAdapter(Context context, ArrayList<Study> studyList, ArrayList<Subject> subjects, IStudyAll iStudyAll) {
        this.context = context;
        this.studyList = studyList;
        this.subjects = subjects;
        this.iStudyAll = iStudyAll;
    }

    @NonNull
    @Override
    public StudyAllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_study_all, parent, false);
        return new StudyAllViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudyAllViewHolder holder, int position) {
        holder.tvDayOfWeek.setText(studyList.get(position).getDayOfWeek());
        holder.tvTimeOfDay.setText("Tiết " + studyList.get(position).getLesson());
        holder.tvPlace.setText("Phòng " + studyList.get(position).getPlace());

        String nameSubject = "";
        for (Subject subject:subjects){
            if (subject.getId() == studyList.get(position).getIdSubject()){
                nameSubject = subject.getName();
            }
        }
        holder.tvSubject.setText(nameSubject);

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iStudyAll.onClickItemStudyAll(Integer.parseInt(v.getTag().toString()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return studyList.size();
    }

    public class StudyAllViewHolder extends RecyclerView.ViewHolder{
        TextView tvDayOfWeek;
        TextView tvTimeOfDay;
        TextView tvPlace;
        TextView tvSubject;
        public StudyAllViewHolder(View itemView) {
            super(itemView);
            tvDayOfWeek = itemView.findViewById(R.id.tv_day_of_week_all);
            tvTimeOfDay = itemView.findViewById(R.id.tv_time_of_day_all);
            tvPlace     = itemView.findViewById(R.id.tv_place_all);
            tvSubject   = itemView.findViewById(R.id.tv_name_subject_all);
        }
    }

    public interface IStudyAll{
        void onClickItemStudyAll(int position);
    }
}
