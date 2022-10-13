package model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Library {
    private final int MAX_BOOK_LIMIT = 3;
    private final int MAX_LOAN_DAYS = 14;


    public TransactionDAO transactions;
    public UserDAO users;
    public BookDAO books;
    public ArrayList<String> msgLog;

    public Library() {
        try {
            String url = "jdbc:sqlite:C:\\sqlite3\\lib.db";
            transactions = new TransactionDAO(url);
            users = new UserDAO(url);
            books = new BookDAO(url);
            msgLog = new ArrayList<String>();
        } catch (SQLException e) {
            System.out.println("No connection with database, check database");
            System.exit(1);
        }
    }

    public void addBook(Book b) {

        int bID = books.insert(b);
        msgLog.add("Book ID " + bID + " added to Library");
    }

    public void addUser(User u) {
        int uID = users.insert(u);
        msgLog.add("User ID " + uID + " added to Library");
    }

    public void addTransaction(Transaction t) {
        transactions.insert(t);
    }

    public boolean isAvailable(int bookID) {
        for (Transaction t : transactions.getCurrent()) {
            if (t.getBookID() == bookID)
                return false;
        }
        return true;

    }

    public int getBorrowCount(int userID) {
        int count = 0;
        for (Transaction t : transactions.getCurrent()) {
            if (t.getUserID() == userID)
                count++;
        }
        return count;
    }


    public LocalDate getDueDate(LocalDateTime issueDate) {
        return issueDate.toLocalDate().plusDays(MAX_LOAN_DAYS);
    }


    public boolean issueBook(int userID, int bookID) {
        User u = getUser(userID);
        Book b = getBook(bookID);

        //check if book is unavailable
        if (!isAvailable(bookID)) {
            msgLog.add("This book is currently unavailable!");
            return false;
        }

        //check if max books limit has been reached or outstanding fines
        if (getBorrowCount(userID) >= MAX_BOOK_LIMIT) {
            msgLog.add("User has reached the maximum limit of borrowed books!");
            return false;
        }
        //check if user has outstanding balance
        if (u.getBalance() > 0) {
            msgLog.add("User has an outstanding balance of $" + u.getBalance() + "!");
            return false;
        }

        // ready to issue book.
        Transaction trans = new Transaction(bookID, userID);
        transactions.insert(trans);
        msgLog.add(b.getName() + " has been issued to " + u.getName() + ".");
        msgLog.add("The due date is " + getDueDate(trans.getIssueDate()));
        return true;
    }

    public boolean returnBook(int bookID) {
        Transaction trans = null;
        for (Transaction t : transactions.getCurrent())
            if (t.getBookID() == bookID) {
                trans = t;
                break;
            }
        if (trans == null) {
            msgLog.add("Book currently not borrowed");
            return false;
        }

        //compute Fines
        int userID = trans.getUserID();
        User u = getUser(userID);
        Book b = getBook(bookID);
        double fine = computeFine(trans);
        //System.out.println(fi)
        u.setBalance(u.getBalance() + fine);
        users.update(u);
        trans.setStatus(false);
        transactions.update(trans);
        if (fine == 0)
            msgLog.add("Thanks for returning " + b.getName() + "!");
        else {
            msgLog.add("You returned " + b.getName() + " " + fine + " days late!");
            msgLog.add("Your outstanding balance is $" + u.getBalance());
        }
        return true;

    }

    public void collectFine(User u) {
        if (u.getBalance() <= 0) {
            msgLog.add("User has no outstanding balances..");
        } else {
            msgLog.add("User dues collected....");
            u.setBalance(0);
            users.update(u);
        }

    }

    private double computeFine(Transaction t) {

        Period interval = Period.between(LocalDate.now() ,t.getIssueDate().toLocalDate());
        int ms = Math.abs(interval.getDays());
        if (ms<=MAX_LOAN_DAYS) //change in final
            return 0;
        return (ms - MAX_LOAN_DAYS)*1.0;
    }

    public ArrayList<Book> searchBook(String name) {
        return books.getByQuery(name);
    }

    public ArrayList<User> searchUser(String name) {
        return users.getByQuery(name);
    }

    public void printResults(ArrayList<Book> results) {
        if (results.size() == 0)
            msgLog.add("No books matched the search criteria");
        for (Book b : results) {
            msgLog.add("Book Id : " + b.getID() + " Book Name : " + b.getName());

        }

    }


    public Book getBook(int bookID) {

        for (Book temp : books.getBook(bookID)) {
            if (temp.getID() == bookID) {
                return temp;
            }
        }
        return null;
    }

    public User getUser(int userID) {
        for (User temp : users.getUser(userID)) {
            if (temp.getID() == userID) {
                return temp;
            }
        }
        return null;
    }

    public String getLog(){
        StringBuilder sb= new StringBuilder();

        for(String msg :msgLog){
            sb.append(msg+"\n");
        }
        msgLog.clear();
        return sb.toString();
    }

}
