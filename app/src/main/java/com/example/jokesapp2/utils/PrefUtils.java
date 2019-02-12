package com.example.jokesapp2.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public final class PrefUtils {

    public static final String PREF_FAVORED_JOKES = "pref_favored_jokes";

    public static void addToFavorites(final Context context, String jokeId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> favoredJokesSet = sharedPreferences.getStringSet(PREF_FAVORED_JOKES, null);
        if (favoredJokesSet == null) {
            favoredJokesSet = new HashSet<>();
        }
        favoredJokesSet.add(jokeId);
        sharedPreferences.edit().putStringSet(PREF_FAVORED_JOKES, favoredJokesSet).apply();
    }

    public static void removeFromFavorites(final Context context, String jokeId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> favoredJokesSet = sharedPreferences.getStringSet(PREF_FAVORED_JOKES, null);
        if (favoredJokesSet == null) {
            favoredJokesSet = new HashSet<>();
        }
        favoredJokesSet.remove(jokeId);
        sharedPreferences.edit().putStringSet(PREF_FAVORED_JOKES, favoredJokesSet).apply();
    }
}
