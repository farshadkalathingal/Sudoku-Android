package com.example.mysudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.mysudoku.sudoku.Sudoku;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.CheckBox;

public class NewMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String SETTINGS_KEY = "AndroidSudokuPreferences";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        preferences = getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);

            builder.setMessage("Are you sure you want to Exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    System.exit(0);
                    NewMainActivity.super.onBackPressed();

                }
            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            androidx.appcompat.app.AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tools) {

            DialogFragment dialog = new SettingsDialog();
            dialog.show(getFragmentManager(), "SettingsDialog");

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    public static class SettingsDialog extends DialogFragment {

        /**
         * Creates dialog.
         *
         * @param savedInstanceState    Instance state.
         * @return                      Newly created Dialog.
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Activity activity = getActivity();

            LayoutInflater inflater = activity.getLayoutInflater();
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            View layout = inflater.inflate(R.layout.dialog_settings, null);

            SharedPreferences preferences = activity.getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE);
            CheckBox toggleHighlight = layout.findViewById(R.id.toggleHighlight);
            toggleHighlight.setChecked(preferences.getBoolean("highlight", true));
            CheckBox toggleHints = layout.findViewById(R.id.toggleHints);
            toggleHints.setChecked(preferences.getBoolean("hints", true));
            CheckBox toggleError = layout.findViewById(R.id.toggleErrorDetection);
            toggleError.setChecked(preferences.getBoolean("autoError", true));

            return builder.setPositiveButton(R.string.save, (dialog, id) -> dialog.dismiss())
                    .setTitle(R.string.settings)
                    .setView(layout)
                    .create();
        }
    }

    public void settingsListener(View view) {
        if (view instanceof CheckBox) {
            SharedPreferences.Editor editor = preferences.edit();
            CheckBox setting = (CheckBox) view;

            switch (view.getId()) {
                case R.id.toggleHighlight: {
                    editor.putBoolean("highlight", setting.isChecked());
                    break;
                }
                case R.id.toggleHints: {
                    editor.putBoolean("hints", setting.isChecked());
                    break;
                }
                case R.id.toggleErrorDetection: {
                    editor.putBoolean("autoError", setting.isChecked());
                    break;
                }
            }

            editor.apply();
        }
    }


}
