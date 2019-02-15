package com.example.jokesapp2;

import com.example.jokesapp2.categories.categorydi.DaggerCategoryComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class MyApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerCategoryComponent.builder().application(this).build();
    }

}
