package com.example.adi.thefatfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ObjectView extends View
{
    private Bitmap fish[] = new Bitmap[2];
    private int fishX = 10, fishY, fishSpeed;
    private int canvasWidth, canvasHeight;
    private Bitmap backgroundImage;
    private Paint score = new Paint();
    private Bitmap health[] = new Bitmap[2];

    private int yellowX, yellowY, yellowSpeed = 16;
    private Paint yellowPaint = new Paint();

    private int blackX, blackY, blackSpeed = 24;
    private Paint blackPaint = new Paint();

    private int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed = 22;
    private Paint redPaint = new Paint();

    private int scorePoints, healthPoints;


    private boolean touch = false;

    public ObjectView(Context context)
    {
        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish3);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        blackPaint.setColor(Color.BLACK);
        blackPaint.setAntiAlias(false);


        score.setColor(Color.WHITE);
        score.setTextSize(70);
        score.setTypeface(Typeface.DEFAULT_BOLD);
         score.setAntiAlias(true);

        health[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        health[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishY = 550;
        scorePoints = 0;
        healthPoints = 3;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(backgroundImage, 0, 0, null);

        int minimFishY = fish[0].getHeight();
        //Log.d("MyActivity", "MinimFishY " + minimFishY);
        int maximFishY = canvasHeight - fish[0].getHeight() * 2;
        //Log.d("MyActivity", "MaximFishY " + maximFishY);

        fishY = fishY + fishSpeed;

        if(fishY < minimFishY)
        {
            fishY = minimFishY;
        }

        if(fishY > maximFishY)
        {
            fishY = maximFishY;
        }

        fishSpeed = fishSpeed + 2;

        if(touch)
        {
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        }
        else
        {
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }

        yellowX = yellowX - yellowSpeed;

        if(hitBallChecker(yellowX, yellowY))
        {
            scorePoints += 10;
            yellowX = -100;
        }

        if(yellowX < 0)
        {
            yellowX = canvasWidth + 1;
            yellowY = (int) Math.floor(Math.random() * (maximFishY - minimFishY)) + minimFishY;
        }

        canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);


        greenX = greenX - greenSpeed;

        if(hitBallChecker(greenX, greenY))
        {
            scorePoints += 20;
            greenX = -100;
        }

        if(greenX < 0)
        {
            greenX = canvasWidth + 20;
            greenY = (int) Math.floor(Math.random() * (maximFishY - minimFishY)) + minimFishY;
        }

        canvas.drawCircle(greenX, greenY, 15, greenPaint);

        redX = redX - redSpeed;

        if(hitBallChecker(redX, redY))
        {
            scorePoints -= 10;
            redX = -100;
            healthPoints--;

            if(healthPoints == 0)
            {
                //Toast.makeText(getContext(), "Game Over!", Toast.LENGTH_SHORT).show();

                Intent endIntent = new Intent(getContext(), EndActivity.class);
                endIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                endIntent.putExtra("Score", scorePoints);
                getContext().startActivity(endIntent);
            }
        }

        if(redX < 0)
        {
            redX = canvasWidth + 20;
            redY = (int) Math.floor(Math.random() * (maximFishY - minimFishY)) + minimFishY;
        }

        canvas.drawCircle(redX, redY, 25, redPaint);

        blackX = blackX - blackSpeed;

        if(hitBallChecker(blackX, blackY))
        {
            blackX = -100;
            healthPoints -= 3;

            if(healthPoints <= 0)
            {
                //Toast.makeText(getContext(), "Game Over!", Toast.LENGTH_SHORT).show();

                Intent endIntent = new Intent(getContext(), EndActivity.class);
                endIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                endIntent.putExtra("Score", scorePoints);
                getContext().startActivity(endIntent);
            }
        }

        if(blackX < 0)
        {
            blackX = canvasWidth + 20;
            blackY = (int) Math.floor(Math.random() * (maximFishY - minimFishY)) + minimFishY;
        }

        canvas.drawCircle(blackX, blackY, 25, blackPaint);


        canvas.drawText("Score : " + scorePoints, 20 , 60, score);

        for(int i = 0; i < 3; ++i)
        {
            int x = (int) (580 + health[0].getWidth() * 1.5 * i);
            int y = 30;


            if(i < healthPoints)
            {
                canvas.drawBitmap(health[0], x, y, null);
            }
            else
            {
                canvas.drawBitmap(health[1], x, y, null);
            }
        }



    }

    public boolean hitBallChecker(int x, int y)
    {
        if(fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight()))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            touch = true;

            fishSpeed = -25;
        }

        return true;
    }
}
