import java.util.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
 /**
 * The Eight puzzle board imported from the text file.
 *
 * @author Oliver Yuan
 * @date 03/02/2023
 */
public class EightPuzzle {
    /** 
     * the chess board
     */
    private int[][] board;
    /** 
     * the blank space or b
     */
    private int[] blank;
    /**
     * the initial State matirx
     */
    private int[][] initialState;

    /**
     * Method setState
     *
     * @param fileName,which is the name of the text file in the folder
     * @return the current state of 8-puzzle as an int matrix
     */
    public static int[][] setState(String fileName){
        int[][] state= new int[3][3];
        try{
            Scanner scanner = new Scanner(new File(fileName));
            /* get the original string line with b and space*/
            String sLine = scanner.next(); 
            /* replace all the space and b to the all-int String*/
            String newSline = sLine.replaceAll("b","0");
            String newFline = newSline.replaceAll(" ","");
            // System.out.print(newFline); //this is for testing if the file is correctly transfrom into a string with number
            for(int i = 0; i < 9; i++){
                int row=i/3;
                int col=i%3;
                /* put each number into the int matrix */
                state[row][col]=Character.getNumericValue(newFline.charAt(i));
            }
            scanner.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return state;
    }

    /**
     * Method printState to print the current state
     *
     * @param cState which is the current state
     */
    public static void printState(int[][] cState){
        for(int row = 0; row <3; row ++){
            for(int col = 0; col <3; col++){
                System.out.print(cState[row][col]+" ");
            }
            System.out.println("");
        }
    }

    /**
     * Main class for testing
     */
    public static void main(String[] args){
        int[][] test1 = setState("test1.txt");
        printState(test1);
    }
}

