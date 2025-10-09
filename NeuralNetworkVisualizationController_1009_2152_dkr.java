// 代码生成时间: 2025-10-09 21:52:09
package com.example.neuralnetwork;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import javax.inject.Inject;

// Controller for Neural Network Visualization
@Controller("/visualization")
public class NeuralNetworkVisualizationController {

    private final NeuralNetworkService neuralNetworkService;

    // Constructor injection of NeuralNetworkService
    @Inject
    public NeuralNetworkVisualizationController(NeuralNetworkService neuralNetworkService) {
        this.neuralNetworkService = neuralNetworkService;
    }

    // GET endpoint to visualize a specific neural network
    @Get("/{neuralNetworkId}")
    public HttpResponse visualization(@PathVariable String neuralNetworkId) {
        try {
            // Retrieve the neural network data for visualization
            byte[] visualizationData = neuralNetworkService.getVisualizationData(neuralNetworkId);
            // Return the visualization data with appropriate content type
            return HttpResponse.ok(visualizationData).contentType(