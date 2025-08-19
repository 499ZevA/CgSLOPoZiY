// 代码生成时间: 2025-08-19 16:16:33
import io.micronaut.context.annotation.Requires;
    import io.micronaut.http.annotation.Controller;
    import io.micronaut.http.annotation.Get;
    import io.micronaut.http.HttpStatus;
    import javax.inject.Singleton;
    import java.net.InetAddress;
    import java.net.UnknownHostException;

    /**
     * 网络连接状态检查器控制器
     */
    @Controller("/network")
    @Singleton
    @Requires(property = "app.networkcheck.enabled", value = "true")
    public class NetworkStatusChecker {

        /**
         * 检查给定主机的网络连接状态
         *
         * @param host 要检查的主机地址
         * @return 包含连接状态的响应
         */
        @Get("/check")
        public String checkNetworkConnection(String host) {
            try {
                // 尝试解析主机地址
                InetAddress.getByName(host);
                // 如果解析成功，表示网络连接正常
                return "Network connection to host is OK.";
            } catch (UnknownHostException e) {
                // 如果发生异常，表示网络连接失败
                return "Network connection to host failed.";
            }
        }
    }