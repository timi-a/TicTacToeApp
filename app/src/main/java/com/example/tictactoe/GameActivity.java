/*
 This class is used to handle the app's game page. Users are allowed to play a classic tic-tac-toe
 game with another player an unlimited amount of times and can even switch back to the home page to
 change some of the app settings.

 BEWARE: The fight between American and UK English variations of the word 'color' is rampant in this
 class.

 Author: Timi Aina
 Date: Feburary 14, 2025
 */

package com.example.tictactoe;

// Android core functionality
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

// AndroidX support libraries for UI and compatibility
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Utility classes
import java.util.HashMap;
import java.util.Objects;

// UI components
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GameActivity extends AppCompatActivity {

    // Data Field ==========================================================================
    private static final int[][] gridIds = {
            {R.id.grid00, R.id.grid01, R.id.grid02},
            {R.id.grid10, R.id.grid11, R.id.grid12},
            {R.id.grid20, R.id.grid21, R.id.grid22}
    };
    protected static final int rows = gridIds.length;
    protected static final int cols = gridIds[0].length;
    private static final Grid gameGrid = new Grid(rows, cols);
    protected static final int[][] gridOutlineIds = {
            {R.id.gridOutline00, R.id.gridOutline01, R.id.gridOutline02},
            {R.id.gridOutline10, R.id.gridOutline11, R.id.gridOutline12},
            {R.id.gridOutline20, R.id.gridOutline21, R.id.gridOutline22}
    };

    protected static final int[][] buttonIds = {
            {R.id.button00, R.id.button01, R.id.button02},
            {R.id.button10, R.id.button11, R.id.button12},
            {R.id.button20, R.id.button21, R.id.button22}
    };

    private final EventListener buttonListener  = new EventListener(this);

    private static final Turn playerTurn = new Turn(1);

    private final Animation flashAnimator = new Animation(this);

    private static boolean tieGame;

    static HashMap<String, Integer> colourMap;

    // Methods =============================================================================

    /**
     * Initializes the activity and sets up the game's starting state.
     *
     * @param savedInstanceState - A possible saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Theme.applyTheme(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.game_activity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        colourMap = buttonListener.initColours(this); // Maps player's turn to a colour

        buttonListener.initGameBtns();
        roundInfo(this, playerTurn.getTurn());
    }//onCreate

    /**
     * Moves the user from the game page (GameActivity) to the home page (HomeActivity).
     */
    protected void moveToHome(){
        resetGame();

        Intent intent = new Intent(GameActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // Close current activity
    }//moveToHome

    /**
     * Updates the text displaying the current player's turn or the winner.
     *
     * @param player - The current player's number
     */

    private static void roundInfo(Activity activity, int player) {
        final int ROUND_TEXT_SIZE = 36;

        TextView playerTurnView = activity.findViewById(R.id.roundView);
        if (playerTurnView != null) {

            // Update the text
            playerTurnView.setTextSize(ROUND_TEXT_SIZE);

            // Set the text with the player's number (playerTurn.getTurn())
            String playerText = activity.getString(R.string.turnString);
            String message = playerText + player + ":";
            playerTurnView.setText(message);
        }//if statement

        // Change the square view to the player's colour
        View playerSquare = activity.findViewById(R.id.playerSquare);
        if (playerSquare != null) {

            Integer playerColour = Objects.requireNonNullElse(colourMap.get("Player" +
                    playerTurn.getTurn()), Color.WHITE);
            playerSquare.setBackgroundColor(playerColour);
        }//if statement
    }//roundInfo

    /**
     * Displays a message in the header, indicating the game has ended in a tie.
     */
    private void tieInfo() {
        final int TIE_TEXT_SIZE = 25;

        // Hide the player square
        View playerSquare = findViewById(R.id.playerSquare);
        playerSquare.setVisibility(View.INVISIBLE);

        // Hide the round text
        TextView roundView = findViewById(R.id.roundView);
        roundView.setVisibility(View.INVISIBLE);

        // Create the text message
        TextView tieText = findViewById(R.id.tieText);
        String message = "NO PLAYER WINS!";

        // Display the tie message
        tieText.setVisibility(View.VISIBLE);
        tieText.setTextSize(TIE_TEXT_SIZE);
        tieText.setText(message);
    }//tieInfo


    /**
     * Implements all the tasks needed to do after a tie.
     */
    private void tieSequence(){
        disableCells();
        tieInfo();
    }//tieSequence


    /**
     * Displays the winner in the header, indicating the game result.
     */
    private static void winnerInfo(Activity activity) {
        final int WINNER_TEXT_SIZE = 22;

        TextView roundView = activity.findViewById(R.id.roundView);
        if (roundView != null) {
            roundView.setTextSize(WINNER_TEXT_SIZE);

            // Set the text with the player's number (playerTurn.getTurn())
            String playerText = activity.getString(R.string.turnString);
            String message = playerText + playerTurn.getTurn() + " WINS!";
            roundView.setText(message);
        }//if statement
    }//winnerInfo

    /**
     * Implements all the tasks needed to do after a win.
     */
    private void winSequence(){
        disableCells();
        GameActivity.winnerInfo(this);
        flashAnimator.winAnimSequence();
    }//winSequence

    /**
     * Sets an individual cell to the colour of the current player and marks the cell internally
     * as a number.
     *
     * @param row - The row index of the cell
     * @param col - The column index of the cell
     * @param btn - The button representing the cell
     */
    protected void setCell(Activity activity, int row, int col, Button btn) {
        btn.setEnabled(false); // Disable button
        gameGrid.setGrid(row, col, playerTurn.getTurn()); // BACKEND: Mark cell with player's number

        View selectCell = activity.findViewById(gridIds[row][col]);
        selectCell.setBackgroundColor(getPlayerColour());

        if (activity instanceof GameActivity) {
            GameActivity gameActivity = (GameActivity) activity;

            gameActivity.flashAnimator.winningPattern = gameGrid.checkWin(playerTurn.getTurn());
            if (gameActivity.flashAnimator.winningPattern != null) { // Player has won the game
                gameActivity.winSequence();
            } else { // Player has not won the game

                // Checks whether the game has ended in a tie
                tieGame = gameGrid.checkGrid();
                if (tieGame) {
                    gameActivity.tieSequence();
                } else {
                    // Continue the game
                    playerTurn.switchTurn(); // Switch turn
                    roundInfo(this, playerTurn.getTurn()); // Update round info
                }//if-else
            }//if-else
        }//if statement
    }//setCell

    /**
     * Returns the current player's assigned colour in HEX.
     *
     * @return - The player's colour
     */
    private static int getPlayerColour() {
        Integer color = colourMap.get("Player" + playerTurn.getTurn());
        return Objects.requireNonNullElse(color, Color.WHITE);
    }//getPlayerColour

    /**
     * Disables all grid cells after a player has won the game.
     */
    private void disableCells(){
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Button btn = findViewById(buttonIds[row][col]);
                btn.setEnabled(false); // Disable button
            }//for-loop
        }//for-loop
    }//disableCells

    /**
     * Resets the game, clearing the previous game state.
     */
    protected void resetGame(){
        roundInfo(this, playerTurn.getTurn());
        flashAnimator.toggleInfoText(1);
        resetCells();

        if (flashAnimator.winningPattern != null){ // If a player won the previous game
            flashAnimator.resetOutline();
        } else if (tieGame) {
            tieGame = false;

            // Show the player square
            View playerSquare = findViewById(R.id.playerSquare);
            playerSquare.setVisibility(View.VISIBLE);

            // Show the text for the winner
            TextView roundView = findViewById(R.id.roundView);
            roundView.setVisibility(View.VISIBLE);

            // Hide the text indicating that the game ended in a tie
            TextView tieText = findViewById(R.id.tieText);
            tieText.setVisibility(View.GONE);
        }//if-elseif statement
    }//resetGame

    /**
     * Resets all grid cells background colour back to the "Empty" colour.
     */
    private void resetCells() {
        gameGrid.resetGrid();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                View selectCell = findViewById(gridIds[row][col]);

                Integer cellColour = Objects.requireNonNullElse(colourMap.get("Empty"), Color.WHITE);
                selectCell.setBackgroundColor(cellColour);

                Button btn = findViewById(buttonIds[row][col]);
                btn.setEnabled(true); // Re-enable button
            }//for-loop
        }//for-loop
    }//resetCells
}//GameActivity

