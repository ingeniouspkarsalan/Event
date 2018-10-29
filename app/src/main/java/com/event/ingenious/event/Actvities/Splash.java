package com.event.ingenious.event.Actvities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.event.ingenious.event.Classes.Animation;
import com.event.ingenious.event.R;
import com.pixplicity.easyprefs.library.Prefs;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Boolean isLoginSucces = Prefs.getBoolean("loginSuccess",false);
        if(isLoginSucces) {
            //start activity..
            Intent intent = new Intent(Splash.this,Home.class);
            startActivity(intent);
        }

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Splash.this,Login.class));
                Animation.fade(Splash.this);
                finish();
            }
        });

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Splash.this,Sign_up.class));
                Animation.shrink(Splash.this);
            }
        });
    }
}
