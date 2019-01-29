package com.example.preetam.tranny;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.ViewGroup;
import android.view.View;
import android.view.MotionEvent;
import android.os.Handler;
import android.os.Message;


public class MainActivity extends AppCompatActivity implements SensorEventListener {


    ImageView imageView;
    Button button;
    long animationDuration = 500;
    private QueueAccl mQueueAccl;
    private Sensor mSensor;
    private SensorManager mSensorManager;
    long lastUpdate = 0;
    long trapUpdate = 0;
    float x2;
    float y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        //button = (Button)findViewById(R.id.button);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mSensorManager.registerListener(this, mSensor, mSensorManager.SENSOR_DELAY_NORMAL);
        mQueueAccl = new QueueAccl();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    Pos mPos = new Pos();

    @Override

    // Introduce Multi threading over here


    public void onSensorChanged(SensorEvent event) {
        long currtime = System.currentTimeMillis();


        double x = event.values[0];

        double y = event.values[1];

        double z = event.values[2];


        if (Math.abs(currtime - lastUpdate) > 500) {
//
            lastUpdate = currtime;
            QueueAccl.Add(x, y, z, currtime);


        }
        if (Math.abs(currtime - trapUpdate) > 2000) {
        }


        Runnable r = new Runnable() {//a
            @Override
            public void run() {

                mPos.init();
                mPos.trap_pos();
                mPos.trap_vel();
            }
        };
        Thread astThread = new Thread(r);
        astThread.start();

        Runnable r1 = new Runnable() {//b
            @Override
            public void run() {
                int i;
                i = 0;
                if(i>100)
                {
                    i=0;
                }
                double x1 = QueueAccl.Queue[i].x;
                double y1 = QueueAccl.Queue[i].y;
                double z1 = QueueAccl.Queue[i].z;

                x2 = (float) x1;
                y2 = (float) y1;
                i++;

                handler.sendEmptyMessage(0);
            }

        };
        Thread bsThread = new Thread(r1);
        bsThread.start();

    }


    long curtime = System.currentTimeMillis();




    public void handleAnimation(View view, float x, float y) {
        ObjectAnimator animatorx = ObjectAnimator.ofFloat(imageView, "x", x);
        ObjectAnimator animatory = ObjectAnimator.ofFloat(imageView, "y", y);
        animatorx.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorx, animatory);
        animatorSet.start();


    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (Math.abs(curtime - lastUpdate) > 500) {
                lastUpdate = curtime;
                handleAnimation(imageView, x2, y2);
            }
        }




    };
}
