package com.anwesome.ui.timerlibrarydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.anwesome.ui.circulartimermodule.CircularTimer;
import com.anwesome.ui.circulartimermodule.TimeListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CircularTimer circularTimer = new CircularTimer(this);
        circularTimer.setTimerListener(new TimeListener() {
            @Override
            public void onStart() {
                Toast.makeText(MainActivity.this,"Timer has started",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStop() {
                Toast.makeText(MainActivity.this,"Timer has stopped",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTick(int time) {
                Log.d("Time elapsed",""+time);
            }
        });
        circularTimer.start();

    }
}
