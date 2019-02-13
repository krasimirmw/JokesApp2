package com.example.jokesapp2.jokedetail;

import com.example.jokesapp2.model.Joke;
import com.example.jokesapp2.model.datasource.JokesDataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class JokePresenterTest {

    private static String ICON_URL = "https://api.chucknorris.io/jokes/pattwbdusdgzo753xnhyxw";

    private static List<String> categoryList = new ArrayList<String>() {{
        add(("science"));
    }};
    private static Joke JOKE = new Joke(categoryList, ICON_URL, "one good mock joke");

    @Mock
    private JokeContract.View view;

    @Mock
    private JokeContract.Interactor interactor;

    @Mock
    private JokesDataSource jokesDataSource;

    private JokeContract.Presenter presenter;

    @Captor
    private ArgumentCaptor<JokeContract.Interactor.OnFinishedListener> argumentCaptor;

    @Before
    public void setupJokePresenter() {
        MockitoAnnotations.initMocks(this);

        presenter = new JokePresenter(view, jokesDataSource);
    }

    @Test
    public void requestDataFromServer() {
        presenter.requestDataFromServer(JOKE.getCategory().get(0));
        interactor.getJoke(argumentCaptor.capture(), eq(JOKE.getCategory().get(0)));
        verify(interactor, times(1)).getJoke(argumentCaptor.capture(), eq(JOKE.getCategory().get(0)));
        verify(view).showProgress();
    }

    @Test
    public void onFinished() {
        presenter.requestDataFromServer(JOKE.getCategory().get(0));
        verify(interactor, times(1)).getJoke(argumentCaptor.capture(), eq(JOKE.getCategory().get(0)));
        argumentCaptor.getValue().onFinished(JOKE);
        ArgumentCaptor<Joke> dataArgumentCaptor = ArgumentCaptor.forClass(Joke.class);
        verify(view).setData(JOKE.getId(), JOKE.getCategory().get(0), JOKE.getValue(), JOKE.getIconUrl(), JOKE.isFavored());
        verify(view).hideProgress();
    }

    @Test
    public void onFailure() {
        presenter.requestDataFromServer(JOKE.getCategory().get(0));
        verify(interactor, times(1)).getJoke(argumentCaptor.capture(), eq(JOKE.getCategory().get(0)));
        argumentCaptor.getValue().onFailure(new Throwable());

        ArgumentCaptor<Throwable> throwableArgumentCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(view, times(1)).onResponseFailure(throwableArgumentCaptor.capture());
        verify(view).onResponseFailure(throwableArgumentCaptor.getValue());
        verify(view).hideProgress();
    }
}