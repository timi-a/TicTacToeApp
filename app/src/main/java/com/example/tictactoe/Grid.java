/*
 This class is used to handle the maintenance of the game grid, storing and updating the grid's
 current state.

 Author: Timi Aina
 Date: Feburary 14, 2025
 */

package com.example.tictactoe;

public class Grid {

    // Data Field ==========================================================================
    private final int rows;
    private final int cols;
    private final int[][] grid;

    // Constructor  ========================================================================

    /**
     * Constructs a Grid object with the given number of rows and columns.
     * Initializes the grid and resets it to an empty state.
     *
     * @param rows - Number of rows in the grid.
     * @param cols - Number of columns in the grid.
     */
    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new int[rows][cols];
        this.resetGrid();
    }//Grid

    // Getter ==============================================================================
    /**
     * Gets the current state of the grid.
     *
     * @return - A 2D array representing the grid.
     */
    public int[][] getGrid() {
        return grid;
    }//getGrid

    // Setter ==============================================================================
    /**
     * Sets a specific cell in the grid to the player's assigned number (1 or 2).
     *
     * @param row - Row index of the cell.
     * @param col - Column index of the cell.
     * @param aNum - Number to be placed in the specified cell.
     */
    public void setGrid(int row, int col, int aNum) {
        grid[row][col] = aNum;
    }//setGrid

    // Methods =============================================================================

    /**
     * Resets all cells in the grid to 0, representing an empty state.
     */
    public void resetGrid() {
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                grid[row][col] = 0;
            }//for-loop
        }//for-loop
    }//resetGrid

    /**
     * Checks if the grid is completely filled, meaning the game is a tie.
     *
     * @return - true if the grid is full, false otherwise.
     */
    public boolean checkGrid() {
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                if (grid[row][col] == 0){
                    return false;
                }//if statement
            }//for-loop
        }//for-loop
        return true;
    }//checkGrid

    /**
     * Checks if a player has won by marking a complete row, column, or diagonal.
     *
     * @param aNum - The player's assigned number (1 or 2).
     * @return - A 2D array with the winning positions, null otherwise.
     */
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

    /**
     * Checks to see whether a player has won the game by marking an entire row with their assigned
     * number.
     *
     * @param aNum - The player's assigned number (1 or 2).
     * @return - A 2D array with the winning row's positions, null otherwise.
     */
    private int[][] checkWinHori(int aNum){
        int cellsInRow = 0;
        int[][] squares = new int[3][2]; // 2D array that holds winning positions

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
    /**
     * Checks if a player has won by filling an entire column.
     *
     * @param aNum - The player's assigned number (1 or 2).
     * @return - A 2D array with the winning column's positions, null otherwise.
     */
    private int[][] checkWinVert(int aNum){
        int cellsInCol = 0;
        int[][] squares = new int[3][2]; // 2D array that holds winning positions

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

    /**
     * Checks if a player has won by forming a diagonal from right to left with their assigned
     * number.
     *
     * @param aNum - The player's assigned number (1 or 2).
     * @return - A 2D array with the winning diagonal's positions, null otherwise.
     */
    private int[][] checkWinDiagRL(int aNum){
        if (grid[0][0] == aNum && grid[1][1] == aNum && grid[2][2] == aNum){
            return new int[][]{{0, 0}, {1, 1}, {2, 2}}; // Return diagonal cells
        } else {
            return null;
        }//if-else
    }//checkWinDiagLR

    /**
     * Checks if a player has won by forming a diagonal from left to right with their assigned
     * number.
     *
     * @param aNum - The player's assigned number (1 or 2)
     * @return - A 2D array with the winning diagonal's positions, null otherwise
     */
    private int[][] checkWinDiagLR(int aNum){
        if (grid[0][2] == aNum && grid[1][1] == aNum && grid[2][0] == aNum) {
            return new int[][]{{0, 2}, {1, 1}, {2, 0}}; // Return diagonal cells
        } else {
            return null;
        }//if-else
    }//checkWinDiagRL
}//Grid

