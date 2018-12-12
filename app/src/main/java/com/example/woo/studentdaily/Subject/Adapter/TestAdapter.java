package com.example.woo.studentdaily.Subject.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Model.Subject;
import com.example.woo.studentdaily.Subject.Model.Test;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {
    Context context;
    ArrayList<Test> tests;
    ArrayList<Subject> listSubject;
    ITest iTest;

    public TestAdapter(Context context, ArrayList<Test> tests, ArrayList<Subject> listSubject, ITest iTest) {
        this.context = context;
        this.tests = tests;
        this.listSubject = listSubject;
        this.iTest = iTest;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_child_test_view, parent, false);
        return new TestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        String nameSubject = "";
        for (Subject i : listSubject){
            if (i.getId() == tests.get(position).getIdSubject()){
                nameSubject = i.getName();
            }
        }

        holder.tvDayTest.setText(moveDay(tests.get(position).getDayTest()));
        holder.tvTitleTest.setText(nameSubject);
        holder.tvPlaceTest.setText("Phòng "+tests.get(position).getPlace());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iTest.onClickItemTest(Integer.parseInt(v.getTag().toString()));
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

    private String moveDay(String createDay) {
        if (createDay.contains(" ")){
            String startTime[] = createDay.split(" ");
            String day = Common.moveSlashTo(startTime[0], "-", "/");
            String time = startTime[1].substring(0, 5);
            return day + " " + time;
        }else return createDay;
    }

    public interface ITest{
        void onClickItemTest(int position);
    }
}
