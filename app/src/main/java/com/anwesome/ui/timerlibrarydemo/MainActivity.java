package com.anwesome.ui.timerlibrarydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anwesome.ui.circulartimermodule.CircularTimer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CircularTimer circularTimer = new CircularTimer(this);
        circularTimer.start();
    }
}
