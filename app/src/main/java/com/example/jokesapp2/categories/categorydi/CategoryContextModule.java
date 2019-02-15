package com.example.jokesapp2.categories.categorydi;

import android.content.Context;

import com.example.jokesapp2.categories.CategoryActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class CategoryContextModule {

    Context categoryContext;

    public CategoryContextModule(CategoryActivity categoryActivity) {
        this.categoryContext = categoryActivity.getApplicationContext();
    }

    @CategoryScope
    @Provides
    Context provideCategoryContext() {
        return categoryContext;
    }
}
