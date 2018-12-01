package com.event.ingenious.event.Classes;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class for_markers_events {

    String ev_id, ev_title, ev_desc, ev_date, ev_start_time, ev_end_time, ev_address, ev_category, ev_image, ev_created_by,ev_premimum,ev_trending;
    double ev_latitude, ev_longitude;


    public for_markers_events(String ev_id, String ev_title, String ev_desc, String ev_date, String ev_start_time, String ev_end_time, String ev_address, double ev_latitude, double ev_longitude, String ev_category, String ev_image, String ev_created_by, String ev_premimum, String ev_trending) {
        this.ev_id = ev_id;
        this.ev_title = ev_title;
        this.ev_desc = ev_desc;
        this.ev_date = ev_date;
        this.ev_start_time = ev_start_time;
        this.ev_end_time = ev_end_time;
        this.ev_address = ev_address;
        this.ev_latitude = ev_latitude;
        this.ev_longitude = ev_longitude;
        this.ev_category = ev_category;
        this.ev_image = ev_image;
        this.ev_created_by = ev_created_by;
        this.ev_premimum = ev_premimum;
        this.ev_trending = ev_trending;
    }

    public String getEv_id() {
        return ev_id;
    }

    public String getEv_title() {
        return ev_title;
    }

    public String getEv_desc() {
        return ev_desc;
    }

    public String getEv_date() {
        return ev_date;
    }

    public String getEv_start_time() {
        return ev_start_time;
    }

    public String getEv_end_time() {
        return ev_end_time;
    }

    public String getEv_address() {
        return ev_address;
    }

    public double getEv_latitude() {
        return ev_latitude;
    }

    public double getEv_longitude() {
        return ev_longitude;
    }

    public String getEv_category() {
        return ev_category;
    }

    public String getEv_image() {
        return ev_image;
    }

    public String getEv_created_by() {
        return ev_created_by;
    }

    public String getEv_premimum() {
        return ev_premimum;
    }

    public String getEv_trending() {
        return ev_trending;
    }


}
