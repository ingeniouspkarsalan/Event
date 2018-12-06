
package com.event.ingenious.event.Actvities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.event.ingenious.event.AppUtils.Endpoints;
import com.event.ingenious.event.AppUtils.Utils;
import com.event.ingenious.event.Classes.Animation;
import com.event.ingenious.event.R;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pixplicity.easyprefs.library.Prefs;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

public class Create_Event extends AppCompatActivity  {
    EditText ev_title, ev_des, ev_price;
    TextView ev_date,  ev_str_time, ev_end_time,show_ev_date,  show_ev_str_time, show_ev_end_time;
    Spinner ev_category, ev_paid;
    ImageView imagebanner;
    Calendar ev_date_picker;
    Button ev_cre_btn;
    ArrayList<String> cat_names;
    String[] is_paid = {"No","Yes"};

    private String photoPath = "";
    LinearLayout freezlayout;
    AVLoadingIndicatorView avi;

    public static final int PICK_IMAGE = 1;
    private final static int PLACE_PICKER_REQUEST = 999;
    TextView address;
    String placeName="";
    double latitude =0;
    double longitude=0;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Create Event Form");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        address=findViewById(R.id.show_add);
        Init();
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    // for activty
                    startActivityForResult(builder.build(Create_Event.this), PLACE_PICKER_REQUEST);
                    // for fragment
                    //startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void Init() {

        freezlayout=findViewById(R.id.freezlayout);
        avi=findViewById(R.id.avi);

        ev_date_picker = Calendar.getInstance();
        ev_title =  findViewById(R.id.ev_title);
        ev_date =  findViewById(R.id.evt_date);
        ev_str_time =  findViewById(R.id.ev_strt_time);
        ev_des =  findViewById(R.id.evt_detail);
        ev_end_time =  findViewById(R.id.ev_end_time);
        ev_category = findViewById(R.id.evt_cate);
        ev_price = findViewById(R.id.ev_price);
        ev_paid = findViewById(R.id.evt_paid);

        show_ev_date =  findViewById(R.id.show_evt_date);
        show_ev_str_time =  findViewById(R.id.show_ev_strt_time);
        show_ev_end_time =  findViewById(R.id.show_ev_end_time);


        imagebanner=findViewById(R.id.logo_image);

        imagebanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.create(Create_Event.this) // Activity or Fragment
                        .folderMode(true) // folder mode (false by default)
                        .toolbarFolderTitle("Select Image") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .single() // single mode
                        .start();
            }
        });

        ev_cre_btn=findViewById(R.id.evt_create);


        cat_names=new ArrayList<>();
        //Load category in Spiner
        load_category_for_spiner();

        //Load Paid Status in Spinner
        paid_spinner();


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
                        show_ev_str_time.setText( selectedHour + ":" + selectedMinute + ":" + AM_PM);
                    }
                }, hour, minute, true);//Yes 24 hour time
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
                        show_ev_end_time.setText( selectedHour + ":" + selectedMinute + ":" + AM_PM);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        ev_cre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(ev_title.getText().toString()) && !TextUtils.isEmpty(ev_des.getText().toString()) && !TextUtils.isEmpty(show_ev_date.getText().toString()) &&
                        !TextUtils.isEmpty(show_ev_str_time.getText().toString()) && !TextUtils.isEmpty(show_ev_end_time.getText().toString()) && !TextUtils.isEmpty(photoPath.toString()) &&
                        !TextUtils.isEmpty(ev_title.getText().toString()) && !TextUtils.isEmpty(ev_title.getText().toString()) && !TextUtils.isEmpty(ev_title.getText().toString()))
                {

                    creating_event(ev_title.getText().toString(),ev_des.getText().toString(),show_ev_date.getText().toString(),show_ev_str_time.getText().toString()
                    ,show_ev_end_time.getText().toString(),placeName,latitude,longitude,ev_category.getSelectedItem().toString(),photoPath,ev_paid.getSelectedItem().toString(),ev_price.getText().toString());
                }else{
                    Toasty.success(Create_Event.this,"Fill all fields",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    try {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data))
        {
            Image image = ImagePicker.getFirstImageOrNull(data);
            photoPath = image.getPath();
            Bitmap bmImg = BitmapFactory.decodeFile(photoPath);
            imagebanner.setImageBitmap(bmImg);
        }

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_REQUEST:
                    Place place = PlacePicker.getPlace(this, data);
                     placeName = String.format("%s", place.getName());
                     latitude = place.getLatLng().latitude;
                     longitude = place.getLatLng().longitude;
                     address.setText(" "+placeName);
            }
        }


    }catch (Exception e){

    }
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

    //Declear Event Paid Status is event free or paid
    //Declear the function for Spiner for data
    private void paid_spinner()
    {
        ev_paid.setAdapter(new ArrayAdapter<String>(Create_Event.this, android.R.layout.simple_spinner_dropdown_item, is_paid));
    }

    //set into date textview
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        show_ev_date.setText(sdf.format(ev_date_picker.getTime()));
    }


    private void creating_event(String tit,String des,String date, String s_time,String end_time,String address,double latit,double longti,String cate,String photo, String paid, String price)
    {
        String id= Prefs.getPreferences().getString("user_id","");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("req_key","create_event");
        params.put("ev_title",tit);
        params.put("ev_desc",des);
        params.put("ev_date",date);
        params.put("ev_start_time",s_time);
        params.put("ev_end_time",end_time);
        params.put("ev_address",address);
        params.put("ev_latitude",latit);
        params.put("ev_longitude",longti);
        params.put("ev_category",cate);
        params.put("ev_create_by",id);
        params.put("is_paid",paid);
        params.put("ev_price",price);
        try{

        params.put("file_name",new File(photo));

        }catch (Exception e){}

        client.post(Endpoints.ip_server, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
                freezlayout.setEnabled(false);
                avi.show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = Utils.getResponse(responseBody);
                if(response.equals("null")) {
                    Toasty.warning(Create_Event.this, "Response is null", Toast.LENGTH_SHORT).show();
                }else {

                    try {
                        JSONObject object  = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        if(object.getBoolean("success")) {
                            Toasty.success(Create_Event.this,object.getString("message"),Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(Sign_up.this, Home.class));
                            finish();
                            Animation.slideUp(Create_Event.this);

                        }else {
                            freezlayout.setEnabled(true);
                            avi.smoothToHide();
                            new SweetAlertDialog(Create_Event.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(object.getString("message"))
                                    .show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("response",response);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String  response  = Utils.getResponse(responseBody);
                if(response.equals("null")) {
                    Toasty.warning(Create_Event.this, "Unable to Connect Server", Toast.LENGTH_SHORT).show();

                }else {

                    Log.d("response",response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                avi.smoothToHide();
                freezlayout.setEnabled(true);
            }


        });
    }




}
