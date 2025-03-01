/*
 This class is used to handle setting up button listeners for the home and game activities, as well
 as initializing the colour map to assign colours to each player and establish an "Empty" colour.

 BEWARE: The fight between American and UK English variations of the word 'color' is rampant in this
 class.

 Author: Timi Aina
 Date: Feburary 27, 2025
 */

package com.example.tictactoe;

// Android core functionality
import android.app.Activity;
import android.content.Context;

// UI components
import android.graphics.Color;
import android.widget.Button;

// Utility classes
import androidx.core.content.ContextCompat;
import java.util.HashMap;


public class EventListener {

    // Data Field ==========================================================================
    private final Activity activity;

    private final HashMap<String, Integer> colourMap = new HashMap<>();

    // Constructor  ========================================================================

    /**
     * Constructs a ListenerService with the provided context.
     *
     * @param activity - The activity whose buttons will be set up with listeners
     */
    public EventListener(Activity activity) {
        this.activity = activity;
    }//ListenerService

    // Adds buttons listeners to all the buttons in the GameActivity

    /**
     * Adds button listeners to all the buttons ('PLAY AGAIN', 'HOME', and grid buttons)
     * in the GameActivity.
     */
    protected void initGameBtns(){
        Button playAgainBtn = activity.findViewById(R.id.playAgainBtn);
        Button homeBtn = activity.findViewById(R.id.homeBtn);

        // Check if the activity is the GameActivity
        if (activity instanceof GameActivity) {
            GameActivity gameActivity = (GameActivity) activity;
            playAgainBtn.setOnClickListener(v -> gameActivity.resetGame());
            homeBtn.setOnClickListener(v -> gameActivity.moveToHome());

            initGridBtns();
        }//if statement
    }//initGameBtns

    //Adds buttons listeners to all the buttons in the HomeActivity

    /**
     * Adds buttons listeners to all the buttons in the HomeActivity ('PLAY' and 'MODE).
     */
    protected void initHomeBtns(){
        Button playBtn = activity.findViewById(R.id.playBtn);
        Button modeBtn = activity.findViewById(R.id.modeBtn);

        playBtn.setOnClickListener(v -> HomeActivity.moveToGame(v.getContext()));
        modeBtn.setOnClickListener(v -> HomeActivity.toggleTheme(v.getContext()));
    }//initHomeBtns

    /**
     * Adds button listeners to all the grid buttons.
     */
    protected void initGridBtns() {
        for (int row = 0; row < GameActivity.rows; row++) {
            for (int col = 0; col < GameActivity.cols; col++) {
                Button btn = activity.findViewById(GameActivity.buttonIds[row][col]);
                if (btn != null) {
                    final int btnRow = row;
                    final int btnCol = col;
                    btn.setOnClickListener(v -> ((GameActivity) activity).setCell(activity, btnRow, btnCol, btn));
                }//if statement
            }//for-loop
        }//for-loop
    }//initGridBtns

    /**
     * Initializes the color map containing the assigned player colours as well as the
     * "Empty" colour as the default colour for grid cells.
     *
     * @param context - The app context for retrieving colour resources
     * @return - A HashMap mapping player and game elements to their assigned colours
     */
    protected HashMap<String, Integer> initColours(Context context) {

        // Assign colours for each player
        colourMap.put("Player1", Color.parseColor("#FF0000"));
        colourMap.put("Player2", Color.parseColor("#0000FF"));

        // Retrieve colour resource for "Empty"
        int emptyColour = ContextCompat.getColor(context, R.color.defaultGridColour);
        colourMap.put("Empty", emptyColour);
        return colourMap;
    }//initColours
}//ListenerService
