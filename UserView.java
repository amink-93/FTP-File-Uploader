import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class UserView extends Application {

    private Button login_btn;
    private Label password_lbl;
    private Label username_lbl;
    private TextField username_txt;
    private PasswordField password_txt;
    private Label serverAddress_lbl;
    private TextField serverAddress_txt;
    private TextField portNum_txt;


    private User user;
    private MyServer server;
    private Stage window;
    private FileChooser fileChooser;

    private Controller controller;
    private Stage primaryStage;


    public static void main(String[] args){
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;

        showLoginScene();
    }

    private void showLoginScene(){

        setWindow(primaryStage);
        window.setResizable(false);
        getWindow().setTitle("FTP File Transfer");

        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        login_btn = new Button("Login");
        username_lbl = new Label("Username");
        username_txt = new TextField();
        password_txt = new PasswordField();
        serverAddress_lbl = new Label("Server Address");

        setServerAddress_txt(new TextField());
        setPortNum_txt(new TextField());
        getPortNum_txt().setPromptText("Port");
        getPortNum_txt().setPrefWidth(60);

        login_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!getServerAddress_txt().getText().trim().isEmpty()){
                    setUser(new User(username_txt.getText().trim(),password_txt.getText().trim(), getServerAddress_txt().getText().trim()));
                    setServer(new MyServer(getUser()));
                    setController(new Controller(getServer(), getUser()));
                    if(getController().login()){
                        showFileScene();
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Error login failed");
                        alert.showAndWait();
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please provide the server address!");
                    alert.showAndWait();
                }

            }
        });


        grid.add(serverAddress_lbl, 0, 1);
        grid.add(getServerAddress_txt(),1,1);
        grid.add(getPortNum_txt(),2,1);
        grid.add(username_lbl,0,2);
        grid.add(username_txt,1,2);

        GridPane.setHalignment(login_btn, HPos.CENTER);
        grid.add(login_btn,1,4);
        password_lbl = new Label("Password:");
        grid.add(password_lbl,0,3);
        grid.add(password_txt,1,3);

        Scene loginScene = new Scene(grid,400,275);


        getWindow().setScene(loginScene);
        getWindow().show();
    }

    private void showFileScene(){


        TextField filePath = new TextField();
        filePath.setPromptText("File Name");
        filePath.setEditable(false);

        Button file_btn = new Button("Open file");
        Button upload_btn = new Button("Upload");
        Button logout_btn = new Button("Logout");


        logout_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                if(controller.logout()){
                    alert.setContentText("Logout Successful");
                    alert.showAndWait();
                    showLoginScene();
                }
                else{
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("Logout Unsuccessful");
                }
            }
        });

        file_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File file = fileChooser.showOpenDialog(window);
                if(file != null){

                        filePath.setText(file.getName());
                        user.setFile(file);


                }
            }
        });

        upload_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(user.getFile() != null){
                    File temp = user.getFile();
                    if(controller.uploadFile(temp.getAbsolutePath(),temp.getName(),"/")){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("File uploaded successfully");
                        alert.showAndWait();
                        filePath.setText("File Name");
                    }
                }
            }
        });


        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));


        GridPane.setConstraints(filePath,1,0,1,1);
        GridPane.setConstraints(file_btn,2,0,1,1);
        GridPane.setConstraints(upload_btn,1,2,1,1,HPos.CENTER,VPos.CENTER);
        GridPane.setConstraints(logout_btn,1,3,1,1,HPos.CENTER,VPos.CENTER);

        grid.getChildren().addAll(filePath,file_btn,upload_btn,logout_btn);
        grid.requestFocus();

        Scene fileScene = new Scene(grid,400,275);

        window.setScene(fileScene);

        window.show();
    }


    private TextField getServerAddress_txt() {
        return serverAddress_txt;
    }

    private void setServerAddress_txt(TextField serverAddress_txt) {
        this.serverAddress_txt = serverAddress_txt;
    }

    private TextField getPortNum_txt() {
        return portNum_txt;
    }

    private void setPortNum_txt(TextField portNum_txt) {
        this.portNum_txt = portNum_txt;
    }

    private User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    private MyServer getServer() {
        return server;
    }

    private void setServer(MyServer server) {
        this.server = server;
    }

    private Stage getWindow() {
        return window;
    }

    private void setWindow(Stage window) {
        this.window = window;
    }

    private Controller getController() {
        return controller;
    }

    private void setController(Controller controller) {
        this.controller = controller;
    }

}
