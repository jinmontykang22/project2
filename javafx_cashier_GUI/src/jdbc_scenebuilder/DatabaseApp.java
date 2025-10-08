package jdbc_scenebuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

//cmds in terminal to compile and run:
//cd C:\Users\monty\CSCE\CSCE_331\project2_personal\javafx_cashier_GUI
//javac --module-path lib --add-modules javafx.controls,javafx.fxml -cp "lib/*" -d bin src/jdbc_scenebuilder/*.java
//java "-Djava.library.path=lib/bin" --module-path lib --add-modules javafx.controls,javafx.fxml -cp "bin;lib/*" jdbc_scenebuilder.DatabaseApp

//NOTE: make sure to copy the database-view.fxml file into the bin/jdbc_scenebuilder folder before trying to run (everytime you update it in SceneBuilder)
//copy src\jdbc_scenebuilder\database-view.fxml bin\jdbc_scenebuilder\database-view.fxml
//may also need to clear/delete the bin folder and recompile

//Gluon Scene Builder for easy UI creation
//search up Scene Builder in search bar (bot left corner)
//open project -> navigate to JavaFXProject -> src -> jdbc_scenebuilder -> database-view.fxml

public class DatabaseApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/jdbc_scenebuilder/database-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("AWS PostgreSQL Query Example");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
