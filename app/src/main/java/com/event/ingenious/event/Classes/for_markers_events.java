package com.event.ingenious.event.Classes;


import android.os.Parcel;
import android.os.Parcelable;

public class for_markers_events implements Parcelable{

    String ev_id, ev_title, ev_desc, ev_date, ev_start_time, ev_end_time, ev_address, ev_category, ev_image, ev_created_by, ev_premimum, ev_trending;
    Double ev_latitude, ev_longitude;


    public for_markers_events(String ev_id, String ev_title, String ev_desc, String ev_date, String ev_start_time, String ev_end_time, String ev_address, Double ev_latitude, Double ev_longitude, String ev_category, String ev_image, String ev_created_by, String ev_premimum, String ev_trending) {
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

    public Double getEv_latitude() {
        return ev_latitude;
    }

    public Double getEv_longitude() {
        return ev_longitude;
    }

    protected for_markers_events(Parcel in) {
        ev_id = in.readString();
        ev_title = in.readString();
        ev_desc = in.readString();
        ev_date = in.readString();
        ev_start_time = in.readString();
        ev_end_time = in.readString();
        ev_address = in.readString();
        ev_category = in.readString();
        ev_image = in.readString();
        ev_created_by = in.readString();
        ev_premimum = in.readString();
        ev_trending = in.readString();

    }

    public static final Creator<for_markers_events> CREATOR = new Creator<for_markers_events>() {
        @Override
        public for_markers_events createFromParcel(Parcel in) {
            return new for_markers_events(in);
        }

        @Override
        public for_markers_events[] newArray(int size) {
            return new for_markers_events[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ev_id);
        dest.writeString(ev_title);
        dest.writeString(ev_desc);
        dest.writeString(ev_date);
        dest.writeString(ev_start_time);
        dest.writeString(ev_end_time);
        dest.writeString(ev_address);
        dest.writeString(ev_category);
        dest.writeString(ev_image);
        dest.writeString(ev_created_by);
        dest.writeString(ev_premimum);
        dest.writeString(ev_trending);

    }
}