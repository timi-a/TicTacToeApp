/*
This class is used to handle the app's home page. Users are allowed to switch to the game page to
 begin their tic-tac-toe battle but can also adjust the app's theme to better suit their visual
 preferences.

Author: Timi Aina
Date: Feburary 14, 2025
 */

package com.example.tictactoe;

// Android core functionality
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

// AndroidX support libraries for UI and compatibility
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    // Data Field ==========================================================================
    private final EventListener buttonListener  = new EventListener(this);

    // Methods =============================================================================

    /**
     * Initializes the activity and sets up the button listeners.
     *
     * @param savedInstanceState - A possible saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Theme.applyTheme(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_activity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonListener.initHomeBtns();
    }//onCreate

    /**
     * Moves the user from the home page (HomeActivity) to the game page (GameActivity).
     */
    protected static void moveToGame(Context context) {
        Intent intent = new Intent(context, GameActivity.class);
        context.startActivity(intent);

        if (context instanceof Activity) {
            ((Activity) context).finish(); // Close current activity
        }//if statement
    }//moveToGame

    /**
     * Toggles the app's theme between light and dark mode.
     */
    protected static void toggleTheme(Context context) {
        Theme.toggleTheme(context);

        if (context instanceof Activity) {
            ((Activity) context).recreate(); // Restart activity to apply changes
        }//if statement
    }//toggleTheme
}//HomeActivity
