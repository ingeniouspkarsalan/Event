package com.event.ingenious.event.Actvities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.event.ingenious.event.Fragement_activity_classes.Event_profile_fragment;
import com.event.ingenious.event.Fragement_activity_classes.Map_fragment;
import com.event.ingenious.event.Fragement_activity_classes.News_Fragment;
import com.event.ingenious.event.Fragement_activity_classes.Premium_Fragment;
import com.event.ingenious.event.Fragement_activity_classes.Trending_Fragment;
import com.event.ingenious.event.FragmentAdaptor.Tablayout_Fragment;
import com.event.ingenious.event.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class Home extends AppCompatActivity {
    private TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_map_black_24dp,
            R.drawable.premium,
            R.drawable.trending,
            R.drawable.ic_wifi_tethering_black_24dp,
            R.drawable.news,
            R.drawable.profile
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try{
            if(getIntent().getStringExtra("onetime").equals("yes")){
                startActivity(new Intent(Home.this, Edit_Profile_act.class));
            }
        }catch (Exception e){}
        Init();
    }

    private void Init() {
        tabLayout=(TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
        tabLayout.getTabAt(5).setIcon(tabIcons[5]);

    }

    private void setupViewPager(ViewPager viewPager) {
        Tablayout_Fragment adapter = new Tablayout_Fragment(getSupportFragmentManager());
        adapter.addFragment(new Map_fragment(), "Map");
        adapter.addFragment(new Premium_Fragment(), "Premium");
        adapter.addFragment(new Trending_Fragment(), "Trending");
        adapter.addFragment(new Trending_Fragment(), "Live");
        adapter.addFragment(new News_Fragment(), "News");
        adapter.addFragment(new Event_profile_fragment(), "Profile");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animation.fade(Home.this);
        moveTaskToBack(true);
        System.exit(1);
    }


}
