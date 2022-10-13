package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Library;
import model.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;


public class MainMenu implements Initializable {

    private Library library = new Library();

    @FXML
    private Button viewBooksButton;

    @FXML
    private Button returnButton;

    @FXML
    private Button userDetail;

    @FXML
    public Button issueBookButton;

    @FXML
    private Button newUser;

    @FXML
    private Button newBook;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // this method serves as a general way to launch the views
    // from the main menu. It loads the fxml file and returns
    // the to the view connected controller
    public Object loadScreen(String title, String url) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/" + url));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.primaryStage);
        stage.setScene(scene);
        stage.show();
        return loader.getController();
    }

    // launch the addNewUser view and call the method initData of
    // its connected controller to pass a reference to the library data (model)
    public void addNewUser(ActionEvent event) throws IOException {
        NewUser controller = (NewUser) loadScreen("Add New User", "newUser.fxml");
        controller.initData(library);
    }

    // launch the addNewBook view and call the method initData of
    // its connected controller to pass a reference to the library data (model)
    public void addNewBook(ActionEvent event) throws IOException {
        NewBook controller = (NewBook) loadScreen("Add New Book", "newBook.fxml");
        controller.initData(library);
    }

    // launch the showIssueBook view and call the method initData of
    // its connected controller to pass a reference to the library data (model)
    public void showIssueBook(ActionEvent event) throws IOException {
        IssueBook controller = (IssueBook) loadScreen("Issue Book", "issueBook.fxml");
        controller.initData(library);
    }

    // launch the showUserDetail view and call the method initData of
    // its connected controller to pass a reference to the library data (model)
    public void showUserDetail(ActionEvent event) throws IOException {
        UserView controller = (UserView) loadScreen("User Details", "userView.fxml");
        controller.initData(library);
    }

    // launch the returnBook view and call the method initData of
    // its connected controller to pass a reference to the library data (model)
    public void returnBook(ActionEvent event) throws IOException {
        ReturnBook controller = (ReturnBook) loadScreen("Return Book", "returnBook.fxml");
        controller.initData(library);
    }

    // launch the viewIssuedBooks view and call the method initData of
    // its connected controller to pass a reference to the library data (model)
    public void viewIssuedBooks (ActionEvent event) throws IOException {
        ViewIssuedBooks controller = (ViewIssuedBooks) loadScreen("View Issued Books", "viewIssuedBooks.fxml");
        controller.initData(library);
    }

    // this method is called by the Main method, passing a reference to
    //  library (model)
    public void initData(Library library) {
        this.library = library;
    }
}
