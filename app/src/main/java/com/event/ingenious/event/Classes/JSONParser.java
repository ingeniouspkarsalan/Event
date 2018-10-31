package com.event.ingenious.event.Classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser
{
    static List<Premimum_Event_Class> premimum_event_classList;
    static List<MyEventClass> myEventClassList;

    public static List<Premimum_Event_Class> prese_premimum_event(String content)
    {
        JSONArray jsonArray = null;
        Premimum_Event_Class premimum_event_class = null;
        try
        {
            jsonArray = new JSONArray(content);
            premimum_event_classList = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                premimum_event_class = new Premimum_Event_Class();

                premimum_event_class.setEv_id(jsonObject.getString("ev_id"));
                premimum_event_class.setEv_title(jsonObject.getString("ev_title"));
                premimum_event_class.setEv_desc(jsonObject.getString("ev_desc"));
                premimum_event_class.setEv_date(jsonObject.getString("ev_date"));
                premimum_event_class.setEv_start_time(jsonObject.getString("ev_start_time"));
                premimum_event_class.setEv_end_time(jsonObject.getString("ev_end_time"));
                premimum_event_class.setEv_address(jsonObject.getString("ev_address"));
                premimum_event_class.setEv_latitude(jsonObject.getString("ev_latitude"));
                premimum_event_class.setEv_longitude(jsonObject.getString("ev_longitude"));
                premimum_event_class.setEv_category(jsonObject.getString("ev_category"));
                premimum_event_class.setEv_image(jsonObject.getString("ev_image"));
                premimum_event_class.setEv_created_by(jsonObject.getString("ev_create_by"));

                premimum_event_classList.add(premimum_event_class);
            }
            return premimum_event_classList;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static List<MyEventClass> prese_my_event(String content)
    {
        JSONArray jsonArray = null;
        MyEventClass myEventClass = null;
        try
        {
            jsonArray = new JSONArray(content);
            myEventClassList = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                myEventClass = new MyEventClass();

                myEventClass.setEv_id(jsonObject.getString("ev_id"));
                myEventClass.setEv_title(jsonObject.getString("ev_title"));
                myEventClass.setEv_category(jsonObject.getString("ev_category"));
                myEventClass.setEv_address(jsonObject.getString("ev_address"));
                myEventClass.setEv_date(jsonObject.getString("ev_date"));
                myEventClass.setEv_image(jsonObject.getString("ev_image"));

                myEventClassList.add(myEventClass);
            }
            return myEventClassList;
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
