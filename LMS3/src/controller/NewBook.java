package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Book;
import model.Library;
import model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javafx.collections.FXCollections.*;

public class NewBook {

    private Library library;

    @FXML
    private TextField bookName;

    @FXML
    private TextField author;

    @FXML
    private TextField isbn;

    @FXML
    private TextField publishYear;

    @FXML
    private ListView<String> publisherList;

    @FXML
    private ComboBox<String> genre;

    @FXML
    private Button registerBook;

    @FXML
    private ObservableList<String> genreList;

    @FXML
    private ObservableList<String> publishers;

    @FXML
    public void initialize() {

        // initialize the listview connected to the combobox
        // with predefined genres of books and the listview
        // of publishers with predefined publishers. By using
        // observableArrayList adding items to this list is
        // immediately reflected in the view. There is a
        // dynamic connection between the view and the data
        genreList = observableArrayList();
        genreList.add("Education");
        genreList.add("Adventure");
        genreList.add("Thriller");
        genreList.add("History");
        genre.setItems(genreList);
        genre.getSelectionModel().selectFirst();
        publishers = observableArrayList();
        publishers.add("Pearson");
        publishers.add("Penguin");
        publishers.add("Addison Wesley");
        publishers.add("Manning");
        publishers.add("McGraw-Hill");
        publisherList.setItems(publishers);

    }

    // this method adds a new book to the library
    // name and isbn are mandatory
    public void registerNewBook(ActionEvent actionEvent) {
        String name = bookName.getText();
        String publisher = publisherList.getSelectionModel().getSelectedItem();
        String authorBook = author.getText();
        String isbnBook = isbn.getText();
        String genreBook = (String) genre.getSelectionModel().getSelectedItem();

        // take a default publish year of 1900
        long year = 1900;
        try {
            year = Long.parseLong(publishYear.getText());
        } catch (NumberFormatException n) {
            // if publishYear does not contain a correct number hen
            // take the default year of publishing 1900
        }

        // check if at least the name and isbn is not empty
        if (!name.isEmpty() && !isbnBook.isEmpty()) {
            library.addBook(new Book(name, authorBook, publisher, genreBook, isbnBook, year));
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setHeaderText(""); //haha found it
            dialog.setTitle("Registering Successful");
            dialog.setContentText(library.getLog());
            dialog.showAndWait();
            Stage stage = (Stage) registerBook.getScene().getWindow();
            stage.close();
        }
    }

    // is called by the owner of this stage to pass the model
    public void initData(Library library) {
        this.library = library;
    }
}
