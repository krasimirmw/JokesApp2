package com.example.jokesapp2.jokedetail;

import com.example.jokesapp2.model.Joke;
import com.example.jokesapp2.model.datasource.remote.ApiModule;
import com.example.jokesapp2.model.datasource.remote.JokesApiService;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * CategoryInteractor used for querying the jokes data via Retrofit
 */
public class JokeInteractor implements JokeContract.Interactor {

    @Override
    public void getJoke(OnFinishedListener onFinishedListener, String jokeCategory) {
        /* Creates handle for the RetrofitInstance interface */
        JokesApiService jokesApiService = ApiModule.getRetrofitInstance().create(JokesApiService.class);

        /* Call the method with parameter in the ApiService to get the jokes data*/
        Call<Joke> jokeCall = jokesApiService.getJoke(jokeCategory);

        jokeCall.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(@NotNull Call<Joke> call, @NotNull Response<Joke> response) {
                assert response.body() != null;
                onFinishedListener.onFinished(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<Joke> call, @NotNull Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
