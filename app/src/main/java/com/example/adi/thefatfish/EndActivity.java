package com.example.adi.thefatfish;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    private Button startGameAgain;
    private TextView displayScore;
    private TextView displayHighScore;
    private int score, highScore;
    private String Score;
    private String HighSocre;
    private String New;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);


        startGameAgain = (Button) findViewById(R.id.play_again_button);
        displayHighScore = (TextView) findViewById(R.id.high_score_text);
        displayScore = (TextView) findViewById(R.id.score_text);

        Score = getIntent().getExtras().get("Score").toString();

        score = Integer.parseInt(Score);

        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE", 0);

        if(score > highScore)
        {
            displayHighScore.setText("High Score: " + score);

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.commit();
        }
        else
        {
            displayHighScore.setText("High Score: " + highScore);
        }


        displayScore.setText("Your Score: " + Score);

        startGameAgain.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                restartGame();
            }
        });
    }

    private void restartGame()
    {
        Intent mainIntent = new Intent(EndActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }
}
