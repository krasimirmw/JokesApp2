package com.example.jokesapp2.jokedetail;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.jokesapp2.R;
import com.example.jokesapp2.categories.CategoryActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class JokeActivityTest {

    @Rule
    public ActivityTestRule<JokeActivity> activityTestRule =
            new ActivityTestRule<>(JokeActivity.class);

    @Test
    public void showProgress() {
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
    }

    @Test
    public void hideProgress() {
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
    }

    @Test
    public void onResponseFailure() {
        onView(withId(R.id.text_joke)).check(matches(withText(R.string.error)));
    }
}