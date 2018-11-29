package com.event.ingenious.event.Fragement_activity_classes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.event.ingenious.event.R;

public class f_event_detail_comment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ed = inflater.inflate(R.layout.fragment_f_event_detail_comment, container, false);
        return ed;
    }

}
