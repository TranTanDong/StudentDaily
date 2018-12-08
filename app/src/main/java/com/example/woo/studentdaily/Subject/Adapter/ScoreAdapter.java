package com.example.woo.studentdaily.Subject.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Model.Score;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {
    Context context;
    ArrayList<Score> scores;
    String type;
    IScore iScore;

    public ScoreAdapter(Context context, ArrayList<Score> scores, String type, IScore iScore) {
        this.context = context;
        this.scores = scores;
        this.type = type;
        this.iScore = iScore;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_child_score_view, parent, false);
        return new ScoreViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, final int position) {

        holder.tvScore.setText(String.valueOf(scores.get(position).getScore()));
        holder.tvNote.setText(scores.get(position).getNote());
        holder.tvUpdateDay.setText("Cập nhật: "+moveDay(scores.get(position).getUpdateDay()));

        holder.btnDelete.setTag(position);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iScore.onClickDeleteScore(Integer.parseInt(view.getTag().toString()), type);
            }
        });

        holder.btnEdit.setTag(position);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iScore.onClickEditScore(Integer.parseInt(view.getTag().toString()), type);
            }
        });
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder{
        TextView tvScore, tvUpdateDay, tvNote;
        ImageView btnDelete, btnEdit;
        public ScoreViewHolder(View itemView) {
            super(itemView);
            tvScore = itemView.findViewById(R.id.tv_score);
            tvUpdateDay = itemView.findViewById(R.id.tv_update_day_score);
            tvNote = itemView.findViewById(R.id.tv_note_score);
            btnEdit = itemView.findViewById(R.id.btn_edit_score);
            btnDelete = itemView.findViewById(R.id.btn_delete_score);
        }
    }

    private String moveDay(String createDay) {
        if (createDay.contains(" ")){
            String startTime[] = createDay.split(" ");
            String day = Common.moveSlashTo(startTime[0], "-", "/");
            String time = startTime[1].substring(0, 5);
            return day + " " + time;
        }else return createDay;
    }

    public void refreshAdapter(ArrayList<Score> scoreNew){
        scores.clear();
        scores.addAll(scoreNew);
        notifyDataSetChanged();
    }

    public interface IScore{
        void onClickEditScore(int position, String type);
        void onClickDeleteScore(int position, String type);
    }
}
