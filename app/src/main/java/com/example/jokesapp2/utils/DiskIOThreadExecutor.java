package com.example.jokesapp2.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

/* Executor for computing disk Input/Output on a single Thread instance */
class DiskIOThreadExecutor implements Executor {
    private final Executor diskIO;

    DiskIOThreadExecutor() {
        diskIO = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        diskIO.execute(runnable);
    }
}
