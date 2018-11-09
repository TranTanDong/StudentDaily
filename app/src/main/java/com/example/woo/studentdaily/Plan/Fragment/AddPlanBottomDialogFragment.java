package com.example.woo.studentdaily.Plan.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woo.studentdaily.Common.Common;
import com.example.woo.studentdaily.R;

import java.util.Calendar;

public class AddPlanBottomDialogFragment extends BottomSheetDialogFragment {

    private TextInputEditText edtNameNewPlan;
    private TextView tvDayAddNewPlan;
    private Button btnOk, btnCancel;

    BottomSheetListener mListener;

    public static AddPlanBottomDialogFragment newInstance(String namePlan, String dayUpdate) {
        final AddPlanBottomDialogFragment fragment = new AddPlanBottomDialogFragment();
        final Bundle args = new Bundle();
        if(namePlan != null) {
            args.putString("namePlan", namePlan);
        }
        if(dayUpdate != null) {
            args.putString("dayUpdate", dayUpdate);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_add_new_plan, container, false);
        addControls(v);
        addEvents();
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    private void addEvents() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namePlan = edtNameNewPlan.getText().toString();
                String dayUpdate = tvDayAddNewPlan.getText().toString();
                Toast.makeText(getActivity(), "LOL"+dayUpdate, Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(namePlan)){
                    mListener.sendDataPlan(edtNameNewPlan.getText().toString(), tvDayAddNewPlan.getText().toString());
                    dismiss();
                }else Toast.makeText(getActivity(), "Hãy nhập tên kế hoạch", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void addControls(View v) {
        edtNameNewPlan = v.findViewById(R.id.edt_add_new_plan);
        tvDayAddNewPlan = v.findViewById(R.id.tv_day_add_new_plan);
        btnOk   = v.findViewById(R.id.btnOk);
        btnCancel   = v.findViewById(R.id.btnCancel);

        tvDayAddNewPlan.setText(Common.f_ddmmy.format(Calendar.getInstance().getTime()));
    }

    public interface BottomSheetListener {
        void sendDataPlan(String namePlan, String dayUpdate);
    }


}
