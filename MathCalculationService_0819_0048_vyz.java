// 代码生成时间: 2025-08-19 00:48:15
package com.example.mathtool;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import javax.annotation.Nullable;

@Controller("/math")
public class MathCalculationService {
    
    // Adds two numbers
    @Get("/add/{a}/{b}")
    public Result add(@PathVariable("a") Integer a, @PathVariable("b") Integer b) {
        try {
            return new Result("Addition", a + b);
        } catch (Exception e) {
            return new Result("Error