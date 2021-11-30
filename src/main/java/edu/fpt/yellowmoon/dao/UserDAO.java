package edu.fpt.yellowmoon.dao;

import edu.fpt.yellowmoon.constant.DBConstants;
import edu.fpt.yellowmoon.dto.User;
import edu.fpt.yellowmoon.util.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private Connection connection;
    private PreparedStatement preStm;
    private ResultSet resultSet;

    private static final String SQL_REGISTER =
            String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)", DBConstants.TABLE_USER,
                    DBConstants.USER_EMAIL, DBConstants.USER_FULL_NAME, DBConstants.USER_PASSWORD, DBConstants.USER_ROLE);

    private static final String SQL_DEFAULT_LOGIN = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s=? AND %s=?",
            DBConstants.USER_ID, DBConstants.USER_FULL_NAME, DBConstants.USER_ROLE, DBConstants.USER_ADDRESS,
            DBConstants.TABLE_USER, DBConstants.USER_EMAIL, DBConstants.USER_PASSWORD);

    private static final String SQL_EXIST_EMAIL = String.format("SELECT %s FROM %s WHERE %s=?",
            DBConstants.USER_ID, DBConstants.TABLE_USER, DBConstants.USER_EMAIL);

    public boolean checkExistEmail(String email) {
        boolean isExist = false;

        try {
            connection = DatabaseUtils.getConnection();
            preStm = connection.prepareStatement(SQL_EXIST_EMAIL);
            preStm.setString(1, email);
            resultSet = preStm.executeQuery();
            isExist = resultSet.next();

        } catch (Exception e) {
            logger.error("Error checking existed email", e);
        } finally {
            close();
        }
        return isExist;
    }

    public boolean register(User user) {
        boolean isSuccess = false;

        try {
            connection = DatabaseUtils.getConnection();
            preStm = connection.prepareStatement(SQL_REGISTER);
            preStm.setString(1, user.getEmail());
            preStm.setString(2, user.getFullName());
            preStm.setString(3, user.getPassword());
            preStm.setInt(4, user.getRoleId());

            isSuccess = preStm.executeUpdate() > 0;
        } catch (Exception e) {
            logger.error("User registration error", e);
        } finally {
            close();
        }
        return isSuccess;
    }

    public User loginByEmailPassword(String email, String password) {
        User user = null;
        try {
            connection = DatabaseUtils.getConnection();
            preStm = connection.prepareStatement(SQL_DEFAULT_LOGIN);
            preStm.setString(1, email);
            preStm.setString(2, password);
            resultSet = preStm.executeQuery();

            if(resultSet.next()) {
                int id = resultSet.getInt(DBConstants.USER_EMAIL);
                int roleId = resultSet.getInt(DBConstants.ROLE_ID);
                String address = resultSet.getString(DBConstants.USER_ADDRESS);
                String fullName = resultSet.getString(DBConstants.USER_FULL_NAME);
                user = User.builder()
                        .id(id)
                        .roleId(roleId)
                        .address(address)
                        .fullName(fullName)
                        .build();
            }

        } catch (Exception e) {
            logger.error("Login failed", e);
        } finally {
            close();
        }
        return user;
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Closing database resources failed", e);
        } catch (NullPointerException e) {}
    }

    private static class InstanceHolder {
        private static UserDAO INSTANCE = new UserDAO();
    }

    public static UserDAO getInstance() {
        return InstanceHolder.INSTANCE;
    }
}