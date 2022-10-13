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
import model.Book;
import model.Library;
import model.Transaction;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static javafx.collections.FXCollections.observableArrayList;

public class ReturnBook implements Initializable {

    private Library library;

    @FXML
    private Button returnButton;

    @FXML
    private TextField bookName;

    @FXML
    private ListView<Book> bookView;

    @FXML
    private TextField bookID;

    @FXML
    private Label warningText;
    @FXML
    private ObservableList<Book> bookList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void searchBook(KeyEvent keyEvent) {
        warningText.setText("");
        String input = bookName.getText();
        ObservableList<Book> bookListFiltered = observableArrayList(library.searchBook(input));
        bookView.setItems(bookListFiltered);
    }

    public void selectBook(MouseEvent mouseEvent) {
        warningText.setText("");
        bookID.setText(Integer.toString(((Book)bookView.getSelectionModel().getSelectedItem()).getID()));
    }

    public void returnBook(ActionEvent actionEvent) {
        warningText.setText("");
        Book book = (Book)bookView.getSelectionModel().getSelectedItem();
        if (library.returnBook(book.getID())) {
            warningText.setTextFill(Color.LIGHTGREEN);
        } else {
            warningText.setTextFill(Color.DARKRED);
        }
        warningText.setText(library.getLog());
    }

    public void initData(Library library) {
        this.library = library;
        bookList = observableArrayList(library.books.getAll());
        bookView.setItems(bookList);
    }
}
