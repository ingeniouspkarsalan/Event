package com.event.ingenious.event.Fragement_activity_classes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.event.ingenious.event.AppUtils.Endpoints;
import com.event.ingenious.event.Classes.Animation;
import com.event.ingenious.event.Classes.JSONParser;
import com.event.ingenious.event.Classes.for_markers_events;
import com.event.ingenious.event.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class Map_fragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private List<for_markers_events> for_markers_eventsList;


    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rv =  inflater.inflate(
                R.layout.activity_map_fragment, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return rv;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        requestData(Endpoints.ip_server);
        // Add a marker in Sydney and move the camera
        List<LatLng> locations = new ArrayList<>();
        if(for_markers_eventsList.size() > 0){
            for(int i=0;i<for_markers_eventsList.size();i++){
//                if(for_markers_eventsList.get(i).getEv_latitude() !=0 && for_markers_eventsList.get(i).getEv_longitude() !=0){
//                    locations.add(new LatLng(for_markers_eventsList.get(i).getEv_latitude(),for_markers_eventsList.get(i).getEv_longitude()));
//                    mMap.addMarker(new MarkerOptions().position(new LatLng(for_markers_eventsList.get(i).getEv_latitude(),for_markers_eventsList.get(i).getEv_longitude())).title(for_markers_eventsList.get(i).getEv_title()));
//
//                }
            }

            //LatLngBound will cover all your marker on Google Maps
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(locations.get(0)); //Taking Point A (First LatLng)
            builder.include(locations.get(locations.size() - 1)); //Taking Point B (Second LatLng)
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
            mMap.moveCamera(cu);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);



        }else {
            Toast.makeText(getContext(),"",Toast.LENGTH_SHORT).show();
        }
    }


    public void requestData(String uri) {

        StringRequest request = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("null")) {
                    Toasty.warning(getContext(), "Events not available.", Toast.LENGTH_LONG).show();
                } else {
                    for_markers_eventsList = JSONParser.prese_map_event(response);

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
                params.put("req_key", "all_event_for_map");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}
