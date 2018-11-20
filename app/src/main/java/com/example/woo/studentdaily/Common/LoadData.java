package com.example.woo.studentdaily.Common;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.woo.studentdaily.Plan.Model.Event;
import com.example.woo.studentdaily.Plan.Model.Plan;
import com.example.woo.studentdaily.Server.Server;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoadData {
    public static void loadDataPlan(final Context context, final ArrayList<Plan> plans) {
        final FirebaseAuth nAuth = FirebaseAuth.getInstance();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.patchSelectPlan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    String code = nAuth.getCurrentUser().getUid();
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if (jsonObject.getString("codeuser").equals(code)){
                                Plan plan = new Plan();
                                plan.setId(jsonObject.getInt("id"));
                                plan.setCodeUser(jsonObject.getString("codeuser"));
                                plan.setName(jsonObject.getString("name"));
                                plan.setUpdateDay(jsonObject.getString("updateday"));
                                plans.add(plan);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Common.setListPlan(plans, context);
                    Toast.makeText(context, "Loaded plan success", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Plan", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public static void loadDataEvent(final Context context, final ArrayList<Event> events) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.patchSelectEvent, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Event event = new Event();
                            event.setId(jsonObject.getInt("id"));
                            event.setIdPlan(jsonObject.getInt("idplan"));
                            event.setName(jsonObject.getString("name"));
                            event.setPlace(jsonObject.getString("place"));
                            event.setStartTime(jsonObject.getString("starttime"));
                            event.setEndTime(jsonObject.getString("endtime"));
                            event.setPriority(jsonObject.getInt("priority"));
                            event.setRemind(jsonObject.getInt("remind"));
                            event.setDescribe(jsonObject.getString("describe"));
                            events.add(event);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Common.setListEvent(events, context);
                    Toast.makeText(context, "Loaded event success", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Event", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }


}
