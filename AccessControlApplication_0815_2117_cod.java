// 代码生成时间: 2025-08-15 21:17:39
package com.example.micronaut.accesscontrol;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.token.jwt.claims.Claims;
import io.micronaut.core.annotation.Introspected;
import java.util.Map;

// 定义访问控制的接口
@Introspected
@Requires(property = "security.enabled")
@Controller("/access")
public class AccessControlApplication {

    // 访问控制接口，检查用户是否有权限访问
    @Get("/check")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public String checkAccess(Claims claims) {
        String role = claims.get("role", String.class).orElse("GUEST");
        if (!role.equals("ADMIN")) {
            throw new AccessDeniedException("Access Denied: User does not have required permissions");
        }
        return "Access granted";
    }

    // 定义自定义的异常类
    public static class AccessDeniedException extends RuntimeException {
        public AccessDeniedException(String message) {
            super(message);
        }
    }
}

// 配置文件 (application.yml)
// security:
//   enabled: true
//   oauth2:
//     clients:
//       client-id: "your-client-id"
//     token:
//       claims:
//         role:
//           type: "string"
