package com.example.woo.studentdaily.Diary.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Diary.Model.PostDiary;
import com.example.woo.studentdaily.R;

import java.util.ArrayList;

public class PostDiaryAdapter extends RecyclerView.Adapter<PostDiaryAdapter.PostDiaryViewHolder> {
    Context context;
    ArrayList<PostDiary> postDiaries;
    IPostDiary iPostDiary;

    public PostDiaryAdapter(Context context, ArrayList<PostDiary> postDiaries, IPostDiary iPostDiary) {
        this.context = context;
        this.postDiaries = postDiaries;
        this.iPostDiary = iPostDiary;
    }

    @NonNull
    @Override
    public PostDiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =LayoutInflater.from(context).inflate(R.layout.item_content_diary, parent, false);
        return new PostDiaryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostDiaryViewHolder holder, int position) {
        holder.tvDayCreate.setText(Common.moveDay(postDiaries.get(position).getDayCreate()));
        holder.tvContent.setText(postDiaries.get(position).getContent());
        String img = postDiaries.get(position).getAttach();
        if (img.isEmpty()){
            holder.imgContent.setVisibility(View.GONE);
        }else {
            holder.imgContent.setVisibility(View.VISIBLE);
            Glide.with(context).load(img)
                    .apply(RequestOptions
                            .overrideOf(120, 120)
                            .placeholder(R.drawable.ic_image_error_blue)
                            .error(R.drawable.ic_image_error_blue)
                            .formatOf(DecodeFormat.PREFER_RGB_565)
                            .timeout(3000)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)).into(holder.imgContent);
        }

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Delete ^^", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return postDiaries.size();
    }

    public class PostDiaryViewHolder extends RecyclerView.ViewHolder{
        TextView tvDayCreate, tvContent;
        ImageView imgContent, btnDelete;
        public PostDiaryViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content_post);
            tvDayCreate = itemView.findViewById(R.id.tv_day_create_post);
            imgContent = itemView.findViewById(R.id.img_post);
            btnDelete = itemView.findViewById(R.id.btn_delete_post);
        }
    }

    public interface IPostDiary{
        void onClickDeletePostDiary(int position);
    }
}
