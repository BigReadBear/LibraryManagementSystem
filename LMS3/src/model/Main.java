package model;

import controller.MainMenu;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;

public class Main extends Application {

    Library library;
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Book b1 = new Book("Programming with Java", "Daniel Lang", "Pearson", "Education", "123456", 2020);
//        Book b2 = new Book("Modern Java ", "Raoul-Gabriel Urma", "Manning", "Education", "1234567", 2020);
//        Book b3 = new Book("The Java Module System", "Nicolai Parlog", "Manning", "Education", "123458", 2020);
//        Book b4 = new Book("Core Java Volume I", "Cay S. Horstmann", "Pearson", "Education", "1234569", 2019);
//        Book b5 = new Book("Core Java Volume II", "Cay S. Horstmann", "Pearson", "Education", "12344444", 2020);
//        User u1 = new User("Piet Jansen", "p.jansen@quicknet.nl", "Schouw 12, 1774 PK, Wieringerwerf", LocalDate.of(1980,4, 20), true);
//        User u2 = new User("Piet Zoutman", "p.jansen@quicknet.nl", "Slenk 32, 1772 PM, Wieringerwerf", LocalDate.of(1990,5, 20), true);
//        User u3 = new User("Jarno Pomstra", "j.pomstra@quicknet.nl", "Zicht 16, 1771 PS, Wieringerwerf", LocalDate.of(2000,9, 15), true);
//        User u4 = new User("Klaas Jansen", "p.jansen@quicknet.nl", "Tjalk 7, 1773 PK, Wieringerwerf", LocalDate.of(1980,04, 20), true);
//        User u5 = new User("Harry Pieterse", "h.pieterse@quicknet.nl", "Sloep 19, 1770 PR, Wieringerwerf", LocalDate.of(1984,11, 14), true);


          library = new Library();

//        library.users.insert(u1);
//        library.users.insert(u2);
//        library.users.insert(u3);
//        library.users.insert(u4);
//        library.users.insert(u5);
//        library.books.insert(b1);
//        library.books.insert(b2);
//        library.books.insert(b3);
//        library.books.insert(b4);
//        library.books.insert(b5);

        Main.primaryStage = primaryStage;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/mainMenu.fxml"));

        Parent root = loader.load();

        MainMenu mainController = loader.getController();
        mainController.initData(library);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
