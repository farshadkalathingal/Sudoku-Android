package com.example.mysudoku;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mysudoku.sudoku.Constants;
import com.example.mysudoku.sudoku.Sudoku;

public class PuzzleActivity extends AppCompatActivity {

    private CellView[] cells;

    /**
     * Stores puzzle instance.
     */
    private Sudoku puzzle;

    /**
     * Stores cell highlight state.
     */
    private boolean highlightCells;

    /**
     * Stores note display state.
     */
    private boolean displayNotes;

    private boolean autoError;

    /**
     * Stores current cell selection.
     */
    private CellView currentSelection;

    /**
     * Stores layout.
     */
    private LinearLayout grid;

    /** Timer */

    private Chronometer chronometer;
    private boolean running;
    public static native long elapsedRealtime();

    /**
     * Defines layout parameters for rows.
     */
    private static final LinearLayout.LayoutParams ROW_PARAMS = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 0, 100f / Constants.GROUP_SIZE
    );

    /**
     * Defines layout parameters for cells.
     */
    private static final LinearLayout.LayoutParams CELL_PARAMS = new LinearLayout.LayoutParams(
            0, LinearLayout.LayoutParams.MATCH_PARENT, 100f / Constants.GROUP_SIZE
    );

    /**
     * Overrides default constructor.
     */


    public PuzzleActivity() {
        puzzle = new Sudoku();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        SharedPreferences prefs = getSharedPreferences(NewMainActivity.SETTINGS_KEY, MODE_PRIVATE);
        highlightCells = prefs.getBoolean("highlight", true);
       // displayNotes = prefs.getBoolean("hints", true);
        autoError = prefs.getBoolean("autoError", true);


        grid = findViewById(R.id.puzzle);
        cells = new CellView[Constants.PUZZLE_SIZE];
        setupPuzzle(getIntent());
        chronometer = findViewById(R.id.timer);
    }

    /**
     * Post-processes activity.
     *
     * @param savedInstanceState    Instance state.
     */
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        int index = 0;

        for (int i = 0; i < Constants.GROUP_SIZE; i++) {
            LinearLayout row = new LinearLayout(this);

            for (int j = 0; j < Constants.GROUP_SIZE; j++) {
                CellView cell = new CellView(this, puzzle.getCellInfo(index), displayNotes);

                cell.setMinWidth(CELL_PARAMS.width);
                cell.setMaxWidth(CELL_PARAMS.width);
                cell.setMinHeight(ROW_PARAMS.height);
                cell.setMaxHeight(ROW_PARAMS.height);

                cell.setOnFocusChangeListener(this::onFocusChange);
                cell.setOnLongClickListener(this::onLongClick);

                row.addView(cell, CELL_PARAMS);
                cells[index++] = cell;

                if (currentSelection == null && cell.isFocusable()) {
                    currentSelection = cell;
                }
            }

            grid.addView(row, ROW_PARAMS);
        }

        currentSelection.requestFocus();
        Count.getCount(puzzle);
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            running = true;
        }
    }

    /**
     * Destroys activity.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Updates given cell value.
     *
     * @param view  Target view.
     */
    public void updateCellValue(View view) {
        if (currentSelection != null && currentSelection.isFocusable()) {
            int value = Constants.EMPTY_CELL_VALUE;
            switch (view.getId()) {
                case R.id.clear: {
                   // System.out.println("Runn");
                    valueChange();
                    value = Constants.EMPTY_CELL_VALUE;
                    break;
                }
                case R.id.nr_1: {
                    if(currentSelection.getCellValue()==1){
                        value = Constants.EMPTY_CELL_VALUE;
                        Count.count1 = Count.count1 - 1;
                    } else if(currentSelection.getCellValue()==0){
                        value = 1;
                        Count.count1 = Count.count1 + 1;
                    } else {
                        value = 1;
                        checkCount(currentSelection.getCellValue(), 1);
                    }
                    checkButtonStatus(R.id.nr_1, Count.count1);
                    break;
                }
                case R.id.nr_2: {
                    if(currentSelection.getCellValue()==2){
                        value = Constants.EMPTY_CELL_VALUE;
                        Count.count2 = Count.count2 - 1;
                    } else if(currentSelection.getCellValue()==0){
                        value = 2;
                        Count.count2 = Count.count2 + 1;
                    } else {
                        value = 2;
                        checkCount(currentSelection.getCellValue(), 2);
                    }
                    checkButtonStatus(R.id.nr_2, Count.count2);
                    break;
                }
                case R.id.nr_3: {
                    if(currentSelection.getCellValue()==3){
                        value = Constants.EMPTY_CELL_VALUE;
                        Count.count3 = Count.count3 - 1;
                    } else if(currentSelection.getCellValue()==0){
                        value = 3;
                        Count.count3 = Count.count3 + 1;
                    } else {
                        value = 3;
                        checkCount(currentSelection.getCellValue(), 3);
                    }
                    checkButtonStatus(R.id.nr_3, Count.count3);
                    break;
                }
                case R.id.nr_4: {
                    if(currentSelection.getCellValue()==4){
                        value = Constants.EMPTY_CELL_VALUE;
                        Count.count4 = Count.count4 - 1;
                    } else if(currentSelection.getCellValue()==0){
                        value = 4;
                        Count.count4 = Count.count4 + 1;
                    } else {
                        value = 4;
                        checkCount(currentSelection.getCellValue(), 4);
                    }
                    checkButtonStatus(R.id.nr_4, Count.count4);
                    break;
                }
                case R.id.nr_5: {
                    if(currentSelection.getCellValue()==5){
                        value = Constants.EMPTY_CELL_VALUE;
                        Count.count5 = Count.count5 - 1;
                    } else if(currentSelection.getCellValue()==0){
                        value = 5;
                        Count.count5 = Count.count5 + 1;
                    } else {
                        value = 5;
                        checkCount(currentSelection.getCellValue(), 5);
                    }
                    checkButtonStatus(R.id.nr_5, Count.count5);
                    break;
                }
                case R.id.nr_6: {
                    if(currentSelection.getCellValue()==6){
                        value = Constants.EMPTY_CELL_VALUE;
                        Count.count6 = Count.count6 - 1;
                    } else if(currentSelection.getCellValue()==0){
                        value = 6;
                        Count.count6 = Count.count6 + 1;
                    } else {
                        value = 6;
                        checkCount(currentSelection.getCellValue(), 6);
                    }
                    checkButtonStatus(R.id.nr_6, Count.count6);
                    break;
                }
                case R.id.nr_7: {
                    if(currentSelection.getCellValue()==7){
                        value = Constants.EMPTY_CELL_VALUE;
                        Count.count7 = Count.count7 - 1;
                    } else if(currentSelection.getCellValue()==0){
                        value = 7;
                        Count.count7 = Count.count7 + 1;
                    } else {
                        value = 7;
                        checkCount(currentSelection.getCellValue(), 7);
                    }
                    checkButtonStatus(R.id.nr_7, Count.count7);
                    break;
                }
                case R.id.nr_8: {
                    if(currentSelection.getCellValue()==8){
                        value = Constants.EMPTY_CELL_VALUE;
                        Count.count8 = Count.count8 - 1;
                    } else if(currentSelection.getCellValue()==0){
                        value = 8;
                        Count.count8 = Count.count8 + 1;
                    } else {
                        value = 8;
                        checkCount(currentSelection.getCellValue(), 8);
                    }
                    checkButtonStatus(R.id.nr_8, Count.count8);
                    break;
                }
                case R.id.nr_9: {
                    if(currentSelection.getCellValue()==9){
                        value = Constants.EMPTY_CELL_VALUE;
                        Count.count9 = Count.count9 - 1;
                    } else if(currentSelection.getCellValue()==0){
                        value = 9;
                        Count.count9 = Count.count9 + 1;
                    } else {
                        value = 9;
                        checkCount(currentSelection.getCellValue(), 9);
                    }
                    checkButtonStatus(R.id.nr_9, Count.count9);
                    break;
                }

                case R.id.hint: {
                    int row = currentSelection.getIndex();
                    int val = currentSelection.getCellValue();
                    value = Count.getValue(row);
                    checkCount(val, value);
                    Count.hintCount = Count.hintCount + 1;
                    checkHintButton(R.id.hint, Count.hintCount);
                 //   Toast.makeText(PuzzleActivity.this, Count.count1, Toast.LENGTH_LONG).show();
                    break;
                }

                //case R.id.undo: {
               //     currentSelection.UndoManager
              //  }

            }
            currentSelection.setCellValue(value);
            updateGridStatus();
        }
    }

    private void checkCount(int val, int value) {
        switch (val) {
            case 1: {
                Count.count1 = Count.count1 - 1;
                checkButtonStatus(R.id.nr_1, Count.count1);
                break;
            }
            case 2: {
                Count.count2 = Count.count2 - 1;
                checkButtonStatus(R.id.nr_2, Count.count2);
                break;
            } case 3: {
                Count.count3 = Count.count3 - 1;
                checkButtonStatus(R.id.nr_3, Count.count3);
                break;
            } case 4: {
                Count.count4 = Count.count4 - 1;
                checkButtonStatus(R.id.nr_4, Count.count4);
                break;
            } case 5: {
                Count.count5 = Count.count5 - 1;
                checkButtonStatus(R.id.nr_5, Count.count5);
                break;
            } case 6: {
                Count.count6 = Count.count6 - 1;
                checkButtonStatus(R.id.nr_6, Count.count6);
                break;
            } case 7: {
                Count.count7 = Count.count7 - 1;
                checkButtonStatus(R.id.nr_7, Count.count7);
                break;
            } case 8: {
                Count.count8 = Count.count8 - 1;
                checkButtonStatus(R.id.nr_8, Count.count8);
                break;
            } case 9: {
                Count.count9 = Count.count9 - 1;
                checkButtonStatus(R.id.nr_9, Count.count9);
                break;
            }
        }
        switch (value) {
            case 1: {
                Count.count1 = Count.count1 + 1;
                checkButtonStatus(R.id.nr_1, Count.count1);
                break;
            }
            case 2: {
                Count.count2 = Count.count2 + 1;
                checkButtonStatus(R.id.nr_2, Count.count2);
                break;
            } case 3: {
                Count.count3 = Count.count3 + 1;
                checkButtonStatus(R.id.nr_3, Count.count3);
                break;
            } case 4: {
                Count.count4 = Count.count4 + 1;
                checkButtonStatus(R.id.nr_4, Count.count4);
                break;
            } case 5: {
                Count.count5 = Count.count5 + 1;
                checkButtonStatus(R.id.nr_5, Count.count5);
                break;
            } case 6: {
                Count.count6 = Count.count6 + 1;
                checkButtonStatus(R.id.nr_6, Count.count6);
                break;
            } case 7: {
                Count.count7 = Count.count7 + 1;
                checkButtonStatus(R.id.nr_7, Count.count7);
                break;
            } case 8: {
                Count.count8 = Count.count8 + 1;
                checkButtonStatus(R.id.nr_8, Count.count8);
                break;
            } case 9: {
                Count.count9 = Count.count9 + 1;
                checkButtonStatus(R.id.nr_9, Count.count9);
                break;
            }
        }
    }

    private void checkHintButton(int hint, int hintCount) {
        if(hintCount >= 3 ){
            findViewById(hint).setEnabled(false);
        } else {
            findViewById(hint).setEnabled(true);
        }
    }

    private void checkButtonStatus(int button, int count) {
        if(count == 9){
            findViewById(button).setEnabled(false);
        } else {
            findViewById(button).setEnabled(true);
        }
    }

    public void valueChange(){
        switch (currentSelection.getCellValue()){
            case 1: {
                Count.count1 = Count.count1 - 1;
                checkButtonStatus(R.id.nr_1, Count.count1);
                break;
            }
            case 2: {
                Count.count2 = Count.count2 - 1;
                checkButtonStatus(R.id.nr_2, Count.count2);
                break;
            }
            case 3: {
                Count.count3 = Count.count3 - 1;
                checkButtonStatus(R.id.nr_3, Count.count3);
                break;
            }
            case 4: {
                Count.count4 = Count.count4 - 1;
                checkButtonStatus(R.id.nr_4, Count.count4);
                break;
            }
            case 5: {
                Count.count5 = Count.count5 - 1;
                checkButtonStatus(R.id.nr_5, Count.count5);
                break;
            }
            case 6: {
                Count.count6 = Count.count6 - 1;
                checkButtonStatus(R.id.nr_6, Count.count6);
                break;
            }
            case 7: {
                Count.count7 = Count.count7 - 1;
                checkButtonStatus(R.id.nr_7, Count.count7);
                break;
            }
            case 8: {
                Count.count8 = Count.count8 - 1;
                checkButtonStatus(R.id.nr_8, Count.count8);
                break;
            }
            case 9: {
                Count.count9 = Count.count9 - 1;
                checkButtonStatus(R.id.nr_9, Count.count9);
                break;
            }
        }
    }

    /**
     * Updates grid cell state.
     */
    private void updateGridStatus() {

        if(autoError){
            if(!Verify.checkValue(puzzle, currentSelection.getIndex() , currentSelection.getCellValue())) {
                currentSelection.setTextColor(Color.RED);
            } else {
                currentSelection.setTextColor(Color.BLACK);
            }
        }

        if (puzzle.puzzleIsComplete()) {
            for (CellView cell : cells) {
                cell.setActivated(false);
                cell.setFocusable(false);
                cell.setSelected(false);

                cell.updateCellText();
            }

           // stopService(new Intent(this, TimerService.class));
            chronometer.stop();
            running = false;
            Toast.makeText(this, R.string.completed, Toast.LENGTH_LONG).show();
        } else {
            for (CellView cell : cells) {
                cell.updateCellText();
            }
        }



    }

    /**
     * Initializes puzzle with intended difficulty.
     *
     * @param intent    Intent data.
     */
    private void setupPuzzle(Intent intent) {
        puzzle.generateSolution();

        for (Sudoku.Difficulty difficulty : Sudoku.Difficulty.values()) {
            if (difficulty.name().equals(intent.getStringExtra("difficulty"))) {
                puzzle.generatePuzzle(difficulty);
                break;
            }
        }
    }

    /**
     * Listens for long click events.
     *
     * @param view  Target view.
     * @return true if event is processed, false otherwise.
     */

    public boolean onLongClick(View view) {
        updateCellValue(view);
        return true;
    }

    /**
     * Listens for focus change events.
     *
     * @param view      Target view.
     * @param hasFocus  Focus state.
     */
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus && view instanceof CellView) {
            currentSelection = (CellView) view;

            for (CellView cell : cells) {
                if (highlightCells && cell.isFocusable()) {
                    cell.setActivated(currentSelection.shouldActivate(cell));
                }

                cell.setSelected(cell == currentSelection);
            }
        }
    }
}
