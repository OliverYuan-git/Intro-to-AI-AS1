import java.util.*;
/**
 * The Eight puzzle board with goal state and randomize state(included movement).
 *
 * @author Oliver Yuan
 * @date 03/02/2023
 */
public class GoalPuzzle extends EightPuzzle{
    /** 
     * the chess board
     */
    private int[][] board;
    /** 
     * row position of the blank b
     */
    private int blankRow;
    /** 
     * column position of the blank b
     */
    private int blankCol;
    
    

    /**
     * GoalPuzzle Constructor
     * set the goal state as b12345678
     */
    public GoalPuzzle() {
        blankRow = 0;
        blankCol = 0;

        board = new int[][]{{0, 1, 2}, 
                            {3, 4, 5}, 
                            {6, 7, 8}};
            }

    /**
     * Method moveUp
     * the method to move the blank b up
     */
    public void moveUp() {
        if (blankRow > 0) {
            swap(blankRow, blankCol, blankRow - 1, blankCol);
            blankRow--;
        }
    }

    /**
     * Method moveDown
     * the method to move the blank b down
     */
    public void moveDown() {
        if (blankRow < 2) {
            swap(blankRow, blankCol, blankRow + 1, blankCol);
            blankRow++;
        }
    }

    /**
     * Method moveLeft
     * the method to move the blank b left
     */
    public void moveLeft() {
        if (blankCol > 0) {
            swap(blankRow, blankCol, blankRow, blankCol - 1);
            blankCol--;
        }
    }

    /**
     * Method moveRight
     * the method to move the blank b right
     */
    public void moveRight() {
        if (blankCol < 2) {
            swap(blankRow, blankCol, blankRow, blankCol + 1);
            blankCol++;
        }
    }

    /**
     * Method swap
     * the method to swap the blank b with the number in the matrix to reach the result of movement
     */
    private void swap(int row1, int col1, int row2, int col2) {
        int temp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = temp;
    }

    /**
     * Method randomizeState
     * the method to randomize a current state from the goal state with n steps
     * @param n A parameter stands for the steps of movement
     */
    public void randomizeState(int n) {
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            int dir = rand.nextInt(4);
            switch (dir) {
                case 0: moveUp(); break;
                case 1: moveDown(); break;
                case 2: moveLeft(); break;
                case 3: moveRight(); break;
            }
        }
    }

    /**
     * Method printState
     * method to print the current state by randomizing
     */
    public void printState() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Main class for testing
     */
    public static void main(String[] args) {
        GoalPuzzle puzzle = new GoalPuzzle();
        puzzle.randomizeState(10);
        System.out.print("the randomized state is: ");
        System.out.println();
        puzzle.printState();
    }
}

