// 代码生成时间: 2025-08-01 00:44:40
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.env.Environment;
import io.micronaut.runtime.Micronaut;
import javax.sql.DataSource;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

// 数据库迁移工具类
@Factory
@Requires(env = Environment.CLI)
public class DatabaseMigrationTool {

    private final DataSource dataSource;
    private final String changeLogFile;
    private final String driver;
    private final String url;
    private final String username;
    private final String password;

    // 构造函数注入
    public DatabaseMigrationTool(
            @Value('${datasource.driver}') String driver,
            @Value('${datasource.url}') String url,
            @Value('${datasource.username}') String username,
            @Value('${datasource.password}') String password,
            @Value('${liquibase.changelog.file}') String changeLogFile) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.changeLogFile = changeLogFile;
        this.dataSource = Micronaut.datasource();
    }

    // 执行数据库迁移
    public void migrateDatabase() {
        try {
            // 创建数据库连接
            Database database = DatabaseFactory.getInstance()
                    .open(dataSource);

            // 创建资源访问器
            ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();

            // 创建Liquibase迁移对象
            Liquibase liquibase = new Liquibase(changeLogFile, resourceAccessor, database);

            // 执行迁移
            liquibase.update(null);

            // 关闭数据库连接
            database.close();

            System.out.println("数据库迁移完成");

        } catch (Exception e) {
            // 错误处理
            System.err.printf("数据库迁移失败: %s%n", e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Micronaut.run(DatabaseMigrationTool.class, args);
    }
}
