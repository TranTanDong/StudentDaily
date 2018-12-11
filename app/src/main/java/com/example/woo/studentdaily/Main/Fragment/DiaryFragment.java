package com.example.woo.studentdaily.Main.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.Common.LoadData;
import com.example.woo.studentdaily.Common.Popup;
import com.example.woo.studentdaily.Diary.Adapter.DiaryAdapter;
import com.example.woo.studentdaily.Diary.AddDiaryActivity;
import com.example.woo.studentdaily.Diary.ContentDiaryActivity;
import com.example.woo.studentdaily.Diary.Model.Diary;
import com.example.woo.studentdaily.Plan.AddPlanActivity;
import com.example.woo.studentdaily.R;
import com.example.woo.studentdaily.Server.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryFragment extends Fragment implements DiaryAdapter.IDiary{
    private RecyclerView rcvDiary;
    private DiaryAdapter diaryAdapter;
    private ArrayList<Diary> listDiary;

    public DiaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_diary, container, false);
        addControls(v);
        addEvents();

        return v;
    }

    private void addControls(View v) {
        rcvDiary = v.findViewById(R.id.rcv_diary);
        listDiary = new ArrayList<>();
//        if (listDiary.size() <= 0){
//            LoadData.loadDataDiary(getActivity());
//            LoadData.loadDataPostDiary(getActivity());
//        }
        listDiary = Common.getListDiary(getActivity());

        rcvDiary.setLayoutManager(new LinearLayoutManager(getActivity()));
        diaryAdapter = new DiaryAdapter(getActivity(), listDiary, this);
        rcvDiary.setAdapter(diaryAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        setInfDiary();
    }

    private void setInfDiary() {
        listDiary.clear();
        listDiary = Common.getListDiary(getActivity());
        Log.i("listDiarySize", listDiary.size() + "");
        diaryAdapter.refreshAdapter(listDiary);
    }

    private void addEvents() {

    }

    public static DiaryFragment newInstance() {

        Bundle args = new Bundle();

        DiaryFragment fragment = new DiaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemClickDiary(int position) {
        Intent mIntent = new Intent(getActivity(), ContentDiaryActivity.class);
        mIntent.putExtra("NAME_DIARY", listDiary.get(position).getName());
        mIntent.putExtra("ID_DIARY", listDiary.get(position).getId());
        startActivity(mIntent);
    }

    @Override
    public void onLongClickDiary(final int position) {
        final String listAdd[] = {"Sửa nhật ký", "Xóa nhật ký"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(listDiary.get(position).getName())
                .setItems(listAdd, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (listAdd[i].equals("Sửa nhật ký")){
                            Intent mIntent = new Intent(getActivity(), AddDiaryActivity.class);
                            mIntent.putExtra("FLAG_DIARY", "EDIT_DIARY");
                            mIntent.putExtra("DIARY", listDiary.get(position));
                            startActivity(mIntent);
                        }else if (listAdd[i].equals("Xóa nhật ký")){
                            confirmDeletion(listDiary.get(position).getId());
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void confirmDeletion(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn muốn xóa nhật ký này?");
        builder.setNegativeButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteDiary(id);
            }
        });
        builder.setPositiveButton("KHÔNG", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteDiary(final int id) {
        final Popup popup = new Popup(getActivity());
        popup.createLoadingDialog();
        popup.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.patchDeleteDiary, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                Log.i("DATA_DIARY", response);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LoadData.loadDataDiary(getActivity());
                        LoadData.loadDataPostDiary(getActivity());
                        CountDownTimer timer = new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                setInfDiary();
                                popup.hide();

                            }
                        };
                        timer.start();
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("d_id", String.valueOf(id));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
