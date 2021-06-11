package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

public class User extends Hasher implements Serializable {
    static final Logger LOG = LoggerFactory.getLogger(User.class);
    private String userName;
    private String userPassword;
    private long userId;

    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public static User createUser(String userName, String userPassword) {
        User user = new User(userName, userPassword);
        try {
            Hasher hasher = new Hasher();
            hasher.getSecurePassword(userPassword, hasher.getSalt());
        } catch (NoSuchAlgorithmException e) {
            LOG.debug(e.getLocalizedMessage());
        }
        return user;
    }
}
