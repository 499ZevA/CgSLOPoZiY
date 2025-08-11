// 代码生成时间: 2025-08-11 08:13:48
import io.micronaut.context.annotation.Factory;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.core.io.ResourceLoader;
import io.micronaut.http.multipart.MultipartBody;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Singleton;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

// CsvBatchProcessor class is responsible for processing batch CSV file uploads.
@Controller("/csv")
public class CsvBatchProcessor {

    private final ResourceLoader resourceLoader;

    // Dependency injection of ResourceLoader.
    public CsvBatchProcessor(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    // Handles POST requests to process multiple CSV files.
    @Post(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA)
    public HttpResponse<String> processCsvFiles(MultipartBody body) {
        try {
            List<CompletedFileUpload> files = body.getFiles().stream()
                .map(file -> file.complete())
                .collect(Collectors.toList());

            // Process each CSV file.
            for (CompletedFileUpload fileUpload : files) {
                InputStream inputStream = fileUpload.getInputStream();
                CSVParser csvParser = new CSVParser(inputStream, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withTrim());

                for (CSVRecord record : csvParser) {
                    // Process each record according to the CSV structure.
                    // Example: Print out the record values.
                    System.out.println(record.toMap());
                }
            }

            // Return OK response after processing all files.
            return HttpResponse.ok("All CSV files have been processed.");
        } catch (IOException e) {
            // Handle exceptions and return internal server error response.
            return HttpResponse.serverError("Error processing CSV files: " + e.getMessage());
        }
    }
}

// Factory class to configure the embedded server settings.
@Factory
public class EmbeddedServerConfig {

    // Configure the embedded server to start on the specified port.
    public EmbeddedServer embeddedServer(int port) {
        return EmbeddedServer.builder()
                .port(port)
                .build();
    }
}
