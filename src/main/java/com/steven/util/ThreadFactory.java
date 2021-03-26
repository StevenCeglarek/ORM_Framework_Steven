package com.steven.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadFactory {

    final int MAX_THREADS = 10;
    ExecutorService threadExecutor = Executors.newFixedThreadPool(MAX_THREADS);

    public ExecutorService getThreadExecutor() {
        return threadExecutor;
    }
}
