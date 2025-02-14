/*
This class is used to handle the app's theme, allowing users to switch between dark and light modes.

Author: Timi Aina
ID: 1777752
Date: Feburary 14, 2025
 */

package com.example.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeService {

    // Data Field ==========================================================================
    private static final String SETTINGS = "APP_SETTINGS";
    private static final String THEME_KEY = "DEFAULT_THEME";

    // Methods =============================================================================

    // Applies a certain theme (light/dark) to the app
    public static void applyTheme(Context context) {

        // Uses a private file to grab the boolean value stored in the key THEME_KEY
        SharedPreferences settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        boolean isDefaultTheme = settings.getBoolean(THEME_KEY, true);

        // Set the app to a specific theme
        if (isDefaultTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // Dark mode
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Light mode
        }//if-else
    }//applyTHeme

    // Toggles the app's theme between light and dark mode
    public static void toggleTheme(Context context) {

        // Uses a private file to grab the boolean value stored in the key THEME_KEY
        SharedPreferences settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        boolean isDefaultTheme = settings.getBoolean(THEME_KEY, true);

        // Edits the private file and saves the new value
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(THEME_KEY, !isDefaultTheme);
        editor.apply();

        applyTheme(context); // Apply the new mode
    }//toggleTheme
}//ThemeService

