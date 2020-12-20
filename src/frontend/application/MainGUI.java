package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
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
