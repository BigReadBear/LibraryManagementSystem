package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import model.Book;
import model.Library;
import model.Transaction;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

public class IssueBook implements Initializable {

    private Library library;

    @FXML
    private Button issueBookButton;

    @FXML
    private TextField userID;

    @FXML
    private TextField bookID;

    @FXML
    private TextField bookName;

    @FXML
    private TextField userName;

    @FXML
    private ListView<Book> booksView;

    @FXML
    private ListView<User> usersView;

    @FXML
    private ObservableList<Book> bookList;

    @FXML
    private ObservableList<User> userList;

    @FXML
    private Label textLabel1;

    @FXML

    // issue book to user if it is allowed. Check therefore if
    // the book is available, that is not already borrowed. Then
    // check if the user not has an outstanding fine and then check
    // if the user not already has the maximum amound of books allowed
    private void issueBook(ActionEvent actionEvent) {

        int bID = Integer.parseInt(bookID.getText());
        int uID = Integer.parseInt(userID.getText());
        textLabel1.setText("");
        if (library.issueBook(uID, bID)) {
            textLabel1.setTextFill(Color.LIGHTGREEN);
            textLabel1.setTextAlignment(TextAlignment.LEFT);
        } else {
            textLabel1.setTextFill(Color.DARKRED);
            textLabel1.setTextAlignment(TextAlignment.CENTER);
        }
        textLabel1.setText(library.getLog());
    }

    @FXML

    // find a user by adding characters of the name (partially) in the search field
    // the listview shows the users with name matching the characters (partially)
    private void searchUser(KeyEvent keyEvent) {
        String input = userName.getText();
        ObservableList<User> userListFiltered = observableArrayList(library.searchUser(input));
        usersView.setItems(userListFiltered);
    }

    @FXML

    // find a book by adding characters of the title (partially) in the search field
    // the listview shows the books with title matching the characters (partially)
    private void searchBook(KeyEvent keyEvent) {
        String input = bookName.getText();
        ObservableList<Book> bookListFiltered = observableArrayList(library.searchBook(input));
        booksView.setItems(bookListFiltered);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML

    // if a book is selected in the textview then put the selected bookID in the
    // the bookID textfield
    private void bookSelected(MouseEvent mouseEvent) {
        textLabel1.setText("");
        bookID.setText(Integer.toString(((Book) booksView.getSelectionModel().getSelectedItem()).getID()));
    }

    @FXML

    // if a user is selected in the textview then put the selected userID in the
    // the userID textfield
    private void userSelected(MouseEvent mouseEvent) {
        textLabel1.setText("");
        userID.setText(Integer.toString(((User) usersView.getSelectionModel().getSelectedItem()).getID()));
    }

    // is called by the owner of this stage to pass the model and
    // initialize the user and book textviews with data currently
    // present in the library (model)
    public void initData(Library library) {
        this.library = library;
        bookList = observableArrayList(library.books.getAll());
        booksView.setItems(bookList);
        userList = observableArrayList(library.users.getAll());
        usersView.setItems(userList);

        // prevent writing in the bookID and userID textfields
        // to prevent input of non existing book or users IDs
        bookID.setEditable(false);
        userID.setEditable(false);

        // set the focus to bookName field
        bookName.requestFocus();
    }
}
