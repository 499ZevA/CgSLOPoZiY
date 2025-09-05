// 代码生成时间: 2025-09-05 18:45:49
package com.example.micronaut.json;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.MediaType;
import io.micronaut.jackson.serialize.ObjectMapper;
import jakarta.inject.Inject;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@Controller("/json")
@Introspected
public class JsonDataTransformer {
    @Inject
    private final ObjectMapper objectMapper;

    // Endpoint to transform JSON data
    @Post(value = "/transform", processes = MediaType.APPLICATION_JSON)
    public HttpResponse<String> transformJson(@NotBlank String jsonData) {
        try {
            // Deserialize the input JSON data into a Map for easier manipulation
            Map<String, Object> dataMap = objectMapper.readValue(jsonData, Map.class);

            // Perform transformations on the dataMap as needed
            // For example, convert all values into uppercase strings
            dataMap.values().forEach(value -> {
                if (value instanceof String) {
                    dataMap.put(dataMap.keySet().iterator().next(), ((String) value).toUpperCase());
                }
            });

            // Serialize the transformed data back into JSON
            return HttpResponse.ok(objectMapper.writeValueAsString(dataMap));
        } catch (Exception e) {
            // Handle any exceptions that occur during deserialization or serialization
            return HttpResponse.badRequest("Error transforming JSON data: " + e.getMessage());
        }
    }
}
