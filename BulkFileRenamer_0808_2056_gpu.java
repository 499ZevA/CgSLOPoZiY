// 代码生成时间: 2025-08-08 20:56:47
package com.example.filerenamer;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.runtime.Micronaut;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Singleton;
import javax.inject.Inject;
import io.micronaut.context.BeanContext;

@Introspected
@Singleton
public class BulkFileRenamer {

    @Inject
    private BeanContext beanContext;

    /**
     * Renames all files in the specified directory by applying a new name pattern.
     *
     * @param directoryPath The path to the directory containing files to rename.
     * @param newNamePattern The new name pattern to apply.
     * @throws Exception If any file operation fails.
     */
    public void renameFiles(String directoryPath, String newNamePattern) throws Exception {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("The path specified is not a directory.");
        }

        // Get a list of all files in the directory
        File[] files = directory.listFiles();
        if (files == null) {
            throw new Exception("Failed to list files in the directory.");
        }

        // Rename each file
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                String newName = String.format(newNamePattern, i + 1);
                File newFile = new File(directory, newName);

                // Check if the new name already exists to avoid overwriting
                if (newFile.exists()) {
                    throw new Exception("A file with the new name already exists.");
                }

                Files.move(files[i].toPath(), newFile.toPath());
                System.out.println("Renamed: " + files[i].getName() + " to " + newFile.getName());
            }
        }
    }

    /**
     * Main method to run the program.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        Micronaut.run(BulkFileRenamer.class, args);
        try {
            String directoryPath = "/path/to/your/directory";
            String newNamePattern = "%d-filename.ext";
            BulkFileRenamer renamer = new BulkFileRenamer();
            renamer.renameFiles(directoryPath, newNamePattern);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
