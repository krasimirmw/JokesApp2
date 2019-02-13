package com.example.jokesapp2.categories;

import com.example.jokesapp2.model.datasource.remote.ApiModule;
import com.example.jokesapp2.model.datasource.remote.JokesApiService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * CategoryInteractor used for querying the categories data via Retrofit
 */
public class CategoryInteractor implements CategoryContract.Interactor {

    @Override
    public void getCategoriesArrayList(OnFinishedListener onFinishedListener) {
        /* Creates handle for the RetrofitInstance interface */
        JokesApiService jokesApiService = ApiModule.getRetrofitInstance().create(JokesApiService.class);

        /* Call the method with parameter in the ApiService to get the categories data*/
        Call<String[]> categoriesListCall = jokesApiService.getCategoriesArray();

        categoriesListCall.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(@NotNull Call<String[]> call, @NotNull Response<String[]> response) {
                onFinishedListener.onFinished(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<String[]> call, @NotNull Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
