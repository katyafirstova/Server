package db;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Locale;

import model.Color;
import model.Coordinates;
import model.Person;
import model.Status;
import org.slf4j.*;

/**
 * {@code DBUtils} database utilities
 */
public class DBUtils {

    static final Logger LOG = LoggerFactory.getLogger(DBUtils.class);
    private static final String DB_USER = "postgre";
    private static final String DB_PASSWORD = "postgre";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/contactdb";


    private Connection getDBConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Соединение установлено");
            LOG.info("Соединение установлено");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер не найден");
            LOG.debug("Драйвер не найден");
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных");
            LOG.debug("Ошибка при подключении к базе данных");
        }
        return connection;
    }

    public int insertCoordinates(float x, int y) {
        PreparedStatement preparedStatement = null;
        int primkey = -1;
        try {
            String sql = "insert into coordinates(x, y) values(?, ?)";
            Connection connection = getDBConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, x);
            preparedStatement.setInt(2, y);
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                primkey = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }
        return primkey;
    }


    public int insertPerson(float height, Integer weight, Color color) {
        PreparedStatement preparedStatement = null;
        int primkey = -1;
        try {
            String sql = "insert into person(height, weight, color_id) values(?, ?, ?)";
            Connection connection = getDBConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, height);
            preparedStatement.setInt(2, weight);
            preparedStatement.setInt(3, getColorId(color));
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                primkey = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }
        return primkey;
    }


    public void insertWorker(String name, int coordinates_id, int salary,
                             LocalDate startDate, Date endDate, Status status, int person_id) {
        PreparedStatement preparedStatement = null;
        try {
            String sql = "insert into worker (name, coordinates_id, salary, startDate, endDate, status_id, person_id)" +
                    " values (?, ?, ?, ?, ?)";
            Connection connection = getDBConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, coordinates_id);
            preparedStatement.setInt(3, salary);
            preparedStatement.setDate(4, java.sql.Date.valueOf(startDate.toString()));
            preparedStatement.setDate(5, java.sql.Date.valueOf(endDate.toString()));
            preparedStatement.setInt(6, getStatusId(status));
            preparedStatement.setInt(7, person_id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public Integer getColorId(Color color) {
        PreparedStatement preparedStatement = null;
        Integer id = null;
        try {
            String sql = "select id from color where name = ?";
            Connection connection = getDBConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, color.getName().toLowerCase(Locale.ROOT));
            ResultSet rs = preparedStatement.executeQuery();
            id = rs.getInt("id");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }
        return id;
    }

    public Integer getStatusId(Status status) {
        PreparedStatement preparedStatement = null;
        Integer id = null;
        try {
            String sql = "select id from status where name = ?";
            Connection connection = getDBConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status.getName().toLowerCase(Locale.ROOT));
            ResultSet rs = preparedStatement.executeQuery();
            id = rs.getInt("id");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }
        return id;
    }


}

