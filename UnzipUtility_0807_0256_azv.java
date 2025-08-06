// 代码生成时间: 2025-08-07 02:56:08
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.micronaut.core.annotation.Introspected;
import jakarta.inject.Singleton;

/**
 * A utility class for unzipping files using Java.
 */
@Singleton
@Introspected
public class UnzipUtility {

    private static final String ZIP_FILE_PATH = "/path/to/zip/file.zip";
    private static final String DESTINATION_FOLDER = "/path/to/destination/folder";

    /**
     * Unzips the specified zip file to the destination folder.
     *
     * @param zipFilePath The path to the zip file.
     * @param destinationFolder The destination folder path.
     * @throws IOException If an I/O error occurs.
     */
    public void unzip(String zipFilePath, String destinationFolder) throws IOException {
        File destDir = new File(destinationFolder);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        while (entry != null) {
            String filePath = destinationFolder + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                extractFile(zipIn, filePath);
            } else {
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    /**
     * Extracts a single file from the zip input stream.
     *
     * @param zipIn The zip input stream.
     * @param filePath The path of the file to extract.
     * @throws IOException If an I/O error occurs.
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}
