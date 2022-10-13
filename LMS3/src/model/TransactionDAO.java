package model;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TransactionDAO {
    Connection connection;

    public TransactionDAO(String url) throws SQLException {
        connection = DriverManager.getConnection(url);
    }

    public void insert(Transaction t) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("insert into libTransactions values (?,?,?,true)");
            prepareStatement.setInt(1, t.getBookID());
            prepareStatement.setInt(2, t.getUserID());
            prepareStatement.setDate(3, dateTimeToSQLDate(t.getIssueDate()));
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    public void delete(Transaction t) throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("delete from libTransactions where bookID = ? and userID = ? and issueDate = ?");
        prepareStatement.setInt(1, t.getBookID());
        prepareStatement.setInt(2, t.getUserID());
        prepareStatement.setDate(3, dateTimeToSQLDate(t.getIssueDate()));
        prepareStatement.executeUpdate();
    }

    public void update(Transaction t) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("update libTransactions set status = ? where bookID = ? and userID = ? and issueDate = ?");
            prepareStatement.setBoolean(1, t.isStatus());
            prepareStatement.setInt(2, t.getBookID());
            prepareStatement.setInt(3, t.getUserID());
            prepareStatement.setDate(4, dateTimeToSQLDate(t.getIssueDate()));
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    public ArrayList<Transaction> getAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from libTransactions");
            ResultSet rs = preparedStatement.executeQuery();
            return addResultToList(rs);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<Transaction> getCurrent() {
        try {
            PreparedStatement  preparedStatement = connection.prepareStatement("select * from libTransactions where status = true");
            ResultSet rs = preparedStatement.executeQuery();
            return addResultToList(rs);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<Transaction> getByUser(int uID) {
        try {
            PreparedStatement  preparedStatement = connection.prepareStatement("select * from libTransactions where userID = ?");
            preparedStatement.setInt(1, uID);
            ResultSet rs = preparedStatement.executeQuery();
            return addResultToList(rs);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<Transaction> getByBook(int bID) {
        try {
            PreparedStatement  preparedStatement = connection.prepareStatement("select * from libTransactions where bookID = ?");
            preparedStatement.setInt(1, bID);
            ResultSet rs = preparedStatement.executeQuery();
            return addResultToList(rs);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private ArrayList<Transaction> addResultToList(ResultSet rs) throws SQLException {
        ArrayList<Transaction> result = new ArrayList<>();
        while (rs.next()) {
            Transaction t = new Transaction(rs.getInt(1), rs.getInt(2));
            t.setIssueDate(rs.getTimestamp(3).toLocalDateTime());
            t.setStatus(rs.getBoolean(4));
            result.add(t);
        }
        return result;
    }

    private java.sql.Date dateTimeToSQLDate(LocalDateTime dateValue) {
        java.util.Date utilDate;
        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern(dateFormat);
        SimpleDateFormat sdf1 = new SimpleDateFormat(dateFormat);
        try {
            utilDate = sdf1.parse(dateValue.format(dtf1));
        } catch (ParseException e) {
            utilDate = null; // handle the exception
        }
        return new java.sql.Date(utilDate.getTime());
    }
}
