package com.example.jokesapp2.categories.categorydi;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AppContextModule {

    @Binds
    abstract Context bindContext(Application application);
}
