// 代码生成时间: 2025-09-22 13:01:31
package io.micronaut.basics.sqlinjection;

import io.micronaut.context.annotation.Factory;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.transaction.SynchronousTransactionManager;
import javax.inject.Singleton;
import javax.sql.DataSource;
import io.micronaut.transaction.jdbc.DataSourceTransactionManager;
import jakarta.inject.Named;
import jakarta.inject.Provider;
import jakarta.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

@Controller("/sqlinjection")
public class SqlInjectionController {

    private final DataSource dataSource;

    public SqlInjectionController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Get("/search")
    @Transactional
    public String searchByUsername(@QueryValue String username) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return "User found with username: " + username;
            } else {
                return "User not found with username: " + username;
            }
        } catch (SQLException e) {
            // Handle SQL Exception - logging, error reporting
            return "Error occurred while searching for user: " + e.getMessage();
        }
    }
}

@Factory
public class TransactionManagerFactory {

    @Singleton
    public SynchronousTransactionManager<Connection> transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Singleton
    @Named(TaskExecutors.IO)
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(10); // Adjust thread count as necessary
    }
}