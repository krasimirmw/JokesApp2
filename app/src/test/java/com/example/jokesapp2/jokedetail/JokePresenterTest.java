package com.example.jokesapp2.jokedetail;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.jokesapp2.model.Joke;
import com.example.jokesapp2.model.datasource.JokesDataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class JokePresenterTest {

    private static String ICON_URL = "https://api.chucknorris.io/jokes/pattwbdusdgzo753xnhyxw";

    private static List<String> categoryList = new ArrayList<String>() {{
        add(("science"));
    }};

    private static final String FAKE_ID = "FAKE ID";
    private static final String FAKE_CATEGORY = "FAKE CATEGORY";
    private static final String FAKE_JOKE = "FAKE JOKE";

    private static Joke JOKE = new Joke(FAKE_ID, FAKE_CATEGORY, FAKE_JOKE, false);

    @Mock
    private JokeContract.View view;

    @Mock
    private JokeContract.Interactor interactor;

    @Mock
    private JokesDataSource jokesDataSource;

    private Context context;

    private SharedPreferences sharedPreferences;

    private JokeContract.Presenter presenter;

    @Captor
    private ArgumentCaptor<JokeContract.Interactor.OnFinishedListener> argumentCaptor;

    @Before
    public void setupJokePresenter() {
        MockitoAnnotations.initMocks(this);
        this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences);

        presenter = new JokePresenter(context, view, jokesDataSource);
    }

    @Test
    public void requestDataFromServer() {
        presenter.requestDataFromServer(JOKE.getCategory());
        interactor.getJoke(argumentCaptor.capture(), eq(JOKE.getCategory()));
        verify(interactor, times(1)).getJoke(argumentCaptor.capture(), eq(JOKE.getCategory()));
        verify(view).showProgress();
    }

    @Test
    public void onFinished() {
        presenter.requestDataFromServer(JOKE.getCategory());
        verify(interactor, times(1)).getJoke(argumentCaptor.capture(), eq(JOKE.getCategory()));
        argumentCaptor.getValue().onFinished(JOKE);
        ArgumentCaptor<Joke> dataArgumentCaptor = ArgumentCaptor.forClass(Joke.class);
        verify(view).setData(JOKE.getId(), JOKE.getCategory(), JOKE.getValue(), JOKE.getIconUrl());
        verify(view).hideProgress();
    }

    @Test
    public void onFailure() {
        presenter.requestDataFromServer(JOKE.getCategory());
        verify(interactor, times(1)).getJoke(argumentCaptor.capture(), eq(JOKE.getCategory()));
        argumentCaptor.getValue().onFailure(new Throwable());

        ArgumentCaptor<Throwable> throwableArgumentCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(view, times(1)).onResponseFailure(throwableArgumentCaptor.capture());
        verify(view).onResponseFailure(throwableArgumentCaptor.getValue());
        verify(view).hideProgress();
    }
}