package com.example.woo.studentdaily.More.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Subject.Model.Lecturer;

import java.util.ArrayList;
import java.util.List;

public class LecturerAdapter extends RecyclerView.Adapter<LecturerAdapter.LecturerViewHolder> implements Filterable {
    Context context;
    ArrayList<Lecturer> lecturers;
    ILecturer iLecturer;
    ArrayList<Lecturer> lecturersFilter;

    public LecturerAdapter(Context context, ArrayList<Lecturer> lecturers, ILecturer iLecturer) {
        this.context = context;
        this.lecturers = lecturers;
        this.iLecturer = iLecturer;
        lecturersFilter = new ArrayList<>(lecturers);
    }

    @NonNull
    @Override
    public LecturerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_lecturer, parent, false);
        return new LecturerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LecturerViewHolder holder, final int position) {
        holder.tvLecturer.setText(lecturers.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iLecturer.onClickItemLecturer(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lecturers.size();
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Lecturer> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(lecturersFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Lecturer item : lecturersFilter) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            lecturers.clear();
            lecturers.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class LecturerViewHolder extends RecyclerView.ViewHolder {
        TextView tvLecturer;
        public LecturerViewHolder(View itemView) {
            super(itemView);
            tvLecturer = itemView.findViewById(R.id.tv_name_lecturer_all);
        }
    }

    public void refreshAdapter(List<Lecturer> lecturerNew){
        lecturers.clear();
        lecturers.addAll(lecturerNew);
        notifyDataSetChanged();
    }

    public interface ILecturer{
        void onClickItemLecturer(int position);
    }
}
