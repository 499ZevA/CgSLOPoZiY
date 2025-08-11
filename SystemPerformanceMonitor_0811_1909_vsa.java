// 代码生成时间: 2025-08-11 19:09:27
import io.micronaut.context.annotation.Value;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Singleton
public class SystemPerformanceMonitor {

    // OperatingSystemMXBean interface
    private final OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    // Retrieves CPU usage as a percentage
    public double getCpuUsage() {
        double cpuLoad = osBean.getSystemCpuLoad();
        if (cpuLoad < 0) {
            throw new RuntimeException("CPU usage is not available.");
        }
        return cpuLoad * 100;
    }

    // Retrieves memory usage in bytes
    public long getMemoryUsage() {
        long memory = runtimeMXBean.getUsedHeapMemory();
        if (memory == 0) {
            throw new RuntimeException("Memory usage is not available.");
        }
        return memory;
    }

    // Retrieves disk usage in bytes
    public long getDiskUsage() {
        try {
            File[] roots = File.listRoots();
            long totalDiskSpace = 0;
            for (File file : roots) {
                totalDiskSpace += file.getTotalSpace();
            }
            return totalDiskSpace;
        } catch (SecurityException e) {
            throw new RuntimeException("Failed to get disk usage.", e);
        }
    }

    // Retrieves a map of system properties
    public Map<String, String> getSystemProperties() {
        return System.getenv();
    }

    // Retrieves the uptime of the JVM in seconds
    public long getUptime() {
        return runtimeMXBean.getUptime() / 1000;
    }

    // Retrieves the user's home directory path
    public String getUserHome() {
        return System.getProperty("user.home");
    }

    // Retrieves the current working directory path
    public String getCurrentWorkingDirectory() {
        String userDir = System.getProperty("user.dir");
        if (userDir == null || userDir.isEmpty()) {
            throw new RuntimeException("Current working directory is not available.");
        }
        return userDir;
    }

    // Retrieves the size of a directory
    public long getDirectorySize(String directoryPath) {
        try {
            return Files.walk(Paths.get(directoryPath))
                .filter(path -> Files.isRegularFile(path))
                .mapToLong(path -> path.toFile().length())
                .sum();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get directory size.", e);
        }
    }

    // Main method for demonstration purposes
    public static void main(String[] args) {
        SystemPerformanceMonitor monitor = new SystemPerformanceMonitor();
        double cpu = monitor.getCpuUsage();
        long memory = monitor.getMemoryUsage();
        long disk = monitor.getDiskUsage();
        long uptime = monitor.getUptime();
        String userHome = monitor.getUserHome();
        String currentDir = monitor.getCurrentWorkingDirectory();
        long dirSize = monitor.getDirectorySize(currentDir);

        System.out.println("CPU Usage: " + cpu + "%");
        System.out.println("Memory Usage: " + memory + " bytes");
        System.out.println("Disk Usage: " + disk + " bytes");
        System.out.println("Uptime: " + uptime + " seconds");
        System.out.println("User Home: " + userHome);
        System.out.println("Current Working Directory: " + currentDir);
        System.out.println("Directory Size: " + dirSize + " bytes");
    }
}
