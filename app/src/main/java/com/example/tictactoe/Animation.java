/*
 This class is used to handle the flashing animation for the winning player, highlighting the
 winning pattern marked by the winning player's assigned number.

Author: Timi Aina
Date: Feburary 27, 2025
 */


package com.example.tictactoe;

// Android core functionality
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

// UI components
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Animation {

    // Data Field ==========================================================================
    private final Context context;
    protected int[][] winningPattern = null;

    // Constructor  ========================================================================

    /**
     * Constructs an AnimationService with the provided context.
     *
     * @param context - The activity context whose Views will be manipulated.
     */
    public Animation(Context context) {
        this.context = context;
    }

    // Methods =============================================================================

    /**
     * Disables all non-grid buttons in the game page.
     */
    private void disableButtons(){
        Activity activity = (Activity) context;

        Button playAgainBtn = activity.findViewById(R.id.playAgainBtn);
        playAgainBtn.setEnabled(false); // Disable button

        Button homeBtn = activity.findViewById(R.id.homeBtn);
        homeBtn.setEnabled(false); // Disable button
    }//disableButtons

    /**
     * Enables all non-grid buttons in the game page.
     */
    private void enableButtons(){
        Activity activity = (Activity) context;

        Button playAgainBtn = activity.findViewById(R.id.playAgainBtn);
        playAgainBtn.setEnabled(true); // Enable button

        Button homeBtn = activity.findViewById(R.id.homeBtn);
        homeBtn.setEnabled(true); // Enable button
    }//enableButtons

    /**
     * Highlights the grid cells that make up the winning pattern.
     *
     * @param isDefaultOutline - True if resetting to default grid outline; false otherwise
     */
    private void highlightWinningGrids(boolean isDefaultOutline){
        Activity activity = (Activity) context;
        for (int[] cellPositions : winningPattern) {

            // Grab the position of the cell on the grid
            int row = cellPositions[0];
            int col = cellPositions[1];

            // Get the correct grid outline object from the table
            View cellView = activity.findViewById(GameActivity.gridOutlineIds[row][col]);

            if (cellView != null) {
                if (isDefaultOutline) { // Default button outline
                    cellView.setBackgroundResource(R.drawable.cell_outline);
                } else {
                    cellView.setBackgroundResource(R.drawable.flash_outline);
                }//if-else
            }//if statement
        }//for-each
    }//highlightWinningGrids

    /**
     * Handles the flashing animation of the winning pattern.
     *
     * @param handler - The handler that schedules UI updates
     * @param task - The runnable task that runs the animation
     * @param isDefaultOutline - True if using default grid outline; false otherwise
     * @param amount - The current number of toggles executed
     * @param toggleAmount - The total number of toggles before stopping
     * @param delay - The delay in milliseconds between toggles
     */
    private void handleAnimation(Handler handler, Runnable task, boolean isDefaultOutline,
                                 int amount, int toggleAmount, int delay) {
        if (amount >= toggleAmount) { // Once we reach the end of the animation,
            enableButtons();
            return;
        }//if statement

        highlightWinningGrids(isDefaultOutline);
        handler.postDelayed(task, delay); // Repeat task every time period 'delay'
    }//handleAnimation

    /**
     * Creates a runnable task for toggling the cell outlines, which creates a flashing effect.
     *
     * @param handler - The handler that schedules UI updates
     * @return - A runnable task that runs the animation
     */
    private Runnable runAnimation(Handler handler){
        final int delay = 500; // 0.5 seconds
        final int animationDuration = 3000; // 3 seconds
        final int toggleAmount = animationDuration / delay; // Times toggling outline

        // Repeatedly toggles the cell outlines; creates a flashing effect
        return new Runnable() {
            boolean isDefaultOutline = true;
            int amount = 0;

            // Repeatedly toggles the cell outlines; creates a flashing effect
            @Override
            public void run() {
                handleAnimation(handler, this, isDefaultOutline, amount, toggleAmount, delay);
                isDefaultOutline = !isDefaultOutline; // Toggle outline
                amount += 1;
            }//run
        };
    }//runAnimation

    /**
     * Starts the animation sequence for highlighting the winning pattern.
     */
    protected void winAnimSequence() {
        toggleInfoText(0); // Hide informative text
        disableButtons();

        // Handles the execution of the animation
        Handler handler = new Handler(Looper.getMainLooper());

        Runnable flashAnimation = runAnimation(handler);

        handler.post(flashAnimation); // Start animation
    }//winAnimation

    /**
     * Resets the outlines of the cells that were part of the winning pattern.
     */
    protected void resetOutline() {
        Activity activity = (Activity) context;
        for (int[] cellPosition : winningPattern) {

            // Grab the position of the cell on the grid
            int row = cellPosition[0];
            int col = cellPosition[1];

            // Get the correct grid outline object from the table
            int cellId = GameActivity.gridOutlineIds[row][col];
            View cellView = activity.findViewById(cellId);

            if (cellView != null) {
                cellView.setBackgroundResource(R.drawable.cell_outline); // Reset to old outline
            }//if statement
        }//for-each
        winningPattern = null;
    }//resetOutline

    /**
     * Shows or hides the informative text displayed under the round info.
     *
     * @param opacity - The opacity level (0 for hidden, 1 for visible).
     */
    protected void toggleInfoText(int opacity){
        Activity activity = (Activity) context;
        TextView infoText = activity.findViewById(R.id.infoText);
        infoText.setAlpha(opacity);
    }//toggleInfoText
}//Animation
