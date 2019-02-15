package com.example.jokesapp2.categories.categorydi;

import com.example.jokesapp2.categories.CategoryActivity;

import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;

@Module(includes = AndroidInjectionModule.class)
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = CategoryModule.class)
    abstract CategoryActivity categoryActivityInjector();
}
