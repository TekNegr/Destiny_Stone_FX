import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.text.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class App extends Application {
    public Pane pane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // variables;
        pane = new Pane();
        GridPane topPane = new GridPane();
        BackgroundFill grayBG = new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(1), new Insets(0.0, 0.0, 0.0, 0.0));
        BorderPane main = new BorderPane();
        Scene scene = new Scene(main);
        Label label = new Label("Titre");

        // config window;
        pane.setPrefHeight(500);
        pane.setPrefWidth(1000);
        pane.setBackground(new Background(grayBG));
        topPane.add(label, 0, 0);
        main.setTop(topPane);
        primaryStage.setTitle("game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // FXMLLoader Loader = new
        // FXMLLoader(getClass().getResource("/ressources/FX/Menu_scene.fxml"));
        // Parent root = Loader.load();
        // Menu_Controller controller = Loader.getController();
        // controller.setScene(primaryStage);
        // primaryStage.setTitle("Destiny's showdown");
        // primaryStage.setScene(new Scene(root, 600, 600));
        // primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}