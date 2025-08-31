// 代码生成时间: 2025-08-31 12:47:39
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import javax.inject.Inject;
import java.net.URI;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

// 使用Micronaut测试注解，标记这是一个集成测试
@MicronautTest
public class IntegrationTestExample implements TestPropertyProvider {

    // 注入HttpClient客户端，用于发送HTTP请求
    @Inject
    @Client("/")
    HttpClient httpClient;

    // 提供测试所需的属性
    @Override
    public Map<String, String> getProperties() {
        return Map.of(
            "micronaut.server.context-path", "/test",
            "micronaut.server.port", "8081"
        );
    }

    // 测试用例：测试GET请求
    @Test
    void testGetRequest() {
        try {
            // 发送GET请求到/test/health端点
            var response = httpClient.toBlocking().exchange(
                HttpRequest.GET("/test/health"),
                String.class
            );
            // 断言响应状态码为200
            response.status().isOk();
            // 断言响应体包含预期的字符串
            assertEquals("OK", response.body().get());
        } catch (HttpClientException e) {
            // 错误处理：打印错误信息
            Assertions.fail("HTTP client exception occurred: " + e.getMessage());
        }
    }

    // 工厂方法：创建HttpClient实例
    @Factory
    public HttpClient createHttpClient() {
        return HttpClient.newBuilder()
            // 配置HttpClient的BaseURI
            .baseUri(URI.create("http://localhost:8081"))
            .build();
    }
}
