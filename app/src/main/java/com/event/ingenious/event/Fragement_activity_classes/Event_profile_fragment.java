package com.event.ingenious.event.Fragement_activity_classes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.event.ingenious.event.R;

public class Event_profile_fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rv =  inflater.inflate(
                R.layout.activity_event_profile_fragment, container, false);
        return rv;
    }
}
