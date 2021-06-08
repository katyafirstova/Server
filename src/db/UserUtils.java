package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.*;

public class UserUtils extends DBConnection {

    static final Logger LOG = LoggerFactory.getLogger(UserUtils.class);

    public boolean insertUser(String userName, String userPassword) {
        LOG.debug(String.format("insertUser"));
        Connection connection = getDBConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "insert into user(username, userpassword)" +
                    " values (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userPassword);
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
            return false;
        } catch (Exception e) {
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

}
