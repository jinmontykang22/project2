package jdbc_scenebuilder;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ViewApp extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {

        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "manager-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("AWS PostgreSQL Query Example");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(ViewApp.class.getResource(fxml));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
    }


    public static void main(String[] args) {
        launch();
    }
}
