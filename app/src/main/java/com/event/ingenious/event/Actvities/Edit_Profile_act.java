package com.event.ingenious.event.Actvities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.event.ingenious.event.AppUtils.Endpoints;
import com.event.ingenious.event.AppUtils.Utils;
import com.event.ingenious.event.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.LabelToggle;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

public class Edit_Profile_act extends AppCompatActivity  {
    EditText name, password, age, contact, locations;
    Spinner gender;
    SweetAlertDialog pd;

    //take user id From Prefs
    String id = Prefs.getString("user_id","0");

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

   private void location_picker(){

    }

    private void init() {

        name =  findViewById(R.id.name);
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
                            //name.setText(object.getString("u_name"));
                            name.setText("Dilawar Khan");
                            password.setText(object.getString("u_pass"));
                            age.setText(object.getString("u_age"));
                            contact.setText(object.getString("u_contact"));
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


}
