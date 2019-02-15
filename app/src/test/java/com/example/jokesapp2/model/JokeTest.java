package com.example.jokesapp2.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JokeTest {

    private static final String FAKE_ID = "FAKE ID";

    private static final String FAKE_CATEGORY = "FAKE CATEGORY";

    private static final String FAKE_JOKE_VALUE = "FAKE JOKE - CHICKEN";

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    private Joke joke;

    @Test
    public void constructor_withNullJoke() {
        thrownException.expect(NullPointerException.class);

        joke = new Joke(null);
    }
}
