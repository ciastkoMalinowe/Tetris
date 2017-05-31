package communicator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tetris.GameSize;


public class Main extends Application {

    public static int app_width = 400;
    public static int app_height = 620;
    Greeter greeter;
    Game game;

    @Override
    public void start(Stage primaryStage) {

        greeter = new Greeter(primaryStage);

        Scene scene = new Scene(greeter,greeter.greeter_width, greeter.greeter_height);
        primaryStage.setTitle("Tetris");
        primaryStage.setScene(scene);

        primaryStage.show();
        greeter.requestFocus();

    }


    private void startGame(Stage stage, GameSize gameSize){

        game = new Game(stage, gameSize);
        Scene scene = new Scene(game,400, 620);
        scene.setFill(Color.AQUA);
        stage.setScene(scene);
        game.requestFocus();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
