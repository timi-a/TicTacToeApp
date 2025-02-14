/*
This class is used to handle the app's home page. Users are allowed to switch to the game page to
 begin their tic-tac-toe battle but can also adjust the app's theme to better suit their visual
 preferences.

Author: Timi Aina
ID: 1777752
Date: Feburary 14, 2025
 */

package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    // Runs all the desirable activity functions when an activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeService.applyTheme(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_activity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initActivityBtns(); // Add button listeners
    }//onCreate

    //Adds buttons listeners to all the buttons in the HomeActivity
    public void initActivityBtns(){
        Button playBtn = findViewById(R.id.playBtn);
        Button modeBtn = findViewById(R.id.modeBtn);

        playBtn.setOnClickListener(v -> moveToGame());
        modeBtn.setOnClickListener(v -> toggleTheme());
    }//initButton

    // Moves the user from the home page (HomeActivity) to the game page (GameActivity)
    public void moveToGame(){
        Intent intent = new Intent(HomeActivity.this, GameActivity.class);
        startActivity(intent);
        finish(); // Close current activity
    }//moveToGame

    // Toggles the app's theme between light and dark mode
    public void toggleTheme(){
        ThemeService.toggleTheme(this);
        recreate(); // Restart activity to apply changes
    }//toggleTheme
}//HomeActivity
