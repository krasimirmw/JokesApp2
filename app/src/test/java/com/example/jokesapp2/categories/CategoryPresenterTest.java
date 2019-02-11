package com.example.jokesapp2.categories;

import com.example.jokesapp2.model.Category;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CategoryPresenterTest {

    @Mock
    private CategoryContract.View view;

    @Mock
    private CategoryContract.Interactor interactor;

    private CategoryPresenter presenter;

    @Captor
    private ArgumentCaptor<CategoryContract.Interactor.OnFinishedListener> argumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        presenter = new CategoryPresenter(view, interactor);
    }

    @Test
    public void loadCategories() throws Exception {
        presenter.requestDataFromServer();
        verify(interactor, times(1)).getCategoriesArrayList(argumentCaptor.capture());
        argumentCaptor.getValue().onFinished(getCategories());
        verify(view, times(1)).hideProgress();

        ArgumentCaptor<String[]> entityArgumentCaptor = ArgumentCaptor.forClass(String[].class);
        verify(view).setDataToRecyclerView(entityArgumentCaptor.capture());

        assertEquals(16, entityArgumentCaptor.getValue().length);
    }

    @Test
    public void requestDataFromServer() {
        interactor.getCategoriesArrayList(argumentCaptor.capture());
        verify(interactor, times(1)).getCategoriesArrayList(argumentCaptor.capture());
    }

    @Test
    public void onFinished() {
        presenter.requestDataFromServer();
        verify(interactor, times(1)).getCategoriesArrayList(argumentCaptor.capture());
        argumentCaptor.getValue().onFinished(getCategories());
        ArgumentCaptor<String[]> dataArgumentCaptor = ArgumentCaptor.forClass(String[].class);
        verify(view, times(1)).setDataToRecyclerView(dataArgumentCaptor.capture());
        verify(view).setDataToRecyclerView(dataArgumentCaptor.getValue());
        verify(view).hideProgress();
    }

    @Test
    public void onFailure() throws Exception {
        presenter.requestDataFromServer();
        verify(interactor, times(1)).getCategoriesArrayList(argumentCaptor.capture());
        argumentCaptor.getValue().onFailure(new Throwable());
        ArgumentCaptor<Throwable> throwableArgumentCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(view, times(1)).onResponseFailure(throwableArgumentCaptor.capture());
        verify(view).onResponseFailure(throwableArgumentCaptor.getValue());
        verify(view).hideProgress();
    }

    private String[] getCategories() {
        String[] categories = new String[16];
        try {
            categories[0] = new Category("science").getCategory();
            categories[1] = new Category("science").getCategory();
            categories[2] = new Category("science").getCategory();
            categories[3] = new Category("science").getCategory();
            categories[4] = new Category("science").getCategory();
            categories[5] = new Category("science").getCategory();
            categories[6] = new Category("science").getCategory();
            categories[7] = new Category("science").getCategory();
            categories[8] = new Category("science").getCategory();
            categories[9] = new Category("science").getCategory();
            categories[10] = new Category("science").getCategory();
            categories[11] = new Category("science").getCategory();
            categories[12] = new Category("science").getCategory();
            categories[13] = new Category("science").getCategory();
            categories[14] = new Category("science").getCategory();
            categories[15] = new Category("science").getCategory();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }
}