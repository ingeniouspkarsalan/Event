package com.event.ingenious.event.Actvities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.event.ingenious.event.AppUtils.Endpoints;
import com.event.ingenious.event.AppUtils.Utils;
import com.event.ingenious.event.Classes.Animation;
import com.event.ingenious.event.FCM.SharedPrefManager;
import com.event.ingenious.event.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pixplicity.easyprefs.library.Prefs;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

public class Sign_up extends AppCompatActivity {
    private LinearLayout freezelayout;
    private EditText name,email,password,contact;
    private Button signup;
    private AVLoadingIndicatorView avi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Init();
    }




    private void Init() {
        avi=findViewById(R.id.avi);
        name=findViewById(R.id.name);
        contact=findViewById(R.id.contact);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.signup);
        freezelayout=findViewById(R.id.freezelayout);
        scanning();
    }

    private void scanning() {
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString()) && !TextUtils.isEmpty(contact.getText().toString()) && !TextUtils.isEmpty(name.getText().toString())){
                    String token=SharedPrefManager.getInstance(Sign_up.this).getDeviceToken();
                    Toast.makeText(Sign_up.this,token+" ",Toast.LENGTH_SHORT).show();
                    sign_up(name.getText().toString(),contact.getText().toString(),email.getText().toString(),password.getText().toString(), token);

                }else{
                    name.setError("Not Empty");
                    name.setFocusable(true);
                    contact.setError("Not Empty");
                    contact.setFocusable(true);
                    email.setError("Not Empty");
                    email.setFocusable(true);
                    password.setError("Not Empty");
                    password.setFocusable(true);
                }
            }
        });
    }

    private void sign_up(String names,String contacts,String emails, String passwords,String token)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("req_key","user_registration");
        params.put("name",names);
        params.put("contact",contacts);
        params.put("email",emails);
        params.put("pass",passwords);
        params.put("fcm_id",token);
        client.post(Endpoints.ip_server, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
                freezelayout.setEnabled(false);
                avi.show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = Utils.getResponse(responseBody);
                if(response.equals("null")) {
                    Toasty.warning(Sign_up.this, "Response is null", Toast.LENGTH_SHORT).show();
                }else {

                    try {
                        JSONObject object  = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        if(object.getBoolean("success")) {
                            Toasty.success(Sign_up.this,object.getString("message"),Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(Sign_up.this, Home.class));
                            finish();
                            Animation.slideUp(Sign_up.this);

                        }else {
                            freezelayout.setEnabled(true);
                            avi.smoothToHide();
                            new SweetAlertDialog(Sign_up.this, SweetAlertDialog.ERROR_TYPE)
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
                    Toasty.warning(Sign_up.this, "Unable to Connect Server", Toast.LENGTH_SHORT).show();

                }else {

                    Log.d("response",response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                avi.smoothToHide();
                freezelayout.setEnabled(true);
            }


        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animation.fade(Sign_up.this);
    }
}
