package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Library;
import model.User;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class NewUser implements Initializable {

    private Library library;

    @FXML
    private TextField userName;

    @FXML
    private TextField emailUser;

    @FXML
    private TextArea addressUser;

    @FXML
    private RadioButton isStudent;

    @FXML
    private RadioButton isFaculty;

    @FXML
    private DatePicker dateOfBirthUser;

    @FXML
    private Button registerUser;

    @FXML

    // this method adds a new user to the library
    // name and address are mandatory
    private void addNewUser(ActionEvent actionEvent) {
        String name = userName.getText();
        String email = emailUser.getText();
        String address = addressUser.getText();
        boolean student = isStudent.isSelected();
        LocalDate dateOfBirth = dateOfBirthUser.getValue();
        if (!name.isEmpty() && !address.isEmpty())
        {
            library.addUser(new User(name, email, address, dateOfBirth, student));
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setHeaderText("");
            dialog.setTitle("Registering Successful");
            dialog.setContentText(library.getLog());
            dialog.showAndWait();
            Stage stage = (Stage)registerUser.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // to prevent that both radiobutton can be selected
        // the buttons are put in a togglegroup. Now only one
        // can be selected
        ToggleGroup tg = new ToggleGroup();
        isStudent.setToggleGroup(tg);
        isFaculty.setToggleGroup(tg);
    }

    // is called by the owner of this stage to pass the model
    public void initData(Library library) {
        this.library = library;
    }
}
