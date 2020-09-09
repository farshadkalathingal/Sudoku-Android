package com.example.mysudoku;

import com.example.mysudoku.sudoku.Sudoku;

import java.util.ArrayList;

public class Count {

    public static int count1 = 0;
    public static int count2 = 0;
    public static int count3 = 0;
    public static int count4 = 0;
    public static int count5 = 0;
    public static int count6 = 0;
    public static int count7 = 0;
    public static int count8 = 0;
    public static int count9 = 0;
    public static int hintCount = 0;
    public static String puzz2;
    public static Sudoku puz;

    public static char[] ch, ch1;
    public static void getCount(Sudoku puzzle) {

        count1 = count2 = count3 = count4 = count5 = count6 = count7 = count8 = count9 = 0;
        puz = puzzle;
        ch = new char[puzzle.getPuzzleString(null).length()];

        for (int i = 0; i < puzzle.getPuzzleString(null).length(); i++) {
            ch[i] = puzzle.getPuzzleString(null).charAt(i);
        }

        for(int i=0; i < puzzle.getPuzzleString(null).length(); i++ ){
            switch (ch[i]){
                case '1': {
                   count1 = count1 + 1;
                   break;
                }
                case '2': {
                    count2 = count2 + 1;
                    break;
                } case '3': {
                    count3 = count3 + 1;
                    break;
                } case '4': {
                    count4 = count4 + 1;
                    break;
                } case '5': {
                    count5 = count5 + 1;
                    break;
                } case '6': {
                    count6 = count6 + 1;
                    break;
                } case '7': {
                    count7 = count7 + 1;
                    break;
                } case '8': {
                    count8 = count8 + 1;
                    break;
                } case '9': {
                    count9 = count9 + 1;
                    break;
                }
            }
        }
    }

    public static int getValue(int row) {
      //  System.out.println(row);
        ch1 = new char[puz.getSolutionString().length()];
        for (int i = 0; i < puz.getSolutionString().length(); i++) {
            ch1[i] = puz.getSolutionString().charAt(i);
        }
       // System.out.println(puz);
        return Character.getNumericValue(ch1[row]);
    }
}
