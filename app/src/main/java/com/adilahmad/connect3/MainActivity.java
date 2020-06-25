package com.adilahmad.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean gameIsActive = true;
    // 0: player1(purple)
    // 1: player2(pink)
    int counterColour = 0;
    // gamestate 2 means unplayed
    int gamestate[] = {2,2,2,2,2,2,2,2,2};
    int winpositions[][] = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void dropIn(View view){
        ImageView counter = (ImageView) view;
        System.out.println(counter.getTag().toString());
        int tag = Integer.parseInt(counter.getTag().toString());
        if (gamestate[tag] == 2 && gameIsActive) {
            gamestate[tag] = counterColour;
            counter.setTranslationY(-1000f);
            if (counterColour == 0) {
                counter.setImageResource(R.drawable.purple);
                counterColour = 1;
            } else {
                counter.setImageResource(R.drawable.magenta);
                counterColour = 0;
            }
            counter.animate().translationYBy(1000f).setDuration(300);
            for(int position[]: winpositions){
                if(gamestate[position[0]] == gamestate[position[1]] &&
                        gamestate[position[1]] == gamestate[position[2]] &&
                        gamestate[position[0]] != 2){

                    System.out.println(gamestate[position[0]]);

                    // someone has won!
                    gameIsActive = false;
                    TextView message = (TextView)findViewById(R.id.winner);
                    if (gamestate[position[0]] == 0)
                        message.setText("Player1 Wins");
                    else
                        message.setText("Player2 Wins");
                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                }
                else{
                    boolean gameover = true;
                    for (int value: gamestate){
                        if(value == 2) gameover = false;
                    }
                    if (gameover){
                        gameIsActive = false;
                        TextView message = (TextView)findViewById(R.id.winner);
                        message.setText("Draw");
                        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view){
        gameIsActive = true;

        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        counterColour = 0;
        for(int i=0; i<gamestate.length; i++){
            gamestate[i] = 2;
        }

        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        for(int i=0; i<gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
