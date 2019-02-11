package com.example.jokesapp2.jokedetail;

import com.example.jokesapp2.categories.CategoryContract;
import com.example.jokesapp2.model.Category;
import com.example.jokesapp2.model.Joke;
import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class JokePresenterTest {

    private static String ICON_URL = "https://api.chucknorris.io/jokes/pattwbdusdgzo753xnhyxw";

    @Mock
    private JokeContract.View view;

    @Mock
    private JokeContract.Interactor interactor;

    private JokeContract.Presenter presenter;

    @Captor
    private ArgumentCaptor<JokeContract.Interactor.OnFinishedListener> argumentCaptor;

    @Before
    public void setupJokePresenter() {
        MockitoAnnotations.initMocks(this);

        presenter = new JokePresenter(view, interactor);
    }

    @Test
    public void requestDataFromServer() {
        // TODO:
    }

    @Test
    public void onFinished() {
        // TODO:
    }

    @Test
    public void onFailure() {
        // TODO:
    }

}