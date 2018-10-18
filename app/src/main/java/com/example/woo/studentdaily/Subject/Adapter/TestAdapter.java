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
import com.example.woo.studentdaily.Subject.Model.Test;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {
    Context context;
    ArrayList<Test> tests;

    public TestAdapter(Context context, ArrayList<Test> tests) {
        this.context = context;
        this.tests = tests;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_child_test_view, parent, false);
        return new TestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        holder.tvDayTest.setText(tests.get(position).getNgay());
        holder.tvTitleTest.setText(tests.get(position).getTen());
        holder.tvPlaceTest.setText(tests.get(position).getDiaDiem());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Đau đầu vl", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    public class TestViewHolder extends RecyclerView.ViewHolder{
        TextView tvDayTest;
        TextView tvTitleTest;
        TextView tvPlaceTest;

        public TestViewHolder(View itemView) {
            super(itemView);
            tvDayTest = itemView.findViewById(R.id.tv_day_test);
            tvTitleTest = itemView.findViewById(R.id.tv_title_test);
            tvPlaceTest     = itemView.findViewById(R.id.tv_place_test);
        }

    }
}
