package com.example.adi.thefatfish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                try {

                    sleep(5000);


                }catch (Exception e) {

                    e.printStackTrace();

                }finally{

                    startTheGame();

                }
            }
        };
        thread.start();
    }

    private void startTheGame()
    {
        Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }
}
