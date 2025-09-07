// 代码生成时间: 2025-09-07 23:23:56
package com.example.folderorganizer;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/api/folder")
@Introspected
public class FolderOrganizerController {

    private static final String DIRECTORY_PATH = "path/to/your/directory";
    private static final String ORGANIZED_PATH = "path/to/organized/directory";

    // POST endpoint to organize a folder
    @Post("/organize")
    public HttpResponse<String> organizeFolder() {
        try {
            organizeFolderStructure(Paths.get(DIRECTORY_PATH), Paths.get(ORGANIZED_PATH));
            return HttpResponse.ok("Folder organized successfully.");
        } catch (IOException e) {
            return HttpResponse.serverError(e.getMessage());
        }
    }

    // GET endpoint to list all files in a folder
    @Get("/files/{folderPath}")
    public HttpResponse<List<String>> listFiles(@PathVariable String folderPath) {
        try {
            Path path = Paths.get(folderPath);
            if (!Files.isDirectory(path)) {
                return HttpResponse.badRequest("Provided path is not a directory.");
            }
            return HttpResponse.ok(listFileNames(path));
        } catch (IOException e) {
            return HttpResponse.serverError(e.getMessage());
        }
    }

    // Helper method to organize folder structure
    private void organizeFolderStructure(Path sourcePath, Path destinationPath) throws IOException {
        if (!Files.exists(destinationPath)) {
            Files.createDirectories(destinationPath);
        }
        Files.walk(sourcePath)
                .filter(path -> !Files.isDirectory(path))
                .forEach(path -> {
                    try {
                        Path targetPath = destinationPath.resolve(sourcePath.relativize(path));
                        Files.createDirectories(targetPath.getParent());
                        Files.copy(path, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    // Helper method to list file names in a directory
    private List<String> listFileNames(Path path) throws IOException {
        return Files.list(path)
                .filter(Files::isRegularFile)
                .map(path::toString)
                .collect(Collectors.toList());
    }
}
