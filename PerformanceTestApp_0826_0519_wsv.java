// 代码生成时间: 2025-08-26 05:19:27
package com.example;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import java.net.URL;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main application class for performance testing.
 */
public class PerformanceTestApp {

    private static final int THREAD_COUNT = 10; // Number of threads to spawn for testing
    private static final int WARMUP_REQUESTS = 100; // Number of warm-up requests
    private static final int TEST_REQUESTS = 1000; // Number of test requests
    private static final long TIMEOUT = 5; // Timeout in seconds for each request

    public static void main(String[] args) {
        try (EmbeddedServer server = ApplicationContext.run(EmbeddedServer.class, args)) {
            HttpClient client = server.getHttpClient();
            performWarmUp(client);
            performTest(client);
        }
    }

    private static void performWarmUp(HttpClient client) {
        // Warm-up phase to prime the application
        for (int i = 0; i < WARMUP_REQUESTS; i++) {
            try {
                client.toBlocking().exchange(HttpRequest.GET("/"), String.class);
            } catch (HttpClientResponseException e) {
                System.err.println("Warm-up request failed: " + e.getMessage());
            }
        }
    }

    private static void performTest(HttpClient client) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        long startTime = System.nanoTime();

        for (int i = 0; i < TEST_REQUESTS; i++) {
            executor.submit(() -> {
                try {
                    client.toBlocking().exchange(HttpRequest.GET("/"), String.class).body();
                } catch (HttpClientResponseException e) {
                    System.err.println("Test request failed: " + e.getMessage());
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Test interrupted: " + e.getMessage());
        }

        long duration = (System.nanoTime() - startTime) / 1_000_000;
        System.out.println("Total requests: " + TEST_REQUESTS + ", Duration: " + duration + " ms");
    }
}
