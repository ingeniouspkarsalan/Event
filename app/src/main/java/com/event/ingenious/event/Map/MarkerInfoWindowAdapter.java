package com.event.ingenious.event.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.event.ingenious.event.Classes.for_markers_events;
import com.event.ingenious.event.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
    private ArrayList<for_markers_events> for_markers_eventsList;
    public MarkerInfoWindowAdapter(Context context,Intent list) {
        this.context = context.getApplicationContext();
        this.for_markers_eventsList=list.getParcelableArrayListExtra("data_for_marker");
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        return null;
    }

    @Override
    public View getInfoContents(Marker arg0) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup infoWindow = (ViewGroup) inflater.inflate(R.layout.info_popup_marker, null);

        try {
            LatLng latLng = arg0.getPosition();
            TextView name = infoWindow.findViewById(R.id.ev_map_title);
            TextView contact = infoWindow.findViewById(R.id.ev_map_des);
            TextView city = infoWindow.findViewById(R.id.ev_map_address);
            ImageView image = infoWindow.findViewById(R.id.ev_map_img);
            if (for_markers_eventsList.size() > 0) {

                String id = arg0.getId();
                id = id.replace("m","");
                int i=Integer.parseInt(id);
                name.setText(for_markers_eventsList.get(i).getEv_title());
                name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_short_text_black_24dp,0,0,0);
                contact.setText(for_markers_eventsList.get(i).getEv_desc());
                contact.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_assignment_black_24dp,0,0,0);
                city.setText(for_markers_eventsList.get(i).getEv_address());
                city.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_place_black_24dp,0,0,0);
                if(for_markers_eventsList.get(i).getEv_image().isEmpty()){
                    image.setImageResource(R.drawable.b1);
                }else {
                    Glide.with(context).load(for_markers_eventsList.get(i).getEv_image()).into(image);
                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }



        return infoWindow;
    }
}