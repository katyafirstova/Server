package db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static final Logger LOG = LoggerFactory.getLogger(DBConnection.class);
    private static final String DB_USER = "s309544";
    private static final String DB_PASSWORD = "jvd076";
    private static final String DB_URL = "jdbc:postgresql://localhost:5430/studs";

    protected Connection getDBConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            connection.setAutoCommit(true);
            LOG.info("Соединение установлено");
        } catch (ClassNotFoundException e) {
            LOG.debug("Драйвер не найден");
        } catch (SQLException e) {
            LOG.debug(String.format("Ошибка при подключении к базе данных: %s", e.getMessage()));
        }
        return connection;
    }
}
