package com.example.jokesapp2.categories.categorydi;

import com.example.jokesapp2.categories.CategoryActivity;
import com.example.jokesapp2.categories.CategoryContract;
import com.example.jokesapp2.categories.CategoryInteractor;
import com.example.jokesapp2.categories.CategoryPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class CategoryModule {

    @Binds
    public abstract CategoryContract.View view(CategoryActivity categoryActivity);

    @Binds
    public abstract CategoryContract.Interactor interactor(CategoryInteractor categoryInteractor);

    @Provides
    static CategoryContract.Presenter providePresenter(CategoryContract.View view, CategoryContract.Interactor interactor) {
        return new CategoryPresenter(view, interactor);
    }

    //@CategoryScope
    //@Provides
    //CategoryPresenter providePresenter(@CategoryScope CategoryContract.View view, @CategoryScope CategoryContract.Interactor interactor) {
    //    return new CategoryPresenter(view, interactor);
    //}

    @Provides
    static CategoryInteractor provideCategoryInteractor() {
        return new CategoryInteractor();
    }
}
