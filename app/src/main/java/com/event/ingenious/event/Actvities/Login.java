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

public class Login extends AppCompatActivity {
    private LinearLayout freezlayout;
    private EditText email,password;
    private Button login;
    private AVLoadingIndicatorView avi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Init();
    }

    private void Init() {
        freezlayout=findViewById(R.id.freezelayout);
        avi=findViewById(R.id.avi);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        scanning();
    }

    private void scanning() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())){
                    String token=SharedPrefManager.getInstance(Login.this).getDeviceToken();
                    Toast.makeText(Login.this,token+" ",Toast.LENGTH_SHORT).show();
                    sign_in(email.getText().toString(),password.getText().toString(),token );
                }else{
                    email.setError("Not Empty");
                    email.setFocusable(true);
                    password.setError("Not Empty");
                    password.setFocusable(true);
                }
            }
        });
    }

    private void sign_in(String email, String password,String token)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("req_key","user_login");
        params.put("email",email);
        params.put("pass",password);
        params.put("token",token);
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
                    Toasty.warning(Login.this, "Response is null", Toast.LENGTH_SHORT).show();
                }else {

                    try {
                        JSONObject object  = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        if(object.getBoolean("success")) {
                            Toasty.success(Login.this,object.getString("message"),Toast.LENGTH_LONG).show();
                            Prefs.putString("user_id",object.getString("id"));
                            Prefs.putString("user_name",object.getString("name"));
                            Prefs.putString("user_email",object.getString("email"));
                            Prefs.putBoolean("loginSuccess",true); // change this value on logout
                            startActivity(new Intent(Login.this, Home.class));
                            finish();
                            Animation.slideUp(Login.this);

                        }else {
                            freezlayout.setEnabled(true);
                            avi.smoothToHide();
                            new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
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
                    Toasty.warning(Login.this, "Unable to Connect Server", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animation.fade(Login.this);
    }
}

