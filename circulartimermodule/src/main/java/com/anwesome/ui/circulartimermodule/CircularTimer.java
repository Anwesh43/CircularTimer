package com.anwesome.ui.circulartimermodule;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.hardware.display.DisplayManager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 06/01/17.
 */
public class CircularTimer {
    private Activity activity;
    private CircularTimerView view;
    private boolean added = false;
    private int w,h;
    private int start = 0;

    public void setTimerListener(TimeListener timerListener) {
        this.timerListener = timerListener;
    }

    private TimeListener timerListener;
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTime() {
        return time;
    }

    private int time = 60;
    public CircularTimer(Activity activity) {
        this.activity = activity;
        view = new CircularTimerView(activity);
        initScreenDimensions();
    }
    public void setTime(int time) {
        this.time = time;
    }
    public void initScreenDimensions() {
        DisplayManager displayManager = (DisplayManager)activity.getSystemService(Context.DISPLAY_SERVICE);
        Display display = displayManager.getDisplay(0);
        if(display!=null) {
            Point size = new Point();
            display.getRealSize(size);
            w = size.x;
            h = size.y;
        }
    }

    public void start() {
        view.setStartAnimating(true);
        view.initPoints();
        if(!added) {
            activity.addContentView(view,new ViewGroup.LayoutParams(w/4,w/4));
            added= true;
        }
        else if(view.getVisibility() == View.INVISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        if(timerListener!=null) {
            timerListener.onStart();
        }
    }
    private class CircularTimerView extends View {
        private boolean startAnimating = false;
        private List<PointF> points = new ArrayList<>();
        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        public void initPoints() {
            points = new ArrayList<>();
        }
        public CircularTimerView(Context context) {
            super(context);
        }
        public CircularTimerView(Context context, AttributeSet attrs) {
            super(context,attrs);
        }
        public void onDraw(Canvas canvas) {
            paint.setColor(Color.parseColor("#9575CD"));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,(2*canvas.getWidth())/5,paint);
            paint.setColor(Color.parseColor("#00838F"));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(25);
            Path path = new Path();
            int index = 0;
            for(PointF point:points) {
                if(index == 0) {
                    path.moveTo(point.x,point.y);
                }
                else {
                    path.lineTo(point.x,point.y);
                }
                canvas.drawPath(path,paint);
                index++;
            }
            paint.setColor(Color.WHITE);
            paint.setTextSize(canvas.getWidth()/3);
            if(start>time) {
                start = time;
            }
            canvas.drawText(""+start,canvas.getWidth()/2-paint.measureText(""+start)/2,canvas.getHeight()/2+paint.measureText(""+start)/2,paint);

            if(startAnimating) {
                float deg = 360*(start*1.0f)/time;
                float radius = (2*canvas.getWidth())/5;
                float x = canvas.getWidth()/2+(float)(radius*Math.cos(deg*Math.PI/180)), y = canvas.getHeight()/2+(float)(radius*Math.sin(deg*Math.PI/180));
                points.add(new PointF(x,y));
                start++;
                if(timerListener!=null) {
                    timerListener.onTick(start);
                }
                if(start > time) {
                    startAnimating = false;
                    start = 0;
                    if(timerListener != null) {
                        timerListener.onStop();
                    }
                }
                try {
                    Thread.sleep(1000);
                    invalidate();
                } catch (Exception ex) {

                }
            }
        }
        public void setStartAnimating(boolean startAnimating) {
            this.startAnimating = startAnimating;
        }
    }
}
