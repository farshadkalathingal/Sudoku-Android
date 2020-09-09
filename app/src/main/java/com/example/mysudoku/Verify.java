package com.example.mysudoku;

import com.example.mysudoku.sudoku.Sudoku;



class Verify {

    public static char[] ch;

    public static boolean checkValue(Sudoku puzzle, int currentSelection, int cellValue) {
        ch = new char[puzzle.getSolutionString().length()];
        for (int i = 0; i < puzzle.getSolutionString().length(); i++) {
            ch[i] = puzzle.getSolutionString().charAt(i);
        }
        if(cellValue != Character.getNumericValue(ch[currentSelection])){
            return false;
        }
        else {
            return true;
        }
    }
}
