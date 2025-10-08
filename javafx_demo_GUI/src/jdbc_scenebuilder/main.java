package jdbc_scenebuilder;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

//javac --module-path lib --add-modules javafx.controls,javafx.fxml -d bin src/jdbc_scenebuilder/*.java
//then copy resources folder to bin if changed in SceneBuilder
//java "-Djava.library.path=lib/bin" --module-path lib --add-modules javafx.controls,javafx.fxml -cp "bin;lib/*" jdbc_scenebuilder.ViewApp
public class main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {

        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "resources/cashier-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Boba Tea POS System");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(main.class.getResource(fxml));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
    }


    public static void main(String[] args) {
        launch();
    }
}
