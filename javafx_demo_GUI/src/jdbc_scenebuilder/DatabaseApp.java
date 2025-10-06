package jdbc_scenebuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

//cmds in terminal to compile and run:
//cd C:\Users\monty\CSCE\CSCE_331\project2_personal\javafx_demo_GUI
//javac --module-path lib --add-modules javafx.controls,javafx.fxml -cp "lib/*" -d bin src/jdbc_scenebuilder/*.java
//java "-Djava.library.path=lib/bin" --module-path lib --add-modules javafx.controls,javafx.fxml -cp "bin;lib/*" jdbc_scenebuilder.DatabaseApp

//Gluon Scene Builder for easy UI creation
//search up Scene Builder in search bar (bot left corner)
//open project -> navigate to JavaFXProject -> src -> app -> MainView

public class DatabaseApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("database-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("AWS PostgreSQL Query Example");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
