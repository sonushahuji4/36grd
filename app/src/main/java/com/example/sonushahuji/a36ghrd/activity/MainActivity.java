package com.example.sonushahuji.a36ghrd.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sonushahuji.a36ghrd.R;
import com.example.sonushahuji.a36ghrd.SharedPreManager.SessionManager;

public class MainActivity extends AppCompatActivity
{
    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Session class instance
        session = new SessionManager(getApplicationContext());

        ImageView imageView = findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        imageView.startAnimation(animation);

        Thread timer = new Thread(){

            @Override
            public void run() {

                try
                {
                    sleep(2000);
            if(session.checkLogin()) {
                        Intent intent = new Intent(getApplicationContext(), HomeDashboard.class);
                        startActivity(intent);
                        finish();
                        super.run();
                    }

                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                    //Toast.makeText(MainActivity.this,"Your Message", Toast.LENGTH_LONG).show();
                }

            }
        };
        timer.start();
    }
}
