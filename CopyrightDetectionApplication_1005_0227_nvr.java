// 代码生成时间: 2025-10-05 02:27:50
package com.example.copyright;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.HttpResponse;
import io.reactivex.Single;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Controller("/api/copyright")
public class CopyrightDetectionController {

    private static final String COPYRIGHT_PATTERN = "(C)\s*(\d{4})\s*(.*?)\s*All rights reserved.";

    private final CopyrightService copyrightService;

    public CopyrightDetectionController(CopyrightService copyrightService) {
        this.copyrightService = copyrightService;
    }

    @Get("/detect/{file}")
    public Single<HttpResponse<String>> detectCopyright(@PathVariable String file) {
        return Single.fromCallable(() -> {
            try {
                String content = new String(Files.readAllBytes(Paths.get(file)));
                boolean hasCopyright = copyrightService.detect(content);
                return HttpResponse.ok("Copyright detected: " + hasCopyright);
            } catch (Exception e) {
                // Handle file not found or read errors
                return HttpResponse.serverError(e.getMessage());
            }
        });
    }
}

class CopyrightService {

    private static final Pattern pattern = Pattern.compile(COPYRIGHT_PATTERN);

    public boolean detect(String content) {
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }
}
