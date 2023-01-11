package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS User (" +
                        "    id INT NOT NULL AUTO_INCREMENT," +
                        "    name VARCHAR(50)," +
                        "    lastName VARCHAR(50)," +
                        "    age INT," +
                        "    PRIMARY KEY (id)" +
                        ")";
        try (Connection connection = Util.getConnection(); Statement stmt = connection.createStatement();) {
            stmt.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS User";
        try (Connection connection = Util.getConnection(); Statement stmt = connection.createStatement();) {
            stmt.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO User (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection(); PreparedStatement stmt = connection.prepareStatement(query);) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM User WHERE id = ?";
        try (Connection connection = Util.getConnection(); PreparedStatement stmt = connection.prepareStatement(query);) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM User";
        List<User> resultList = new ArrayList<>();
        try (Connection connection = Util.getConnection(); Statement stmt = connection.createStatement();) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                resultList.add(new User(name, lastName, age));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    public void cleanUsersTable() {
        String query = "DELETE FROM User";
        try (Connection connection = Util.getConnection(); PreparedStatement stmt = connection.prepareStatement(query);) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
