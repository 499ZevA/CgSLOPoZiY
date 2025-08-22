// 代码生成时间: 2025-08-22 14:21:27
package com.example.dataanalysis;

import io.micronaut.core.annotation.Introspected;
import java.util.List;
import java.util.Optional;

// DataAnalysisService class provides statistical analysis functionality
@Introspected
public class DataAnalysisService {

    private final DataAnalysisRepository repository;

    // Constructor injection for the repository
    public DataAnalysisService(DataAnalysisRepository repository) {
        this.repository = repository;
    }

    // Method to calculate the average value of a list
    public double calculateAverage(List<Double> values) throws IllegalArgumentException {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("List of values cannot be null or empty");
        }
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    // Method to find the maximum value in a list
    public Optional<Double> findMaximum(List<Double> values) {
        if (values == null || values.isEmpty()) {
            return Optional.empty();
        }
        return values.stream().max(Double::compare);
    }

    // Method to find the minimum value in a list
    public Optional<Double> findMinimum(List<Double> values) {
        if (values == null || values.isEmpty()) {
            return Optional.empty();
        }
        return values.stream().min(Double::compare);
    }

    // Method to calculate the standard deviation of a list
    public double calculateStandardDeviation(List<Double> values) throws IllegalArgumentException {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("List of values cannot be null or empty");
        }
        double mean = calculateAverage(values);
        double sumOfSquares = 0;
        for (double value : values) {
            sumOfSquares += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sumOfSquares / (values.size() - 1));
    }
}
