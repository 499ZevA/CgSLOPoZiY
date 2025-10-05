// 代码生成时间: 2025-10-05 20:20:19
package com.example.copyright;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.ExceptionHandler;
import io.micronaut.http.exceptions.server.ExceptionHandlerServer;
import io.micronaut.http.server.exceptions.InternalServerException;
import io.micronaut.http.server.exceptions.UnprocessableEntityException;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;

// CopyrightProtectionService class to handle copyright protection logic
@Controller("/copyright")
@Introspected
public class CopyrightProtectionService {

    private final CopyrightRepository copyrightRepository;

    // Constructor injection for CopyrightRepository
    public CopyrightProtectionService(CopyrightRepository copyrightRepository) {
        this.copyrightRepository = copyrightRepository;
    }

    // Endpoint to check if a work is under copyright protection
    @Get("/check")
    public CopyrightResponse checkCopyright(String title) {
        try {
            // Check if the work exists and if it's under copyright protection
            Optional<Copyright> copyright = copyrightRepository.findByTitle(title);
            if (copyright.isPresent()) {
                // Return the copyright status
                return new CopyrightResponse(true, copyright.get().getEndDate());
            } else {
                // Return false if work is not found or out of copyright
                return new CopyrightResponse(false, null);
            }
        } catch (Exception e) {
            // Handle any unexpected errors
            throw new InternalServerException("Error checking copyright status", e);
        }
    }

    // Exception handler for copyright-related errors
    @ExceptionHandler(CopyrightException.class)
    public HttpStatus handleCopyrightException(CopyrightException e) {
        // Log the error and return a 422 Unprocessable Entity status
        // This can be replaced with a more specific logging framework
        System.err.println("Copyright exception: " + e.getMessage());
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}

// Copyright exception class
class CopyrightException extends RuntimeException {
    public CopyrightException(String message) {
        super(message);
    }
}

// Copyright entity class
@Introspected
class Copyright {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

// Copyright repository interface
interface CopyrightRepository {
    Optional<Copyright> findByTitle(String title);
}

// Copyright response DTO
@Introspected
@Schema(description = "Copyright response data")
class CopyrightResponse {
    private boolean isProtected;
    private LocalDate endDate;

    public CopyrightResponse(boolean isProtected, @Nullable LocalDate endDate) {
        this.isProtected = isProtected;
        this.endDate = endDate;
    }

    // Getters and setters
    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean isProtected) {
        this.isProtected = isProtected;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}