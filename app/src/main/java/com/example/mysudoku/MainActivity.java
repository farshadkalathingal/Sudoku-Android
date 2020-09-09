package com.example.mysudoku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mysudoku.sudoku.Sudoku;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void puzzleSelection(View view) {
        Intent activity = new Intent(this, PuzzleActivity.class);
        switch (view.getId()) {
            case R.id.easy: {
                activity.putExtra("difficulty", Sudoku.Difficulty.EASY.name());
                break;
            }
            case R.id.medium: {
                activity.putExtra("difficulty", Sudoku.Difficulty.MEDIUM.name());
                break;
            }
            case R.id.hard: {
                activity.putExtra("difficulty", Sudoku.Difficulty.HARD.name());
                break;
            }
            case R.id.very_hard: {
                activity.putExtra("difficulty", Sudoku.Difficulty.VERY_HARD.name());
                break;
            }
            default: {
                activity.putExtra("difficulty", Sudoku.Difficulty.NONE.name());
                break;
            }
        }
        startActivity(activity);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure you want to Exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                System.exit(0);
                MainActivity.super.onBackPressed();

            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
