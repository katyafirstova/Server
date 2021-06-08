package db;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import model.*;
import org.slf4j.*;

/**
 * {@code DBWorkerUtils} database utilities
 */
public class DBWorkerUtils extends DBConnection {

    static final Logger LOG = LoggerFactory.getLogger(DBWorkerUtils.class);

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


    public long insertWorker(long worker_id, String name, int coordinates_id, int salary,
                                LocalDate startDate, Date endDate, Status status, int person_id, long user_id) {
        LOG.debug(String.format("insertWorker"));
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = null;
        long primkey = -1;
        try {
            String sql = "insert into worker (worker_id, name, coordinates_id, salary, startDate, endDate," +
                    " status_id, person_id, user_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, worker_id);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, coordinates_id);
            preparedStatement.setInt(4, salary);
            preparedStatement.setDate(5, java.sql.Date.valueOf(startDate.toString()));
            LocalDate localEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            preparedStatement.setDate(6, java.sql.Date.valueOf(localEndDate.toString()));
            preparedStatement.setInt(7, getStatusId(status));
            preparedStatement.setInt(8, person_id);
            preparedStatement.setLong(9, user_id);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                primkey = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
            return -1;
        } catch (Exception e) {
            LOG.debug(e.getMessage());
            return -1;
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

    public long insertWorker(Worker worker) {
        Coordinates coordinates = worker.getCoordinates();
        Person person = worker.getPerson();
        int coordinates_id = insertCoordinates(coordinates.getX(), coordinates.getY());
        int person_id = insertPerson(person.getHeight(), person.getWeight(), person.getHairColor());
        return insertWorker(worker.getId(), worker.getName(), coordinates_id, worker.getSalary(), worker.getStartDate(),
                worker.getEndDate(), worker.getStatus(), person_id, worker.getUserId());
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

    public Integer getWorkerId(Worker worker) {
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = null;
        Integer id = null;
        try {
            String sql = "select id from worker where name = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, worker.getName().toLowerCase(Locale.ROOT));
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

    public boolean deleteWorkerById(long id) {
        LOG.debug(String.format("deleteWorkerById"));
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = null;
        Worker worker = new Worker();
        try {
            String sql = "delete from worker where worker_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            LOG.debug(String.format("Deleted row, id = %d, res = %d ", id, result));
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
            return false;
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
        return true;
    }


    public boolean deleteWorker() {
        LOG.debug(String.format("deleteWorker"));
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "truncate worker";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
            return false;
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
        return true;
    }

    public boolean deleteWorkerByGreaterSalary(int salary) {
        LOG.debug(String.format("deleteWorkerByGreaterSalary"));
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "delete from worker where salary >= ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, salary);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
            return false;
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
        return true;
    }

    public boolean deleteWorkerByLowerSalary(int salary) {
        LOG.debug(String.format("deleteWorkerByLowerSalary"));
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "delete from worker where salary <= ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, salary);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
            return false;
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
        return true;
    }

    public boolean deleteWorkerByEndDate(Date endDate) {
        LOG.debug(String.format("deleteWorkerByEndDate"));
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "delete from worker where enddate = ?";
            preparedStatement = connection.prepareStatement(sql);
            java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
            preparedStatement.setDate(1, sqlEndDate);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
            return false;
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
        return true;


    }

    public boolean deleteWorkerByStartDate(LocalDate startDate) {
        LOG.debug(String.format("deleteWorkerByStartDate"));
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "delete from worker where startdate = ?";
            preparedStatement = connection.prepareStatement(sql);
            Instant instant = Instant.from(startDate.atStartOfDay(ZoneId.of("GMT")));
            Date newStartDate = Date.from(instant);
            java.sql.Date sqlStartDate = new java.sql.Date(newStartDate.getTime());
            preparedStatement.setDate(1, sqlStartDate);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException exception) {
                LOG.debug(exception.getMessage()); }

        }
        return true;
    }
}

