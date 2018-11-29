package com.event.ingenious.event.Actvities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.event.ingenious.event.Fragement_activity_classes.f_event_detail;
import com.event.ingenious.event.Fragement_activity_classes.f_event_detail_comment;
import com.event.ingenious.event.R;

import java.util.ArrayList;
import java.util.List;

import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;
import cn.hugeterry.coordinatortablayout.listener.LoadHeaderImagesListener;

public class Event_detail extends AppCompatActivity {

    private CoordinatorTabLayout mCoordinatorTabLayout;
    private TabLayout tabLayout;
    String ev_id, ev_name, ev_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        ev_id = getIntent().getStringExtra("event_id");
        ev_name = getIntent().getStringExtra("event_name");
        ev_image = getIntent().getStringExtra("event_image");

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        mCoordinatorTabLayout = (CoordinatorTabLayout) findViewById(R.id.coordinatortablayout);
        mCoordinatorTabLayout.setTitle(ev_name)
                .setBackEnable(true)
                .setLoadHeaderImagesListener(new LoadHeaderImagesListener() {
                    @Override
                    public void loadHeaderImages(ImageView imageView, TabLayout.Tab tab) {
                        Glide.with(Event_detail.this)
                                .load(ev_image)
                                .into(imageView);
                    }
                }).setupWithViewPager(viewPager);

        tabLayout=mCoordinatorTabLayout.getTabLayout();
        tabLayout.setTabTextColors(ContextCompat.getColor(this, android.R.color.white),
                ContextCompat.getColor(this,android.R.color.white));
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.formenu));
    }

    private void setupViewPager(ViewPager viewPager) {
        AdapterforEvent_details adapter = new AdapterforEvent_details(getSupportFragmentManager());
        adapter.addFragment(new f_event_detail(), "Event Detail",ev_id);
        adapter.addFragment(new f_event_detail_comment(), "Comments",ev_id);
        viewPager.setAdapter(adapter);
    }

    static class AdapterforEvent_details extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public AdapterforEvent_details(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String evrnt_title,String event_id)
        {
            mFragments.add(fragment);
            mFragmentTitles.add(evrnt_title);
            Bundle data = new Bundle();//create bundle instance
            data.putString("event_id", event_id);//put string to pass with a key value
            data.putString("event_name", evrnt_title);//put string to pass with a key value
            fragment.setArguments(data);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
