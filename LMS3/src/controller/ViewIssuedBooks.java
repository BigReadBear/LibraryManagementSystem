package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;
import model.Library;
import model.Transaction;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class ViewIssuedBooks implements Initializable {

    public TableColumn<Transaction, Integer> bookID;
    public TableColumn<Transaction, Integer> userID;
    public TableColumn<Transaction, LocalDateTime> issueDate;
    @FXML
    private TableView<Transaction> bookTable;

    @Override

    // binds the table columns to the members of class Transaction
    // to bind a date member to the table cell the rendering of the
    // cell (updateItem is overridden. A SimpleDateFormat is used
    // to write the dat in the format yyyy-MM-dd
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // bind the table columns to the data of Transaction
        bookID.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("bookID"));
        userID.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("userID"));
        issueDate.setCellValueFactory(new PropertyValueFactory<Transaction, LocalDateTime>("issueDate"));

        // set the date format to yyyy-mm-dd if writing a LocalDateTime in a cell (thx to Stack Overflow)
        issueDate.setCellFactory(column -> {
            return new TableCell<Transaction, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        this.setText(item.toLocalDate().toString());
                    }
                }
            };
        });
    }

    // this methode is called from the parent and initializes the tableview with the
    // current transactions available in library. The members of Transaction is
    // written in the columns as defined in the method initialize in with each row
    // is a transaction with isStatus is true (indicating an outstanding book)
    public void initData(Library library) {
        ObservableList<Transaction> row = observableArrayList();

        // look for issued books in the transaction list
        for (Transaction t : library.transactions.getAll()) {
            if (t.isStatus()) {
                row.add(t);
            }
        }

        bookTable.setItems(row);

        // sort the table in ascending order of bookID
        bookTable.getSortOrder().add(bookID);
        bookID.setSortable(true);
    }
}
