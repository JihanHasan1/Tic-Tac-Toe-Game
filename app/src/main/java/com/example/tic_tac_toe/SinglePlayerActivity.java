package com.example.tic_tac_toe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class SinglePlayerActivity extends AppCompatActivity implements View.OnClickListener {

    Button mButton0, mButton1, mButton2, mButton3, mButton4, mButton5, mButton6, mButton7, mButton8;
    Button restartGameBtn, mButtonBack;
    TextView mTextView;
    int PLAYER_Computer = 0;
    int PLAYER_Human = 1;
    int activePlayer = PLAYER_Computer;
    boolean isGameActive = true;
    int[] filledPositions = {-1, -1, -1, -1, -1, -1, -1, -1, -1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        //Assigning the address of the Buttons
        mTextView = findViewById(R.id.UserTurnTxt);
        mButton0 = findViewById(R.id.Button0);
        mButton1 = findViewById(R.id.Button1);
        mButton2 = findViewById(R.id.Button2);
        mButton3 = findViewById(R.id.Button3);
        mButton4 = findViewById(R.id.Button4);
        mButton5 = findViewById(R.id.Button5);
        mButton6 = findViewById(R.id.Button6);
        mButton7 = findViewById(R.id.Button7);
        mButton8 = findViewById(R.id.Button8);
        restartGameBtn = findViewById(R.id.btnRestart);
        mButtonBack = findViewById(R.id.btnBack);

        //Onclick Listener to get Button tag and set Text to it.
        mButton0.setOnClickListener(this);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);
        mButton7.setOnClickListener(this);
        mButton8.setOnClickListener(this);

        restartGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });

        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        computerPlayer();
    }

    private void computerPlayer() {

        //Generating the random value to click the random button
        int randomNumber = new Random().nextInt(filledPositions.length);

        //Getting the value from the array
        int buttonToClick = filledPositions[randomNumber];
        //checking the button is already clicked or not and setting the text as users turn
        if (buttonToClick == -1) {
            if (randomNumber == 0) {
                mButton0.setText("O");
                filledPositions[randomNumber] = PLAYER_Computer;    //updating the value in Array to identify the button is clicked and to check for winner
                activePlayer = PLAYER_Human;
                mTextView.setText("Your Turn");
            }
            if (randomNumber == 1) {
                mButton1.setText("O");
                filledPositions[randomNumber] = PLAYER_Computer;
                activePlayer = PLAYER_Human;
                mTextView.setText("Your Turn");
            }
            if (randomNumber == 2) {
                mButton2.setText("O");
                filledPositions[randomNumber] = PLAYER_Computer;
                activePlayer = PLAYER_Human;
                mTextView.setText("Your Turn");
            }
            if (randomNumber == 3) {
                mButton3.setText("O");
                filledPositions[randomNumber] = PLAYER_Computer;
                activePlayer = PLAYER_Human;
                mTextView.setText("Your Turn");
            }
            if (randomNumber == 4) {
                mButton4.setText("O");
                filledPositions[randomNumber] = PLAYER_Computer;
                activePlayer = PLAYER_Human;
                mTextView.setText("Your Turn");
            }
            if (randomNumber == 5) {
                mButton5.setText("O");
                filledPositions[randomNumber] = PLAYER_Computer;
                activePlayer = PLAYER_Human;
                mTextView.setText("Your Turn");
            }
            if (randomNumber == 6) {
                mButton6.setText("O");
                filledPositions[randomNumber] = PLAYER_Computer;
                activePlayer = PLAYER_Human;
                mTextView.setText("Your Turn");
            }
            if (randomNumber == 7) {
                mButton7.setText("O");
                filledPositions[randomNumber] = PLAYER_Computer;
                activePlayer = PLAYER_Human;
                mTextView.setText("Your Turn");
            }
            if (randomNumber == 8) {
                mButton8.setText("O");
                filledPositions[randomNumber] = PLAYER_Computer;
                activePlayer = PLAYER_Human;
                mTextView.setText("Your Turn");
            }
        } else {
            //calling the computerPlayer Method if the generated value button is already clicked
            computerPlayer();
        }
        //Checking for winner
        checkWinner();

        //checking for draw
        checkDraw();


    }

    @Override
    public void onClick(View view) {

        //if Game is not Active we will do nothing
        if (!isGameActive)
            return;

        //getting buttons id
        Button clickedBtn = findViewById(view.getId());

        //Getting Tag of the clicked button
        int clickedTag = Integer.parseInt(view.getTag().toString());

        //if the user clicks the Already clicked button we will do nothing
        if (filledPositions[clickedTag] != -1) {
            //Do nothing
            return;

        }
        //we will set update the value in array with the Constant value of user
        filledPositions[clickedTag] = activePlayer;

        //if the Active player is Computer we will set the button text to O and we will set next user as X
        if (activePlayer == PLAYER_Computer) {
            computerPlayer();

        } else {
            clickedBtn.setText("X");
            mTextView.setText("Player Computer Turn");

            //Calling the computerPlayer method to automatically select the next button
            computerPlayer();
        }
        //Checking for winner
        checkWinner();

        //checking for draw
        checkDraw();

    }


    private void checkDraw() {
        int count = 0;

        //we will check all the values in the array and increase count if it is not equal to default value
        for (int i = 0; i < 9; i++) {
            if (filledPositions[i] != -1) {
                count++;
            }
        }

        //if all the values in array are not equal to default value  we will show as the game is draw
        if (count == 9) {
            //Check for whether any player won the game or not before showing draw
            if (checkWinner())
                return;
            showWinner("Game is Draw !");
            isGameActive = false;   //setting the game as inactive
        }
    }

    private boolean checkWinner() {

        //Assigning all the possible ways to win in an Array
        int[][] winningPosition = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

        //Getting all the winning positions
        for (int i = 0; i < 8; i++) {
            int value0 = winningPosition[i][0];
            int value1 = winningPosition[i][1];
            int value2 = winningPosition[i][2];

            //checking whether the value is equal to default value or not
            if (filledPositions[value0] != -1) {
                if (filledPositions[value0] == filledPositions[value1] && filledPositions[value1] == filledPositions[value2]) {

                    //Making the game inActive
                    isGameActive = false;

                    //Deciding winner
                    if (filledPositions[value0] == PLAYER_Computer) {
                        showWinner("Computer is the Winner");


                    } else {
                        showWinner("You are the Winner");
                    }

                    //Returning true to prevent showing the result form draw
                    return true;
                }


            }
        }
        return false;
    }

    private void showWinner(String winner) {
        //Creating a Alert Dialog to show the winner and for Restarting the Game
        new AlertDialog.Builder(this).setTitle(winner).setPositiveButton("Restart Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                restartGame();
            }
        }).show();
    }

    private void restartGame() {
        //Calling the Same Intent Again To Restart the Game
        Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
        startActivity(intent);
    }

}