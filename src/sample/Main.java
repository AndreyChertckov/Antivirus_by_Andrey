package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Main extends Application {
    static String USER;
    static final String PATH= "C:\\Program Files\\Antivirus_by_Andrey\\";
    static Stage pr;
    static Scene scene;
    static Report report;
    static ScannerFile scannerFile = new ScannerFile();
    static ObservableList<String> UsersList = FXCollections.observableArrayList("sdsa");
    @Override
    public void start(Stage primaryStage) throws Exception{
        listOfUsers();
        scene = new Scene(FXMLLoader.load(getClass().getResource("/res/main.fxml")),450,260);
        pr = primaryStage;
        if(new File(PATH+"USERNAME.txt").exists()) {
            USER = FileWorker.readString(new File(PATH + "USERNAME.txt"));

            if (FileWorker.readString(new File(PATH + "USERNAME.txt")) != "") {

                USER = FileWorker.readString(new File(PATH + "USERNAME.txt"));
            }
        }else{

            USER = "";
            new File(PATH+"USERNAME.txt").createNewFile();
        }

        if(USER=="") {

            Parent root = FXMLLoader.load(getClass().getResource("/res/sample.fxml"));
            primaryStage.setTitle("Hello User");
            primaryStage.setScene(new Scene(root, 455, 155));
            pr.getScene().getStylesheets().add("res/DarkStyle.css");
            pr.getIcons().add(new Image("res/death_star.png"));
            primaryStage.show();
        }else{
            window();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void window() {
        File dirAntivirus = new File(PATH);
        if(!dirAntivirus.exists()){
            dirAntivirus.mkdirs();
        }
        report = new Report();

        Scanner scanner = new Scanner(report);
        Thread th = new Thread(scanner);
        th.start();
        pr.setScene(scene);
        pr.setTitle("Антивирус");
        pr.getScene().getStylesheets().add("res/DarkStyle.css");
        pr.getIcons().add(new Image("res/death_star.png"));
        pr.show();

    }
    void listOfUsers(){

        File dirUsers = new File("C:\\Users");
        for(File pat:dirUsers.listFiles()){
            UsersList.add(pat.getName());
        }
    }

}
