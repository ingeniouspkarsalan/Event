package com.event.ingenious.event.Actvities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.event.ingenious.event.Adapter.MyEventAdapter;
import com.event.ingenious.event.AppUtils.Endpoints;
import com.event.ingenious.event.Classes.JSONParser;
import com.event.ingenious.event.Classes.MyEventClass;
import com.event.ingenious.event.R;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class My_Event extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<MyEventClass> myEventClassList;
    MyEventAdapter myEventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Events");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview);
        requestData(Endpoints.ip_server);
    }

    public void requestData(String uri) {
        final String id = Prefs.getString("user_id", "0");
        StringRequest request = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response.contains("null")) {
                    new SweetAlertDialog(My_Event.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("You don't have event.")
                            .show();
                } else {
                    myEventClassList = JSONParser.prese_my_event(response);
                    myEventAdapter = new MyEventAdapter(My_Event.this, myEventClassList);
                    recyclerView.setAdapter(myEventAdapter);
//                    recyclerView.setLayoutManager(new GridLayoutManager(Home.this,1));
                    LinearLayoutManager llm = new LinearLayoutManager(My_Event.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(llm);
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new SweetAlertDialog(My_Event.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Some thing went wrong!")
                                .show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("req_key", "my_event");
                params.put("u_id", id);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(My_Event.this);
        queue.add(request);
    }
}
