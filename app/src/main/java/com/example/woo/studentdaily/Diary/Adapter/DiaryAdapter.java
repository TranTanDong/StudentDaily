package com.example.woo.studentdaily.Diary.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Diary.Model.Diary;
import com.example.woo.studentdaily.R;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {
    Context context;
    ArrayList<Diary> diaries;
    IDiary iDiary;

    public DiaryAdapter(Context context, ArrayList<Diary> diaries, IDiary iDiary) {
        this.context = context;
        this.diaries = diaries;
        this.iDiary = iDiary;
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_diary, parent, false);
        return new DiaryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        holder.tvContentDiary.setText(diaries.get(position).getName());
        holder.tvCreateDay.setText(Common.moveDay(diaries.get(position).getCreateDay()));
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iDiary.onItemClickDiary(Integer.parseInt(view.getTag().toString()));
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                iDiary.onLongClickDiary(Integer.parseInt(view.getTag().toString()));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return diaries.size();
    }


    public class DiaryViewHolder extends RecyclerView.ViewHolder{
        TextView tvContentDiary;
        TextView tvCreateDay;
        public DiaryViewHolder(View itemView) {
            super(itemView);
            tvContentDiary = itemView.findViewById(R.id.tv_name_diary);
            tvCreateDay = itemView.findViewById(R.id.tv_create_day_diary);
        }
    }


    public void refreshAdapter(ArrayList<Diary> diaryNew){
        diaries.clear();
        diaries.addAll(diaryNew);
        notifyDataSetChanged();
    }

    public interface IDiary{
        void onItemClickDiary(int position);
        void onLongClickDiary(int position);
    }
}
