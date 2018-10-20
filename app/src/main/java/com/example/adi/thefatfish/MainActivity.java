package com.example.adi.thefatfish;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{
    private ObjectView gameView;
    private Handler handler = new Handler();
    private final static long interval = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new ObjectView(this);
        setContentView(gameView);

        Timer timer = new Timer();

        //Schedule the timer, after the first 0ms the TimerTask will run every 30ms

        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        //"Redraw on Screen" and calls "OnDraw()" Method
                        //If you implement a custom view, you will need to call invalidate() whenever the backing model changes and you need to redraw your view.
                        // It can also be used to create simple animations, where you change state, then call invalidate(), change state again, etc.
                        gameView.invalidate();
                    }
                });
            }
        }, 0, interval);
    }
}
