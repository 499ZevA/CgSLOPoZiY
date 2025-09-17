// 代码生成时间: 2025-09-18 03:29:16
package com.example.demo;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.server.exceptions.context.ExceptionHandlerAdviceContext;
import io.micronaut.http.server.exceptions.context.ExceptionHandlerAdviceRegistry;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.web.router.RouteData;

@Controller("/example")
public class HttpRequestHandler {

    /**
     * 处理GET请求
     *
     * @return 返回响应消息
     */
    @Get("/")
    public HttpResponse<String> handleGetRequest() {
        // 模拟业务逻辑处理
        return HttpResponse.ok("Hello, this is a GET request to the example handler!");
    }

    /**
     * 异常处理器
     *
     * @param context 上下文信息
     * @return 返回异常处理结果
     */
    @ExceptionHandler(Exception.class)
    public HttpResponse<String> handleException(ExceptionHandlerAdviceContext context) {
        Exception exception = context.getException();
        String message = "An error occurred: " + exception.getMessage();
        // 根据异常类型返回不同的状态码和消息
        if (exception instanceof HttpStatusException) {
            return HttpResponse.status(((HttpStatusException) exception).getStatus(), message);
        } else {
            return HttpResponse.serverError(message);
        }
    }

    /**
     * 演示如何添加自定义异常处理
     *
     * @param registry 注册器
     */
    public void registerExceptionHandlers(ExceptionHandlerAdviceRegistry registry) {
        registry.addExceptionHandler(Exception.class, this::handleException);
    }
}
