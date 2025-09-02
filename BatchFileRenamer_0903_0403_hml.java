// 代码生成时间: 2025-09-03 04:03:28
import io.micronaut.core.io.scan.ClassPathResourceLoader;
    import io.micronaut.http.HttpResponse;
    import io.micronaut.http.annotation.Controller;
    import io.micronaut.http.annotation.Get;
    import io.micronaut.http.annotation.PathVariable;
    import io.micronaut.http.server.exceptions.ExceptionHandler;
    import io.micronaut.http.server.exceptions.response.ExceptionResponse;
    import io.micronaut.scheduling.TaskExecutors;
    import io.micronaut.scheduling.annotation.Scheduled;
    import io.micronaut.scheduling.annotation.ScheduledExecutor;
    import io.micronaut.scheduling.io.watch.DirectoryWatcher;
    import java.io.File;
    import java.io.IOException;
    import java.nio.file.*;
    import java.nio.file.attribute.BasicFileAttributeView;
    import java.util.List;
    import java.util.concurrent.ExecutorService;
    import java.util.concurrent.Executors;
    import java.util.concurrent.TimeUnit;
    import javax.inject.Inject;

    // 控制器类，用于处理HTTP请求
    @Controller("/rename")
    public class BatchFileRenamer {

        // 注入文件系统服务
        @Inject
        private FileSystem fileSystem;

        // 注入目录观察者服务
        @Inject
        private DirectoryWatcher directoryWatcher;

        // 注入任务执行器
        @Inject
        @ScheduledExecutor
        private ExecutorService executorService;

        // HTTP GET请求，用于触发批量重命名操作
        @Get("/{dirPath}")
        public HttpResponse<String> renameFiles(@PathVariable String dirPath) {
            try {
                // 执行批量重命名操作
                renameFilesInDirectory(new File(dirPath));
                // 返回成功响应
                return HttpResponse.ok("Files renamed successfully.");
            } catch (IOException e) {
                // 返回错误响应
                return HttpResponse.serverError(e.getMessage());
            }
        }

        // 批量重命名指定目录下的文件
        private void renameFilesInDirectory(File directory) throws IOException {
            if (!directory.exists() || !directory.isDirectory()) {
                throw new IOException("Invalid directory path: " + directory.getPath());
            }

            File[] files = directory.listFiles();
            if (files == null) {
                return;
            }

            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                // 跳过子目录
                if (file.isDirectory()) {
                    continue;
                }

                // 生成新的文件名
                String fileName = file.getName();
                String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
                String extension = fileName.substring(fileName.lastIndexOf('.'));
                String newName = baseName + 