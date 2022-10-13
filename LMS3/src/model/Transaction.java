package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Transaction {

    private int bookID;
    private int userID;

    // Changed to LocalDateTime because userID, bookID and issueDate is used as key.
    // The resolution of LocalDate without time is too low to prevent identical keys.
    private LocalDateTime issueDate;
    private boolean status; // true : active false : complete


    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public LocalDateTime getIssueDate() { return issueDate; }

    public void setIssueDate(LocalDateTime issueDate) { this.issueDate = issueDate; }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Transaction(int bookID, int userID) {
        this.bookID = bookID;
        this.userID = userID;
        this.issueDate = LocalDateTime.now();
        this.status=true;
    }
}
