import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FocusCompanionApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //just for the record, i have literally 0 experience with javafx, half of this is me just taking a "hello world" program and using it

        primaryStage.setTitle("Focus Companion App by Luke Lavin");
        StackPane root = new StackPane();

        Text grades = new Text(FocusCompanion.getGrades());
        grades.setFont(new Font("Times New Roman", 20));

        root.getChildren().add(grades);
        primaryStage.setScene(new Scene(root, 600, 250));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
