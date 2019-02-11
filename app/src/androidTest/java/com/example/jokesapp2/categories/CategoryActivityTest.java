package com.example.jokesapp2.categories;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.jokesapp2.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CategoryActivityTest {

    @Rule
    public ActivityTestRule<CategoryActivity> activityTestRule =
            new ActivityTestRule<>(CategoryActivity.class);

    @Test
    public void showProgress() {
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
    }

    @Test
    public void hideProgress() {
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())));
    }

    @Test
    public void onResponseFailure() {
        // Verify the toast text
        CategoryActivity activity = activityTestRule.getActivity();
        onView(withText(R.string.toast_error_text)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test
    public void checkRecyclerViewVisibility() {
        // Verify RecyclerView is displayed
        onView(withId(R.id.recycler_view))
                .check(matches(isDisplayed()));
    }
}