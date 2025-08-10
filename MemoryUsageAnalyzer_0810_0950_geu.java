// 代码生成时间: 2025-08-10 09:50:54
package com.example.monitoring;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import javax.inject.Inject;

@Controller("/memory")
public class MemoryUsageAnalyzer {

    @Inject
    private MemoryMXBean memoryMXBean;

    // Get the current memory usage
    @Get(value = "/usage", produces = MediaType.APPLICATION_JSON)
    public MemoryUsageInfo getMemoryUsage() {
        try {
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

            return new MemoryUsageInfo(
                heapMemoryUsage.getUsed(),
                heapMemoryUsage.getCommitted(),
                heapMemoryUsage.getMax(),
                nonHeapMemoryUsage.getUsed(),
                nonHeapMemoryUsage.getCommitted(),
                nonHeapMemoryUsage.getMax()
            );
        } catch (Exception e) {
            // Log and handle the exception
            System.err.println("Error retrieving memory usage: " + e.getMessage());
            return null;
        }
    }

    // Inner class to hold memory usage information
    public static class MemoryUsageInfo {
        private Long heapUsed;
        private Long heapCommitted;
        private Long heapMax;
        private Long nonHeapUsed;
        private Long nonHeapCommitted;
        private Long nonHeapMax;

        public MemoryUsageInfo(Long heapUsed, Long heapCommitted, Long heapMax,
                             Long nonHeapUsed, Long nonHeapCommitted, Long nonHeapMax) {
            this.heapUsed = heapUsed;
            this.heapCommitted = heapCommitted;
            this.heapMax = heapMax;
            this.nonHeapUsed = nonHeapUsed;
            this.nonHeapCommitted = nonHeapCommitted;
            this.nonHeapMax = nonHeapMax;
        }

        // Getters for each field
        public Long getHeapUsed() { return heapUsed; }
        public Long getHeapCommitted() { return heapCommitted; }
        public Long getHeapMax() { return heapMax; }
        public Long getNonHeapUsed() { return nonHeapUsed; }
        public Long getNonHeapCommitted() { return nonHeapCommitted; }
        public Long getNonHeapMax() { return nonHeapMax; }
    }
}
