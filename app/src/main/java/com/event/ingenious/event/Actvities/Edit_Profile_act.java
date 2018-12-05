package com.event.ingenious.event.Actvities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.event.ingenious.event.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.LabelToggle;

public class Edit_Profile_act extends AppCompatActivity implements LocationListener {
    EditText name, password, age, contact, locations;
    Spinner gender;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile_act);
        MultiSelectToggleGroup multiDummy = findViewById(R.id.group_dummy);
        String[] dummyText = getResources().getStringArray(R.array.dummy_text);
        for (String text : dummyText) {
            LabelToggle toggle = new LabelToggle(this);
            toggle.setText(text);
            toggle.setChecked(true);
            multiDummy.addView(toggle);
        }
        init();
    }

    private void init() {
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        age = findViewById(R.id.age);
        contact = findViewById(R.id.contact);
        locations = findViewById(R.id.location);
        gender = findViewById(R.id.gender);
        String[] genders = getResources().getStringArray(R.array.gender);
        gender.setAdapter(new ArrayAdapter<String>(Edit_Profile_act.this, android.R.layout.simple_spinner_dropdown_item, genders));

        locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                
            }
        });
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
