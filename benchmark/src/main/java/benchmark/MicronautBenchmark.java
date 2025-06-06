package benchmark;

import benchmark.config.MicronautDatabaseInitializer;
import io.micronaut.context.ApplicationContext;
import io.micronaut.data.connection.ConnectionDefinition;
import io.micronaut.data.connection.ConnectionOperations;
import io.micronaut.data.connection.jdbc.operations.DefaultDataSourceConnectionOperations;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@State(Scope.Benchmark)
public class MicronautBenchmark {

    private static final Map<String, Object> MICRONAUT_CONFIG;

    static {
        MICRONAUT_CONFIG = new HashMap<>();
        MICRONAUT_CONFIG.put("datasources.default.url", "jdbc:h2:mem:micronauttestdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        MICRONAUT_CONFIG.put("datasources.default.driver-class-name", "org.h2.Driver");
        MICRONAUT_CONFIG.put("datasources.default.username", "sa");
        MICRONAUT_CONFIG.put("datasources.default.pool-name", "MicronautHikariPool");
        MICRONAUT_CONFIG.put("datasources.default.maximum-pool-size", 10);
        MICRONAUT_CONFIG.put("datasources.default.minimum-idle", 5);
        MICRONAUT_CONFIG.put("datasources.default.idle-timeout", 30000);
        MICRONAUT_CONFIG.put("datasources.default.max-lifetime", 1800000);
        MICRONAUT_CONFIG.put("datasources.default.connection-timeout", 30000);
        MICRONAUT_CONFIG.put("datasources.default.leak-detection-threshold", 0);
        MICRONAUT_CONFIG.put("datasources.default.validation-timeout", 5000);

        MICRONAUT_CONFIG.put("logger.levels.io.micronaut.context.env", "INFO");
    }

    @Param({"10", "20", "50", "100", "200", "500", "1000", "10000"})
    private int dataCount;

    private ApplicationContext micronautContext;

    private ConnectionOperations<Connection> connectionOperations;

    @Setup
    public void initialize() throws SQLException, IOException {
        micronautContext = ApplicationContext.run(MICRONAUT_CONFIG);
        connectionOperations = micronautContext.getBean(DefaultDataSourceConnectionOperations.class);
        micronautContext.registerSingleton(MicronautDatabaseInitializer.class, new MicronautDatabaseInitializer(connectionOperations));
        MicronautDatabaseInitializer micronautInitializer = micronautContext.getBean(MicronautDatabaseInitializer.class);
        micronautInitializer.initialize(dataCount);
    }

    @Benchmark
    public void testMicronautWithDatasource() {
        connectionOperations.execute(ConnectionDefinition.DEFAULT, status -> {
            try {
                Connection connection = status.getConnection();
                try (var stmt = connection.createStatement()) {
                    return stmt.execute("SELECT * FROM data_micronaut");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @TearDown
    public void tearDown() {
        if (micronautContext != null) {
            micronautContext.close();
        }
    }
}
