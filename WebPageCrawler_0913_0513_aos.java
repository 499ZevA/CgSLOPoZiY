// 代码生成时间: 2025-09-13 05:13:11
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.Micronaut;
import javax.inject.Inject;
import java.net.URI;
import java.net.http.HttpClient as JdkHttpClient;
import java.net.http.HttpRequest as JdkHttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class WebPageCrawler {

    private static final String USER_AGENT = "Mozilla/5.0";

    @Inject
    @Client("/")
    private HttpClient httpClient;

    // Constructor
    public WebPageCrawler(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    // Method to fetch web page content
    public String fetchWebPageContent(String url) {
        try {
            // Create a GET request
            HttpRequest request = HttpRequest.GET(url);
            request.header("User-Agent", USER_AGENT);

            // Send the request and retrieve the response
            return httpClient.toBlocking().retrieve(request, String.class);
        } catch (HttpClientResponseException e) {
            // Handle HTTP errors
            System.err.println("HTTP error occurred: " + e.getMessage());
            return null;
        } catch (Exception e) {
            // Handle other exceptions
            System.err.println("Error occurred: " + e.getMessage());
            return null;
        }
    }

    // Method to fetch web page content using JDK HttpClient
    public String fetchWebPageContentJdk(String url) {
        try {
            // Create a JDK HTTP request
            JdkHttpRequest jdkRequest = JdkHttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", USER_AGENT)
                .build();

            // Send the request and retrieve the response
            JdkHttpClient jdkHttpClient = JdkHttpClient.newBuilder().build();
            HttpResponse<String> response = jdkHttpClient.send(jdkRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            // Check if the response was successful
            if (response.statusCode() >= 400) {
                throw new RuntimeException("Failed to retrieve content: " + response.statusCode());
            }

            return response.body();
        } catch (CompletionException e) {
            // Handle JDK HTTP client exceptions
            System.err.println("Error occurred: " + e.getCause().getMessage());
            return null;
        } catch (Exception e) {
            // Handle other exceptions
            System.err.println("Error occurred: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        // Start the Micronaut application
        Micronaut.run(WebPageCrawler.class);

        // Initialize the WebPageCrawler
        WebPageCrawler crawler = Micronaut.inject(WebPageCrawler.class);

        // Example usage: Fetch the content of a web page
        String url = "http://example.com";
        String content = crawler.fetchWebPageContent(url);
        System.out.println("Web page content: " + content);
    }
}
