// 代码生成时间: 2025-08-06 14:18:15
package com.example.backupsync;

import io.micronaut.core.annotation.Introspected;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件备份和同步服务，提供文件复制和同步功能。
 */
@Introspected
public class FileBackupSyncService {

    private static final String BACKUP_DIR = "backup_directory";

    /**
     * 复制单个文件到备份目录。
     * @param sourcePath 源文件路径
     * @return 备份文件路径
     * @throws IOException 如果复制过程中发生IO异常
     */
    public Path backupFile(String sourcePath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path backup = Paths.get(BACKUP_DIR, source.getFileName().toString());

        Files.copy(source, backup, StandardCopyOption.REPLACE_EXISTING);
        return backup;
    }

    /**
     * 同步源目录和目标目录，确保目标目录文件与源目录一致。
     * @param sourceDir 源目录路径
     * @param targetDir 目标目录路径
     * @throws IOException 如果同步过程中发生IO异常
     */
    public void syncDirectories(String sourceDir, String targetDir) throws IOException {
        Path sourcePath = Paths.get(sourceDir);
        Path targetPath = Paths.get(targetDir);

        if (!Files.exists(sourcePath)) {
            throw new IOException("Source directory does not exist: " + sourcePath);
        }

        if (!Files.exists(targetPath)) {
            Files.createDirectories(targetPath);
        }

        Files.walk(sourcePath).forEach(path -> {
            try {
                Path target = targetPath.resolve(sourcePath.relativize(path));
                if (Files.isDirectory(path)) {
                    Files.createDirectories(target);
                } else {
                    Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 获取源目录下所有文件的路径。
     * @param sourceDir 源目录路径
     * @return 文件路径列表
     * @throws IOException 如果读取过程中发生IO异常
     */
    public List<String> listFiles(String sourceDir) throws IOException {
        Path sourcePath = Paths.get(sourceDir);
        return Files.walk(sourcePath)
                .filter(Files::isRegularFile)
                .map(path -> path.toAbsolutePath().toString())
                .collect(Collectors.toList());
    }
}
