package com.example.jokesapp2.categories.categorydi;

import android.app.Application;

import com.example.jokesapp2.categories.CategoryActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, CategoryModule.class, ActivityBindingModule.class ,AppContextModule.class})
public interface CategoryComponent extends AndroidInjector<DaggerApplication> {

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {

        @BindsInstance
        CategoryComponent.Builder application(Application application);

        CategoryComponent build();
    }
}
