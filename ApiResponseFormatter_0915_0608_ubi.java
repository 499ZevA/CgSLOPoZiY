// 代码生成时间: 2025-09-15 06:08:28
package com.example.api;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.response.ExceptionResponseProcessor;
import jakarta.inject.Singleton;
import java.util.Optional;

@Singleton
@Produces
public class ApiResponseFormatter implements ExceptionResponseProcessor {

    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    @Override
    public Optional<MutableHttpResponse<?>> process(HttpRequest request, Throwable exception) {
        try {
            // Check if the exception is an API error and format the response accordingly
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                // Construct the error response body
                ErrorResponse errorResponse = new ErrorResponse(
                    apiException.getStatusCode(),
                    apiException.getMessage()
                );

                // Create a mutable HTTP response with the status code and body
                MutableHttpResponse<String> response =
                    HttpResponse.status(apiException.getStatusCode()).body(errorResponse.toJson());

                // Set the Content-Type header to 'application/json'
                response.header(CONTENT_TYPE_HEADER, APPLICATION_JSON);

                return Optional.of(response);
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the formatting process
            return Optional.empty();
        }
        return Optional.empty();
    }

    // Inner class representing an error response
    public static class ErrorResponse {
        private int statusCode;
        private String message;

        public ErrorResponse(int statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }

        // Convert this error response to JSON
        public String toJson() {
            return String.format({"{"status": %d, "message": "%s"}"}, statusCode, message);
        }
    }
}

/*
 * ApiException.java
 * 
 * Custom exception class for API errors.
 */
package com.example.api;

public class ApiException extends RuntimeException {

    private final int statusCode;

    public ApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
