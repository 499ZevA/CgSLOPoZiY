// 代码生成时间: 2025-08-21 08:30:08
import io.micronaut.core.annotation.Introspected;
    import java.io.*;
    import java.nio.file.*;
    import java.util.zip.*;

    /**
     * A utility service for extracting ZIP files using Java.
     */
    @Introspected
    public class ZipExtractionService {

        /**
         * Extracts the contents of a ZIP file into a specified directory.
         *
         * @param zipFilePath The path to the ZIP file to extract.
         * @param outputDir The directory where the extracted files will be placed.
         * @throws IOException If an I/O error occurs during extraction.
         */
        public void extractZipFile(String zipFilePath, String outputDir) throws IOException {
            // Create the output directory if it does not exist
            Files.createDirectories(Paths.get(outputDir));

            // Open the ZIP file
            try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
                ZipEntry entry = zipIn.getNextEntry();
                // Iterate over the ZIP entries
                while (entry != null) {
                    String filePath = outputDir + File.separator + entry.getName();
                    if (!entry.isDirectory()) {
                        // If the entry is a file, extract it
                        extractFile(zipIn, filePath);
                    } else {
                        // If the entry is a directory, create it
                        Files.createDirectories(Paths.get(filePath));
                    }
                    zipIn.closeEntry();
                    entry = zipIn.getNextEntry();
                }
            }
        }

        /**
         * Extracts a single file from the ZIP input stream.
         *
         * @param zipIn The ZIP input stream.
         * @param filePath The path to extract the file to.
         * @throws IOException If an I/O error occurs during extraction.
         */
        private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
                byte[] bytesIn = new byte[4096];
                int read = 0;
                while ((read = zipIn.read(bytesIn)) != -1) {
                    bos.write(bytesIn, 0, read);
                }
            }
        }

        // Example usage of the service
        public static void main(String[] args) {
            ZipExtractionService service = new ZipExtractionService();
            String zipFilePath = "path/to/your.zip"; // Replace with your ZIP file path
            String outputDir = "path/to/output"; // Replace with your desired output directory
            try {
                service.extractZipFile(zipFilePath, outputDir);
                System.out.println("ZIP file extracted successfully.");
            } catch (IOException e) {
                System.err.println("Error occurred while extracting ZIP file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }