package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Book;
import model.Library;
import model.Transaction;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class UserView implements Initializable {

    private Library library;

    @FXML
    private Label address;

    @FXML
    private Button fineButton;

    @FXML
    private Label birthday;

    @FXML
    private ComboBox<User> userList;

    @FXML
    private ListView<Book> bookList;

    @FXML
    private Label balance;

    @FXML
    private Label userType;

    @FXML
    private Label email;

    @FXML
    private Label userName;

    @FXML
    private ObservableList<User> users;

    @FXML
    private ObservableList<Book> books;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // this method shows details of the selected user
    public void showUserData(ActionEvent actionEvent) {
        fineButton.setVisible(false);
        User user = userList.getSelectionModel().getSelectedItem();
        userName.setText(user.getName());
        email.setText(user.getEmail());
        address.setText(user.getAddress());

        // check if birthday exist before calling toString
        if (user.getDateOfBirth() != null)
            birthday.setText(user.getDateOfBirth().toString());
        else
            birthday.setText("Unknown");
        if (user.getStudent()) {
            userType.setText("Student");
        } else {
            userType.setText("Faculty");
        }
        if (user.getBalance() == 0.0) {
            balance.setTextFill(Color.WHITE);
            balance.setText("$" + Double.toString(user.getBalance()));
        } else {
            balance.setTextFill(Color.DARKRED);
            balance.setText("$" + Double.toString(user.getBalance()));
            fineButton.setVisible(true);
        }

        // create an list of books currently borrowed by the selected user
        // by using an observablelist the data gets a dynamic binding to the
        // listview and changes will be shown immediately
        books = observableArrayList();
        for (Transaction t : library.transactions.getCurrent()) {
            if (t.getUserID() == user.getID()) {
                books.add(library.getBook(t.getBookID()));
            }
        }
        bookList.setItems(books);
    }

    // this methods resets the balance to 0.0
    public void collectFine(ActionEvent actionEvent) {
        //userList.getSelectionModel().getSelectedItem().setBalance(0.0);
        User selectedUser = (User) userList.getSelectionModel().getSelectedItem();
        library.collectFine(selectedUser);
        balance.setTextFill(Color.LIGHTGREEN);
        balance.setText("$" + Double.toString(selectedUser.getBalance()));
        fineButton.setVisible(false);
        library.getLog(); // consume text from log for next message
    }

    // the listview connected to the combobox will be set to
    // an observablelist with users currently registered with
    // the library creating a dynamic binding between the list
    // and the listview.
    public void initData(Library library) {
        this.library = library;
        users = observableArrayList(library.users.getAll());
        userList.setItems(users);
    }
}
