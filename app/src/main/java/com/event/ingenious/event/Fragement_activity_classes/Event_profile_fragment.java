package com.event.ingenious.event.Fragement_activity_classes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.event.ingenious.event.Actvities.Create_Event;
import com.event.ingenious.event.Actvities.My_Event;
import com.event.ingenious.event.Classes.Animation;
import com.event.ingenious.event.R;
import com.pixplicity.easyprefs.library.Prefs;


public class Event_profile_fragment extends Fragment {

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rv =  inflater.inflate(
                R.layout.activity_event_profile_fragment, container, false);

        TextView tv_user_name = rv.findViewById(R.id.tv_user_name);
        String user_name = Prefs.getString("user_name","User");
        tv_user_name.setText(user_name);

        rv.findViewById(R.id.shape_create_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Create_Event.class));
                Animation.fade(getContext());
            }
        });
        rv.findViewById(R.id.shape_my_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), My_Event.class));
                Animation.fade(getContext());
            }
        });
        rv.findViewById(R.id.tv_edit_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rv;
    }
}
