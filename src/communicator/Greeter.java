package communicator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tetris.GameSize;


/**
 * Created by amnich on 18.01.17.
 */

public class Greeter extends VBox {

    public  int greeter_width = 300;
    public  int greeter_height = 200;

    private Text tetris;
    private Text question;
    private FlowPane buttons;
    private Button small;
    private Button medium;
    private  Button large;
    private Game game;

    public Greeter(Stage stage){

        buttons = new FlowPane();
        tetris = new Text("HELLO");
        tetris.setFont(Font.font(25));
        tetris.setFill(Color.DARKBLUE);
        tetris.setTranslateX(100);
        tetris.setTranslateY(20);

        question = new Text("Choose board size:");
        question.setFont(Font.font(16));
        question.setFill(Color.DARKBLUE);
        question.setTranslateX(20);
        question.setTranslateY(40);

        this.getChildren().add(tetris);
        this.getChildren().add(question);


        small = new Button("SMALL");
        small.setMinHeight(60);
        small.setTextFill(Color.DARKBLUE);

        small.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                game = new Game(stage, GameSize.small);
                startGame(stage);
                event.consume();
            }
        });

        medium = new Button("MEDIUM");
        medium.setMinHeight(60);
        medium.setTextFill(Color.DARKBLUE);

        medium.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                game = new Game(stage, GameSize.medium);
                startGame(stage);
                event.consume();
            }
        });

        large = new Button("LARGE");
        large.setMinHeight(60);
        large.setTextFill(Color.DARKBLUE);

        large.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                event.consume();
                game = new Game(stage, GameSize.large);
                startGame(stage);
            }
        });

        buttons.getChildren().add(small);
        buttons.getChildren().add(medium);
        buttons.getChildren().add(large);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        buttons.setTranslateY(50);
        this.getChildren().add(buttons);

    }

    private void startGame(Stage stage){
        Scene scene = new Scene(game,400, 620);
        stage.setScene(scene);
        stage.show();
        game.requestFocus();
    }
}
