/*
This class is used to handle the maintenance of the game grid, holding all of the information about
the grid's current state.

Author: Timi Aina
ID: 1777752
Date: Feburary 14, 2025
 */

package com.example.tictactoe;

class Grid {

    // Data Field ==========================================================================
    private final int rows;
    private final int cols;
    private final int[][] grid;

    // Constructor  ========================================================================
    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new int[rows][cols];
        this.resetGrid();
    }//Grid

    // Methods =============================================================================

    // Getter for the class
    public int[][] getGrid() {
        return grid;
    }//getGrid

    // Setter for the class
    public void setGrid(int row, int col, int aNum) {
        grid[row][col] = aNum;
    }//setGrid

    // Resets all grid cells to 0
    public void resetGrid() {
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                grid[row][col] = 0; // Use 0 to represent an empty cell
            }//for-loop
        }//for-loop
    }//resetGrid

    // Checks to see whether a player has won the game
    public int[][] checkWin(int aNum) {
        int[][] winningPattern;

        winningPattern = checkWinHori(aNum);
        if (winningPattern != null) {
            return winningPattern;
        }//if statement

        winningPattern = checkWinVert(aNum);
        if (winningPattern != null) {
            return winningPattern;
        }//if statement

        winningPattern = checkWinDiagRL(aNum);
        if (winningPattern != null) {
            return winningPattern;
        }//if statement

        winningPattern = checkWinDiagLR(aNum);
        return winningPattern;
    }//checkWin

    // Checks to see whether a player has won the game by marking an entire row
    private int[][] checkWinHori(int aNum){
        int cellsInRow = 0;
        int[][] squares = new int[3][2];
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                if (grid[row][col] == aNum){
                    squares[cellsInRow][0] = row;
                    squares[cellsInRow][1] = col;
                    cellsInRow += 1;
                } else {
                    cellsInRow = 0;
                } //if-else

                if (cellsInRow == 3){
                    return squares;
                }//if statement
            }//for-loop
            cellsInRow = 0;
        }//for-loop
        return null;
    }//checkWinHori

    // Checks to see whether a player has won the game by marking an entire column
    private int[][] checkWinVert(int aNum){
        int cellsInCol = 0;
        int[][] squares = new int[3][2];
        for (int col = 0; col < cols; col++){
            for (int row = 0; row < rows; row++){
                if (grid[row][col] == aNum){
                    squares[cellsInCol][0] = row;
                    squares[cellsInCol][1] = col;
                    cellsInCol += 1;
                } else {
                    cellsInCol = 0;
                } //if-else

                if (cellsInCol == 3){
                    return squares;
                }//if statement
            }//for-loop
            cellsInCol = 0;
        }//for-loop
        return null;
    }//checkWinVert

    // Checks to see whether a player has won the game by marking
    private int[][] checkWinDiagRL(int aNum){
        if (grid[0][0] == aNum && grid[1][1] == aNum && grid[2][2] == aNum){
            return new int[][]{{0, 0}, {1, 1}, {2, 2}}; // Return diagonal cells
        } else {
            return null;
        }//if-else
    }//checkWinDiagLR

    // Checks to see whether a player has won the game by marking
    private int[][] checkWinDiagLR(int aNum){
        if (grid[0][2] == aNum && grid[1][1] == aNum && grid[2][0] == aNum) {
            return new int[][]{{0, 2}, {1, 1}, {2, 0}}; // Return diagonal cells
        } else {
            return null;
        }//if-else
    }//checkWinDiagRL
}//Grid

