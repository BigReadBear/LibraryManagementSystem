package model;

import java.sql.*;
import java.util.ArrayList;

public class BookDAO {

    Connection connection = null;

    public BookDAO(String url) throws SQLException {
        connection = connection = DriverManager.getConnection(url);
    }

    public int insert(Book b) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("insert into books values (?,?,?,?,?,?,?)");
            preparedStatement.setString(2, b.getName());
            preparedStatement.setString(3, b.getAuthor());
            preparedStatement.setString(4, b.getPublisher());
            preparedStatement.setString(5, b.getGenre());
            preparedStatement.setString(6, b.getISBN());
            preparedStatement.setLong(7, b.getYear());
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("SELECT last_insert_rowid()");
            ResultSet rs = preparedStatement.executeQuery();
            return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return 0;
        }
    }

    public void delete(Book b) throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("delete from books where ID = ?");
        prepareStatement.setInt(1, b.getID());
        prepareStatement.executeUpdate();
    }

    public void update(Book b) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update books set name=?, author=?, publisher=?, genre=?, ISBN=?, year=? where ID= ?");
        preparedStatement.setString(1, b.getName());
        preparedStatement.setString(2, b.getAuthor());
        preparedStatement.setString(3, b.getPublisher());
        preparedStatement.setString(4, b.getGenre());
        preparedStatement.setString(5, b.getISBN());
        preparedStatement.setLong(6, b.getYear());
        preparedStatement.setInt(7, b.getID());
        preparedStatement.executeUpdate();
    }

    public ArrayList<Book> getAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books");
            ArrayList<Book> result = new ArrayList<>();
            ResultSet rs = preparedStatement.executeQuery();
            return addResultToList(rs);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<Book> getByQuery(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books where name LIKE ?");
            preparedStatement.setString(1, "%" + title + "%");
            ResultSet rs = preparedStatement.executeQuery();
            return addResultToList(rs);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<Book> getBook(int bID) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from books where ID = ?");
            preparedStatement.setInt(1, bID);
            ResultSet rs = preparedStatement.executeQuery();
            return addResultToList(rs);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private ArrayList<Book> addResultToList(ResultSet rs) throws SQLException {
        ArrayList<Book> result = new ArrayList<>();
        while (rs.next()) {
            Book b = new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(6), rs.getLong(7));
            result.add(b);
        }
        return result;
    }
}
