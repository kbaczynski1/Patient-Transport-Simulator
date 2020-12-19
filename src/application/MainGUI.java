package application;

import java.util.ArrayList;

//import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // BorderPane root = new BorderPane();
            // Scene scene = new Scene(root,400,400);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            // primaryStage.setScene(scene);

//			StackPane stackPane = new StackPane();
//			Button button = new Button("[=pr");
//			stackPane.getChildren().add(button);

//			Scene scene = new Scene(stackPane, 400, 400);

//			primaryStage.setScene(scene);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/fxml/MainWindow.fxml"));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Symulator Transportu Pacjentów");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
