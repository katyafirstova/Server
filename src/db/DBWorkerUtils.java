package db;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import model.*;
import org.slf4j.*;

/**
 * {@code DBWorkerUtils} database utilities
 */
public class DBWorkerUtils {

    static final Logger LOG = LoggerFactory.getLogger(DBWorkerUtils.class);
    private static final String DB_USER = "pg";
    private static final String DB_PASSWORD = "pg";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    private Connection getDBConnection() {
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

    public int insertCoordinates(float x, int y) {
        LOG.debug(String.format("insertCoordinates: x=%f, y=%d", x, y));
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = null;
        int primkey = -1;
        try {
            String sql = "insert into coordinates(x, y) values(?, ?)";
            preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setFloat(1, x);
            preparedStatement.setInt(2, y);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                primkey = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException exception) {
                LOG.debug(exception.getMessage());
            }
        }
        return primkey;
    }

    public int insertPerson(float height, Integer weight, Color color) {
        LOG.debug(String.format("insertPerson: height=%f, weight=%d, color=%s", height, weight, color.getName()));
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = null;
        int primkey = -1;
        try {
            String sql = "insert into person(height, weight, color_id) values(?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setFloat(1, height);
            preparedStatement.setInt(2, weight);
            preparedStatement.setInt(3, getColorId(color));
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                primkey = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException exception) {
                LOG.debug(exception.getMessage());
            }
        }

        return primkey;
    }


    public void insertWorker(String name, int coordinates_id, int salary,
                             LocalDate startDate, Date endDate, Status status, int person_id) {
        LOG.debug(String.format("insertWorker"));
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "insert into worker (name, coordinates_id, salary, startDate, endDate, status_id, person_id)" +
                    " values (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, coordinates_id);
            preparedStatement.setInt(3, salary);
            preparedStatement.setDate(4, java.sql.Date.valueOf(startDate.toString()));
            LocalDate localEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            preparedStatement.setDate(5, java.sql.Date.valueOf(localEndDate.toString()));
            preparedStatement.setInt(6, getStatusId(status));
            preparedStatement.setInt(7, person_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
        } catch (Exception e) {
            LOG.debug(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException exception) {
                LOG.debug(exception.getMessage());
            }
        }
    }

    public void insertWorker(Worker worker) {
        Coordinates coordinates = worker.getCoordinates();
        Person person = worker.getPerson();
        int coordinates_id = insertCoordinates(coordinates.getX(), coordinates.getY());
        int person_id = insertPerson(person.getHeight(), person.getWeight(), person.getHairColor());
        insertWorker(worker.getName(), coordinates_id, worker.getSalary(), worker.getStartDate(), worker.getEndDate(),
                worker.getStatus(), person_id);
    }

    public Integer getColorId(Color color) {
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = null;
        Integer id = null;
        try {
            String sql = "select id from color where name = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, color.getName().toLowerCase(Locale.ROOT));
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            id = rs.getInt("id");
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException exception) {
                LOG.debug(exception.getMessage());
            }
        }
        return id;
    }

    public Integer getStatusId(Status status) {
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = null;
        Integer id = null;
        try {
            String sql = "select id from status where name = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status.getName().toLowerCase(Locale.ROOT));
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            id = rs.getInt("id");
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException exception) {
                LOG.debug(exception.getMessage());
            }
        }
        return id;
    }
}

