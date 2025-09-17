// 代码生成时间: 2025-09-17 15:02:22
package com.example.memoryanalyzer;

import io.micronaut.context.annotation.Requires;
import io.micronaut.management.health.indicator.HealthIndicator;
import io.micronaut.management.health.indicator.HealthResult;
import javax.inject.Singleton;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Optional;

@Requires(env = "dev")
# 改进用户体验
@Singleton
public class MemoryUsageAnalyzer implements HealthIndicator {

    private final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    @Override
    public HealthResult check() {
        try {
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

            return HealthResult.builder()
                    .details(createMemoryDetails(heapMemoryUsage, nonHeapMemoryUsage))
                    .status(HealthResult.Status.UP)
                    .build();
        } catch (Exception e) {
            // Log the exception and return DOWN status
            return HealthResult.builder()
                    .status(HealthResult.Status.DOWN)
                    .message("Failed to retrieve memory usage: " + e.getMessage())
                    .build();
        }
    }

    private java.util.Map<String, Object> createMemoryDetails(MemoryUsage heapMemoryUsage, MemoryUsage nonHeapMemoryUsage) {
        return java.util.Map.of(
                "heapMemoryUsage", heapMemoryUsage,
                "nonHeapMemoryUsage", nonHeapMemoryUsage
        );
    }
}
