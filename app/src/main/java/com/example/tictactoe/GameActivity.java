/*
This class is used to handle the app's game page. Users are allowed to play a classic tic-tac-toe
 game with another player an unlimited amount of times and can even switch back to the home page to
 change some of the app settings.

Author: Timi Aina
ID: 1777752
Date: Feburary 14, 2025
 */

package com.example.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GameActivity extends AppCompatActivity {

    // Data Field ==========================================================================
    private final int[][] gridIds = {
            {R.id.grid00, R.id.grid01, R.id.grid02},
            {R.id.grid10, R.id.grid11, R.id.grid12},
            {R.id.grid20, R.id.grid21, R.id.grid22}
    };
    private final int rows = gridIds.length;
    private final int cols = gridIds[0].length;
    private final Grid gameGrid = new Grid(rows, cols);
    private final int[][] gridOutlineIds = {
            {R.id.gridOutline00, R.id.gridOutline01, R.id.gridOutline02},
            {R.id.gridOutline10, R.id.gridOutline11, R.id.gridOutline12},
            {R.id.gridOutline20, R.id.gridOutline21, R.id.gridOutline22}
    };

    private final int[][] buttonIds = {
            {R.id.button00, R.id.button01, R.id.button02},
            {R.id.button10, R.id.button11, R.id.button12},
            {R.id.button20, R.id.button21, R.id.button22}
    };

    private HashMap<String, String> colourMap;
    private final Turn playerTurn = new Turn(1);

    private int[][] winningPattern = null;

    // Methods =============================================================================

    // Runs all the desirable activity functions when an activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeService.applyTheme(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.game_activity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initColours(); // Add the color map
        initActivityBtns(); // Add button listeners
        headerText(playerTurn.getTurn()); // Update round info
    }//onCreate

    // Adds the color map of all different player, theme, and cell colours
    public void initColours() {
        colourMap = new HashMap<>();
        colourMap.put("Player1", "#FF0000"); // Red colour in HEX
        colourMap.put("Player2", "#0000FF"); // Blue colour in HEX
        colourMap.put("Empty", "#FFFFFF"); // White colour in HEX
        colourMap.put("LightMode", "#000000"); // Black colour in HEX
        colourMap.put("DarkMode", "#FFFFFF"); // Black colour in HEX
    }//initColours

    // Adds buttons listeners to all the buttons in the GameActivity
    public void initActivityBtns(){
        Button playAgainBtn = findViewById(R.id.playAgainBtn);
        playAgainBtn.setOnClickListener(v-> resetGame());

        Button homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(v -> moveToHome());

        initGridBtns(rows, cols); // Add grid button listeners
    }//initActivityBtns

    // Adds buttons listeners to all the buttons in the grid
    public void initGridBtns(int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Button btn = findViewById(buttonIds[row][col]);
                if (btn != null) {
                    final int btnRow = row;
                    final int btnCol = col;
                    btn.setOnClickListener(v -> setCell(btnRow, btnCol, btn));
                }//if statement
            }//for-loop
        }//for-loop
    }//initGridBtns

    // Moves the user from the game page (GameActivity) to the home page (HomeActivity)
    public void moveToHome(){
        Intent intent = new Intent(GameActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // Close current activity
    }//moveToHome

    // Checks to see whether a player has won the game
    public int[][] checkWin(int player){
        return gameGrid.checkWin(player);
    }//checkWin

    // Update the text for the player's turn or winner
    public void headerText(int player){

        // Update the text for each player
        TextView playerTurnView = findViewById(R.id.roundView);
        playerTurnView.setTextSize(36);

        // Set the text with the player's number (playerTurn.getTurn())
        String playerText = getString(R.string.turnString);
        String message = playerText + playerTurn.getTurn() + ":";
        playerTurnView.setText(message);

        View playerSquare = findViewById(R.id.playerSquare);
        playerSquare.setBackgroundColor(Color.parseColor(colourMap.get("Player" + player)));
    }//headerText

    // Display's the winner in the text box displaying round info.
    public void winnerText() {
        // Update the text for the winner
        TextView roundView = findViewById(R.id.roundView);
        roundView.setTextSize(22);

        // Set the text with the player's number (playerTurn.getTurn())
        String playerText = getString(R.string.turnString);
        String message = playerText + playerTurn.getTurn() + " WINS!";
        roundView.setText(message);
    }//winnerText

    // Triggers all post-win actions
    public void winSequence(){
        disableCells(); // Game state reset
        winnerText(); // UI updates
        winAnimation(); // Animations
    }//winSequence

    /*
    Sets an individual cell to the colour of a player, and marks that cell with
    the player's number internally.
     */
    public void setCell(int row, int col, Button btn){
        btn.setEnabled(false); // Disable button
        gameGrid.setGrid(row, col, playerTurn.getTurn()); // BACKEND: Mark cell with player's number

        View selectCell = findViewById(gridIds[row][col]);
        selectCell.setBackgroundColor(Color.parseColor(getPlayerColour()));

        winningPattern = checkWin(playerTurn.getTurn());
        if (winningPattern != null){ // Player has won the game
            winSequence();
        } else { // Player has not won the game
            playerTurn.switchTurn(); // Switch turn
            headerText(playerTurn.getTurn()); // Update round info
        }//if-else
    }//setCell

    // Returns the current player's colour in HEX
    public String getPlayerColour() {
        return colourMap.get("Player" + playerTurn.getTurn());
    }//getPlayerColour

    // Disables all of the grid cells after a player has won the game
    public void disableCells(){
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Button btn = findViewById(buttonIds[row][col]);
                btn.setEnabled(false); // Disable button
            }//for-loop
        }//for-loop
    }//disableCells

    // Rests the game; all information from the previous game is removed
    public void resetGame(){
        headerText(playerTurn.getTurn());
        toggleInfoText(1);
        resetCells();

        if (winningPattern != null){ // If a player won the previous game
            resetOutline();
        }//if-statement
    }//resetGame

    // Resets the interface of all grid cells to the "Empty" colour
    public void resetCells() {
        gameGrid.resetGrid();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                View selectCell = findViewById(gridIds[row][col]);
                selectCell.setBackgroundColor(Color.parseColor(colourMap.get("Empty"))); // Reset to white
                Button btn = findViewById(buttonIds[row][col]);
                btn.setEnabled(true); // Re-enable button
            }//for-loop
        }//for-loop
    }//resetCells

    // Displays a short, 3-second winner animation highlighting the winning cell pattern
    public void winAnimation() {
        toggleInfoText(0); // Hide informative text

        // Handles the execution of the animation
        Handler handler = new Handler(Looper.getMainLooper());
        int delay = 500; // 0.5 seconds
        int animationDuration = 3000; // 3 seconds
        int toggleAmount = animationDuration / delay; // Times toggling outline

        // Create an animation task
        Runnable flashAnimation = new Runnable() {
            boolean isDefaultOutline = true;
            int amount = 0;

            // Repeatedly toggles the cell outlines; creates a flashing effect
            @Override
            public void run() {
                if (amount >= toggleAmount) {
                    return;
                }//if statement

                for (int[] cellPositions : winningPattern) {

                    // Grab the position of the cell on the grid
                    int row = cellPositions[0];
                    int col = cellPositions[1];

                    // Get the correct grid outline object from the table
                    View cellView = findViewById(gridOutlineIds[row][col]);

                    if (cellView != null) {
                        if (isDefaultOutline) {
                            cellView.setBackgroundResource(R.drawable.rectangle_1);
                        } else {
                            cellView.setBackgroundResource(R.drawable.rectangle_2);
                        }//if-else
                    }//if statement
                }//for-each

                isDefaultOutline = !isDefaultOutline; // Toggle outline
                amount += 1;

                handler.postDelayed(this, delay); // Repeat every 0.5 seconds
            }//run
        };

        handler.post(flashAnimation); // Start animation
    }//winAnimation

    // Resets the outlines from the cells that were part of the winning pattern
    public void resetOutline() {
        for (int[] cellPosition : winningPattern) {

            // Grab the position of the cell on the grid
            int row = cellPosition[0];
            int col = cellPosition[1];

            // Get the correct grid outline object from the table
            int cellId = gridOutlineIds[row][col];
            View cellView = findViewById(cellId);

            if (cellView != null) {
                cellView.setBackgroundResource(R.drawable.rectangle_1); // Reset to old outline
            }//if statement
        }//for-each
        winningPattern = null;
    }//setOriginalOutline

    // Shows/Hides the informative text below the round info
    public void toggleInfoText(int opacity){
        TextView infoText = findViewById(R.id.infoText);
        infoText.setAlpha(opacity);
    }//toggleInfoText
}//GameActivity

