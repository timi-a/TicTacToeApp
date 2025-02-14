/*
This class is used to handle player turns, including getting the current turn value and switching
between turn values 0 and 1.

Author: Timi Aina
ID: 1777752
Date: Feburary 14, 2025
 */

package com.example.tictactoe;

public class Turn {

    // Data Field ==========================================================================
    private int turn;

    // Constructor  ========================================================================
    public Turn(int initialTurn) {
        this.turn = initialTurn;
    }//Turn

    // Methods =============================================================================

    // Getter for the class
    public int getTurn() {
        return turn;
    }//getTurn

    // Switches player turns
    public void switchTurn() {
        if (turn == 1) {
            turn = 2;
        } else {
            turn = 1;
        }//if-else
    }//switchTurn
}//Turn
