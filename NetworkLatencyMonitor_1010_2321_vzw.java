// 代码生成时间: 2025-10-10 23:21:53
package com.example.monitor;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import javax.inject.Inject;

/**
 * NetworkLatencyMonitor is a class that monitors network latency by making HTTP requests to a specified URL.
 */
@Requires(property = "network.latency.monitor.enabled", value = "true")
@Controller("/network-latency")
public class NetworkLatencyMonitor {

    private final HttpClient httpClient;

    /**
     * Constructor injecting HttpClient.
     * @param httpClient The HttpClient instance to make HTTP requests.
     */
    @Inject
    public NetworkLatencyMonitor(@Client("%network.latency.monitor.url") HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Monitors the network latency by making a GET request to the specified URL.
     * @return A response object containing the latency details.
     */
    @Get("/latency")
    public HttpResponse<String> monitorLatency() {
        try {
            // Setting up proxy if required
            Proxy proxy = Proxy.NO_PROXY;
            if ("true".equals(System.getProperty("network.latency.monitor.useProxy"))) {
                String proxyHost = System.getProperty("network.latency.monitor.proxyHost");
                int proxyPort = Integer.parseInt(System.getProperty("network.latency.monitor.proxyPort"));
                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            }

            // Making the HTTP request and measuring the latency
            long startTime = System.nanoTime();
            HttpResponse<String> response = httpClient.toBlocking().exchange("/", String.class, proxy);
            long endTime = System.nanoTime();

            // Calculating the latency
            long latency = (endTime - startTime) / 1000000; // Converting nanoseconds to milliseconds

            // Constructing the response with latency details
            return HttpResponse.ok("Network latency: " + latency + " ms");
        } catch (Exception e) {
            // Handling any exceptions that occur during the monitoring process
            return HttpResponse.serverError(e.getMessage());
        }
    }
}
