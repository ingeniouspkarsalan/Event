package com.event.ingenious.event.Actvities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.event.ingenious.event.AppUtils.Endpoints;
import com.event.ingenious.event.AppUtils.Utils;
import com.event.ingenious.event.Classes.Animation;
import com.event.ingenious.event.Classes.LocationHelper;
import com.event.ingenious.event.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.LabelToggle;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

public class Edit_Profile_act extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,ActivityCompat.OnRequestPermissionsResultCallback {
    EditText name, password, age, contact, locations;
    Spinner gender;
    ImageView user_image;
    SweetAlertDialog pd;
    private String photoPath = "";
    private Location mLastLocation;
    double latitude=0;
    double longitude=0;
    String address_="ok";
    LocationHelper locationHelper;
    //take user id From Prefs
    String id = Prefs.getString("user_id","0");
    ArrayList<String> inters=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile_act);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MultiSelectToggleGroup multiDummy = findViewById(R.id.group_dummy);
        String[] dummyText = getResources().getStringArray(R.array.dummy_text);
        for (String text : dummyText) {
            final LabelToggle toggle = new LabelToggle(this);
            toggle.setText(text);
            multiDummy.addView(toggle);
            toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(toggle.isChecked()==true){
                        inters.add(""+toggle.getText()+",");
                    }else if(toggle.isChecked()==false) {
                        if(inters.size()>0){
                            for(int i=0;i<inters.size();i++){
                                if(inters.get(i).equals(toggle.getText().toString()+",")){
                                    inters.remove(i);
                                }
                            }
                        }
                    }
                }
            });
        }


        init();
        location_picker();
    }

   private void location_picker(){

       locationHelper=new LocationHelper(this);
       locationHelper.checkpermission();


       if (locationHelper.checkPlayServices()) {

           // Building the GoogleApi client
           locationHelper.buildGoogleApiClient();
       }

    }

    private void init() {

        name =  findViewById(R.id.names);
        password = findViewById(R.id.passwords);
        age = findViewById(R.id.ages);
        contact = findViewById(R.id.contacts);
        locations = findViewById(R.id.locations);
        gender = findViewById(R.id.genders);
        String[] genders = getResources().getStringArray(R.array.gender);
        gender.setAdapter(new ArrayAdapter<String>(Edit_Profile_act.this, android.R.layout.simple_spinner_dropdown_item, genders));
        user_image=findViewById(R.id.user_profile_pic);
        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.create(Edit_Profile_act.this) // Activity or Fragment
                        .folderMode(true) // folder mode (false by default)
                        .toolbarFolderTitle("Select Image") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .single() // single mode
                        .start();
            }
        });

        locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboardFrom(Edit_Profile_act.this,v);
                mLastLocation=locationHelper.getLocation();

                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    getAddress();

                } else {

                    if(locations.isEnabled())
                        locations.setEnabled(false);

                    showToast("Couldn't get the location. Make sure location is enabled on the device");
                }

            }
        });

        //Initialize the user detail function
        get_user_Detail(id);
    }



    // Get user Detail From Server
    private void get_user_Detail(String id)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("req_key","get_all_user_detail_by_id");
        params.put("u_id",id);
        client.post(Endpoints.ip_server, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                pd = Utils.showProgress(Edit_Profile_act.this);
                pd.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = Utils.getResponse(responseBody);
                if(response.equals("null")) {
                    Toasty.warning(Edit_Profile_act.this, "Response is null", Toast.LENGTH_SHORT).show();
                }else {

                    try {
                        JSONObject object  = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        if(object.getBoolean("success"))
                        {
                            name.setText(object.getString("u_name"));
                            password.setText(object.getString("u_pass"));
                            age.setText(object.getString("u_age"));
                            contact.setText(object.getString("u_contact"));
                            if(!object.getString("u_image").isEmpty()){
                                Glide.with(Edit_Profile_act.this).load(object.getString("u_image")).into(user_image);
                            }

                        }
                        else
                        {
                            new SweetAlertDialog(Edit_Profile_act.this, SweetAlertDialog.ERROR_TYPE)
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
                    Toasty.error(Edit_Profile_act.this, "Unable to connect server", Toast.LENGTH_SHORT).show();

                }else {

                    Log.d("response",response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                pd.dismiss();
            }
        });
    }


    public void getAddress()
    {
        Address locationAddress;

        locationAddress=locationHelper.getAddress(latitude,longitude);

        if(locationAddress!=null)
        {

            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();


            String currentLocation="";

            if(!TextUtils.isEmpty(address)) {


                if (!TextUtils.isEmpty(city) && !TextUtils.isEmpty(country)) {
                    currentLocation += city + "," + country;
                }

                locations.setVisibility(View.GONE);
                locations.setText(currentLocation);
                locations.setVisibility(View.VISIBLE);
                address_=currentLocation;
                if (!locations.isEnabled())
                    locations.setEnabled(true);
            }

        }
        else
            showToast("Something went wrong");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        locationHelper.onActivityResult(requestCode,resultCode,data);
        if (ImagePicker.shouldHandle(requestCode, resultCode, data))
        {
            Image image = ImagePicker.getFirstImageOrNull(data);
            photoPath = image.getPath();
            Bitmap bmImg = BitmapFactory.decodeFile(photoPath);
            user_image.setImageBitmap(bmImg);
            //name.setText(photoPath);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        locationHelper.checkPlayServices();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("Connection failed:", " ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        mLastLocation=locationHelper.getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        locationHelper.connectApiClient();
    }


    // Permission check functions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        locationHelper.onRequestPermissionsResult(requestCode,permissions,grantResults);

    }

    public void showToast(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // Declear the update Function
    private void update(final String name,String pass, String conatct,String age,String gender,double latitude,double longitude,String address,String intrest)
    {
        final String id = Prefs.getString("user_id", "0");
        String email = Prefs.getString("user_email","0");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("req_key","update_user_profile_detail");
        params.put("id",id);
        params.put("name",name);
        params.put("email",email);
        params.put("pass",pass);
        params.put("conatct",conatct);
        params.put("age",age);
        params.put("gender",gender);
        params.put("latitude",latitude);
        params.put("longitude",longitude);
        params.put("address",address);
        params.put("intrest",intrest);

        try {
            params.put("file_name",new File(photoPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        client.post(Endpoints.ip_server, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
                pd = Utils.showProgress(Edit_Profile_act.this);
                pd.show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = Utils.getResponse(responseBody);
                if(response.equals("null")) {
                    Toasty.warning(Edit_Profile_act.this, "Response is null", Toast.LENGTH_SHORT).show();
                }else {

                    try {
                        JSONObject object  = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        if(object.getBoolean("success")) {
                            Toasty.success(Edit_Profile_act.this,object.getString("message"),Toast.LENGTH_LONG).show();
                            Prefs.putString("user_name",name);
                            finish();
                        }else {
                            new SweetAlertDialog(Edit_Profile_act.this, SweetAlertDialog.ERROR_TYPE)
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
                    Toasty.warning(Edit_Profile_act.this, "Unable to Connect Server", Toast.LENGTH_SHORT).show();

                }else {

                    Log.d("response",response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                pd.dismiss();
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        //return super.onSupportNavigateUp();
        finish();
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        Animation.swipeLeft(Edit_Profile_act.this);
        //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.update_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.update) {
            if(!TextUtils.isEmpty(name.getText().toString()) && !TextUtils.isEmpty(password.getText().toString()) && !TextUtils.isEmpty(contact.getText().toString())
                    && !TextUtils.isEmpty(age.getText().toString())&& !TextUtils.isEmpty(gender.getSelectedItem().toString()) && !TextUtils.isEmpty(address_)
                     &&inters.size()>0){
                String ints="";
                for(int i=0;i<inters.size();i++){
                    ints+=inters.get(i)+",";
                }
                update(name.getText().toString(),password.getText().toString(), contact.getText().toString(),age.getText().toString()
                        ,gender.getSelectedItem().toString(),latitude,longitude,address_,ints);
            }else{
                Toast.makeText(this, "Fill up", Toast.LENGTH_SHORT).show();
            }
        }


        return super.onOptionsItemSelected(item);
    }
}
