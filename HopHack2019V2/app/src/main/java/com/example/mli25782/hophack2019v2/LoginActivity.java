package com.example.mli25782.hophack2019v2;

import android.os.Bundle;
import android.support.design.widget.*;
import android.support.design.widget.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging fm = FirebaseMessaging.getInstance();
                fm.send(new RemoteMessage.Builder("8084023471" + "@gcm.googleapis.com")
                        .setMessageId(Integer.toString(random.nextInt(9999)))
                        .addData("my_message", "Hello World")
                        .addData("my_action","SAY_HELLO")
                        .build());
                //Log.d("Device ID", FirebaseInstanceId.getInstance().getToken());
            }
        });
    }

}
