import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;


public class FocusCompanionApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        //first experience with javafx, definitely not nice looking

        FocusCompanion companion = new FocusCompanion();

        Scene scene = new Scene(new Group(), 1100, 400);

        TextArea textArea = new TextArea("(Your grades here)");
        textArea.setEditable(true);

        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(20);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(textArea, 1, 0);
        grid.add(new ImageView(new Image("example.png")), 0, 0);
        grid.setAlignment(Pos.TOP_LEFT);

        Button button = new Button("Submit");

        button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                companion.setText(textArea.getText());
                try{
                    if(grid.getChildren().size() >= 4)
                        grid.getChildren().remove(grid.getChildren().get(3));
                    Text grades = new Text(companion.getGrades());
                    grades.setFont(new Font("Calibri", 20));
                    grid.add(grades, 2, 0);
                } catch (Exception ex){}
            }
        });
        grid.add(button, 1, 1);

        Group root = (Group) scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Focus App 1.1");
        stage.setResizable(false);

        System.out.println(textArea.getText());
        System.out.println(textArea.getText().indexOf('\n'));
    }

    public static void main(String[] args) {
        launch(args);
    }
}