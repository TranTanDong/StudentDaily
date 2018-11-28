package com.example.woo.studentdaily.Subject.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Model.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {
    private Context context;
    private ArrayList<Subject> subjects;
    private ISubject iSubject;

    public SubjectAdapter(Context context, ArrayList<Subject> subjects, ISubject iSubject) {
        this.context = context;
        this.subjects = subjects;
        this.iSubject = iSubject;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder (v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        String day = moveDay(subjects.get(position).getCreateDay());

        holder.tvNameSubject.setText(subjects.get(position).getName());
        holder.tvCreateDay.setText(day);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iSubject.onItemClickSubject(Integer.parseInt(view.getTag().toString()));
            }
        });
    }

    private String moveDay(String createDay) {
        if (createDay.contains(" ")){
            String startTime[] = createDay.split(" ");
            String day = Common.moveSlashTo(startTime[0], "-", "/");
            String time = startTime[1];
            return day + " " + time;
        }else return createDay;
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder{
        TextView tvNameSubject;
        TextView tvCreateDay;
        public SubjectViewHolder(View itemView) {
            super(itemView);
            tvNameSubject = itemView.findViewById(R.id.tv_name_subject);
            tvCreateDay   = itemView.findViewById(R.id.tv_create_day);
        }
    }


    public void refreshAdapter(ArrayList<Subject> subjectNew){
        subjects.clear();
        subjects.addAll(subjectNew);
        notifyDataSetChanged();
    }

    public interface ISubject{
        void onItemClickSubject(int position);
    }
}
