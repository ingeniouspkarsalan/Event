
package com.event.ingenious.event.Actvities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;


import com.event.ingenious.event.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Create_Event extends AppCompatActivity {
    EditText ev_title, ev_date,  ev_str_time, ev_end_time,ev_des;
    Spinner ev_category;
    Calendar ev_date_picker;
    Button ev_cre_btn;
    ArrayList<String> cat_names;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Create Event Form");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Init();
    }

    private void Init() {

        ev_date_picker = Calendar.getInstance();
        ev_title =  findViewById(R.id.ev_title);
        ev_date =  findViewById(R.id.evt_date);
        ev_str_time =  findViewById(R.id.ev_strt_time);
        ev_des =  findViewById(R.id.evt_detail);
        ev_end_time =  findViewById(R.id.ev_end_time);
        ev_category = findViewById(R.id.evt_cate);

        cat_names=new ArrayList<>();
        //Load category in Spiner
        load_category_for_spiner();


        // Set Date Picker Listner
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                ev_date_picker.set(Calendar.YEAR, year);
                ev_date_picker.set(Calendar.MONTH, monthOfYear);
                ev_date_picker.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        ev_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Create_Event.this, date, ev_date_picker
                        .get(Calendar.YEAR), ev_date_picker.get(Calendar.MONTH),
                        ev_date_picker.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


       //  Set on Click Listner On Time Form
        ev_str_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Create_Event.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        ev_str_time.setText( selectedHour + ":" + selectedMinute + ":" + AM_PM);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        ev_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Create_Event.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        ev_end_time.setText( selectedHour + ":" + selectedMinute + ":" + AM_PM);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

    }

    //Declear the function for Spiner for data
    private void load_category_for_spiner()
    {
        cat_names.add("Indoor Game");
        cat_names.add("Business Meetup");
        cat_names.add("Networking");
        cat_names.add("Theme Parties");
        cat_names.add("Friends Get togather");
        cat_names.add("Gaming");
        cat_names.add("Live Match");
        ev_category.setAdapter(new ArrayAdapter<String>(Create_Event.this, android.R.layout.simple_spinner_dropdown_item, cat_names));
    }





    //set into date textview
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        ev_date.setText(sdf.format(ev_date_picker.getTime()));
    }
}
