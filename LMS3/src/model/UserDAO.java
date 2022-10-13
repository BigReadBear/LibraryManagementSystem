package model;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO {
    Connection connection = null;

    public UserDAO(String url) throws SQLException {
        connection = connection = DriverManager.getConnection(url);
    }

    public int insert(User u) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users values (?,?,?,?,?,?,?)");
            preparedStatement.setString(2, u.getName());
            preparedStatement.setString(3, u.getEmail());
            preparedStatement.setString(4, u.getAddress());
            preparedStatement.setDate(5, Date.valueOf(u.getDateOfBirth()));
            preparedStatement.setBoolean(6, u.getStudent());
            preparedStatement.setDouble(7, u.getBalance());
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("SELECT last_insert_rowid()");
            ResultSet rs = preparedStatement.executeQuery();
            return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return 0;
        }
    }

    public void delete(User b) throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("delete from users where ID = ?");
        prepareStatement.setInt(1, b.getID());
        prepareStatement.executeUpdate();
    }

    public void update(User u) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("update users set name=?, email=?, address=?, dateOfBirth=?, isStudent=?, balance=? where ID= ?");
            preparedStatement.setString(1, u.getName());
            preparedStatement.setString(2, u.getEmail());
            preparedStatement.setString(3, u.getAddress());
            preparedStatement.setDate(4, Date.valueOf(u.getDateOfBirth()));
            preparedStatement.setBoolean(5, u.getStudent());
            preparedStatement.setDouble(6, u.getBalance());
            preparedStatement.setInt(7, u.getID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            ;
        }

    }

    public ArrayList<User> getAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
            ResultSet rs = preparedStatement.executeQuery();
            return addResultToList(rs);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<User> getByQuery(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where name LIKE ?");
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet rs = preparedStatement.executeQuery();
            return addResultToList(rs);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<User> getUser(int uID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where ID = ?");
            preparedStatement.setInt(1, uID);
            ResultSet rs = preparedStatement.executeQuery();
            return addResultToList(rs);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private ArrayList<User> addResultToList(ResultSet rs) throws SQLException {
        ArrayList<User> result = new ArrayList<>();
        while (rs.next()) {
            User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                    rs.getDate(5).toLocalDate(), rs.getBoolean(6), rs.getDouble(7));
            result.add(u);
        }
        return result;
    }
}
