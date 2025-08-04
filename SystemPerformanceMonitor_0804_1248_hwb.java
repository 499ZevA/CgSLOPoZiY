// 代码生成时间: 2025-08-04 12:48:25
// SystemPerformanceMonitor.java
// This class is a simple system performance monitor tool using Micronaut framework.

package com.example.monitor;

import io.micronaut.context.annotation.Requires;
import io.micronaut.management.endpoint.annotation.Endpoint;
import io.micronaut.management.endpoint.annotation.Read;
import javax.inject.Singleton;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.List;

/**
 * System performance monitoring endpoint.
 */
@Requires(env = "prod") // This endpoint is only available in production environment.
@Singleton
# TODO: 优化性能
@Endpoint(id = "systemPerformance", defaultEnabled = true)
public class SystemPerformanceMonitor {

    private final RuntimeMXBean runtimeMXBean;
    private final ThreadMXBean threadMXBean;

    public SystemPerformanceMonitor() {
        this.runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        this.threadMXBean = ManagementFactory.getThreadMXBean();
    }

    /**
     * Retrieves the current system performance metrics.
     *
     * @return A JSON object containing system performance metrics.
     */
    @Read
    public SystemPerformanceMetrics getSystemPerformanceMetrics() {
        try {
            long uptime = runtimeMXBean.getUptime();
            long peakThreadCount = threadMXBean.getPeakThreadCount();
            long threadCount = threadMXBean.getThreadCount();
            long daemonThreadCount = threadMXBean.getDaemonThreadCount();
# 添加错误处理
            List<ThreadInfo> threadInfoList = threadMXBean.dumpAllThreads(true, true);

            return new SystemPerformanceMetrics(uptime, peakThreadCount, threadCount, daemonThreadCount, threadInfoList);
        } catch (Exception e) {
            // Handle any exceptions that might occur during the retrieval of performance metrics.
            throw new RuntimeException("Failed to retrieve system performance metrics", e);
        }
    }
}

/**
# 扩展功能模块
 * A POJO class to hold system performance metrics.
# 添加错误处理
 */
class SystemPerformanceMetrics {
    private final long uptime;
    private final long peakThreadCount;
    private final long threadCount;
# 扩展功能模块
    private final long daemonThreadCount;
    private final List<ThreadInfo> threadInfoList;

    public SystemPerformanceMetrics(long uptime, long peakThreadCount, long threadCount, long daemonThreadCount, List<ThreadInfo> threadInfoList) {
        this.uptime = uptime;
        this.peakThreadCount = peakThreadCount;
        this.threadCount = threadCount;
# 优化算法效率
        this.daemonThreadCount = daemonThreadCount;
        this.threadInfoList = threadInfoList;
    }

    // Getters for the metrics
    public long getUptime() { return uptime; }
    public long getPeakThreadCount() { return peakThreadCount; }
    public long getThreadCount() { return threadCount; }
    public long getDaemonThreadCount() { return daemonThreadCount; }
    public List<ThreadInfo> getThreadInfoList() { return threadInfoList; }
}
