package com.event.ingenious.event.Fragement_activity_classes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.event.ingenious.event.Adapter.PremimumEventAdapter;
import com.event.ingenious.event.AppUtils.Endpoints;
import com.event.ingenious.event.Classes.JSONParser;
import com.event.ingenious.event.Classes.Premimum_Event_Class;
import com.event.ingenious.event.R;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class Trending_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Premimum_Event_Class> premimum_event_classList;
    PremimumEventAdapter premimumEventAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rv =  inflater.inflate(
                R.layout.activity_trending__fragment, container, false);
        recyclerView = rv.findViewById(R.id.recyclerview);
        requestData(Endpoints.ip_server);
        return rv;
    }

    public void requestData(String uri) {
        final String id = Prefs.getString("user_id", "0");
        StringRequest request = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("null")) {
                    Toasty.warning(getContext(), "Trending Event not available.", Toast.LENGTH_LONG).show();
                } else {
                    premimum_event_classList = JSONParser.prese_premimum_event(response);
                    premimumEventAdapter = new PremimumEventAdapter(getContext(), premimum_event_classList);
                    recyclerView.setAdapter(premimumEventAdapter);
//                    recyclerView.setLayoutManager(new GridLayoutManager(Home.this,1));
                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(llm);
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Some thing went wrong!")
                                .show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("req_key", "all_event");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}
