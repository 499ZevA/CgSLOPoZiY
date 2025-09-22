// 代码生成时间: 2025-09-22 08:28:06
package com.example.demo.model;

/*
 * Data model example using Micronaut framework.
 */

import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// Data model class
@Introspected
public class DataModel {
    // Fields with appropriate annotations for validation
    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "Value cannot be null")
    private Integer value;

    // Constructor
    public DataModel() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}

/*
 * Data model service class
 */
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface DataModelService extends CrudRepository<DataModel, Long> {
    // Find by name
    @Nullable
    DataModel findByName(@NotBlank String name);

    // Find all with pagination
    Page<DataModel> findAll(Pageable pageable);

    // Find all by value
    List<DataModel> findByValue(@NotNull Integer value);
}

/*
 * Data model controller class
 */
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validateable;
import io.micronaut.validation.Validator;
import io.micronaut.core.async.annotation.Async;
import javax.inject.Inject;

@Controller("/api/dataModels")
public class DataModelController {
    private final DataModelService dataModelService;
    private final Validator validator;

    // Injecting the DataModelService and Validator
    @Inject
    public DataModelController(DataModelService dataModelService, Validator validator) {
        this.dataModelService = dataModelService;
        this.validator = validator;
    }

    // Get all data models
    @Get("/")
    public List<DataModel> list() {
        return dataModelService.findAll().getContent();
    }

    // Get one data model by id
    @Get("/{id}")
    public Optional<DataModel> getById(Long id) {
        return dataModelService.findById(id);
    }

    // Create a new data model
    @Post("/")
    @Async
    public DataModel create(@Validateable DataModel dataModel) {
        // Validate the dataModel object
        List<io.micronaut.core.type.Argument<?>> validationResult = validator.validate(dataModel);
        if (!validationResult.isEmpty()) {
            // Handle validation errors
            throw new IllegalArgumentException("Validation error");
        }
        return dataModelService.save(dataModel);
    }
}
