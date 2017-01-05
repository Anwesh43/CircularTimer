package com.anwesome.ui.circulartimermodule;

/**
 * Created by anweshmishra on 06/01/17.
 */
public interface TimeListener {
    void onStart();
    void onStop();
    void onTick(int time);
}
