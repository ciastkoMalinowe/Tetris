package communicator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import tetris.GameSize;
import tetris.Player;
import tetris.ScoreBoard;

import static java.lang.String.valueOf;

/**
 * Created by amnich on 18.01.17.
 */
public class GameOver extends VBox {

    private ScoreBoard scoreBoard;

    private int gameOver_width=400;
    private int gameOver_height=620;
    private Text congrats;
    private Text info;
    private Text points;
    private TextField name;

    public GameOver(Stage stage, Game game, int score, GameSize gameSize){

        this.setWidth(gameOver_width);
        this.setHeight(gameOver_height);
        congrats = new Text("Congratulations!");
        congrats.setFont(Font.font(28));
        congrats.setFill(Color.DARKBLUE);
        congrats.setTextAlignment(TextAlignment.CENTER);
        congrats.setTranslateY(150);
        congrats.setTranslateX(100);
        this.getChildren().add(congrats);

        points = new Text(valueOf(score));
        points.setFont(Font.font(35));
        points.setFill(Color.DARKBLUE);
        points.setTranslateY(160);
        points.setTranslateX(185);
        this.getChildren().add(points);

        info = new Text("Please, type your name");
        info.setFont(Font.font(18));
        info.setFill(Color.DARKBLUE);
        info.setTranslateY(180);
        this.getChildren().add(info);

        name = new TextField("Your Name");
        name.setMaxWidth(150);
        name.setMinHeight(75);
        name.setAlignment(Pos.CENTER);
        name.setTranslateY(200);
        name.setTranslateX(120);
        this.getChildren().add(name);

        scoreBoard=new ScoreBoard();

        this.setOnKeyPressed(new EventHandler<KeyEvent>(){

            public void handle(KeyEvent e){

                if(e.getCode() == KeyCode.ENTER) {

                    Player player = new Player(name.getText(), gameSize, score);
                    scoreBoard.add(player);
                    int place = scoreBoard.getPosition(player);
                    if (place >= scoreBoard.ListSize) {
                        next(false, 0, stage);
                    } else
                        next(true, place, stage);
                    e.consume();
                }
            }
        });
        Group display = new Group();
        game.setOpacity(0.5);
        display.getChildren().add(game);
        display.getChildren().add(this);
        stage.setScene(new Scene(display,gameOver_width,gameOver_height));
        this.requestFocus();
    }

    private void next(boolean joinedScoreBoard, int place, Stage stage){

        this.getChildren().removeAll(name,info);
        if(joinedScoreBoard)
            info.setText("You've joined our scoreboard on " + (place+1) + " place!" );
        else
            info.setText("Probabyly It's not you best resault.");


        Button playAgain = new Button("Play Again");
        playAgain.setTranslateX(160);
        playAgain.setTranslateY(180);
        playAgain.setAlignment(Pos.CENTER);
        playAgain.setTextFill(Color.DARKBLUE);
        playAgain.setMinHeight(50);
        playAgain.setMinWidth(75);

        playAgain.setOnAction(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent e){

                Greeter greeter = new Greeter(stage);
                Scene scene = new  Scene(greeter,greeter.greeter_width,greeter.greeter_height);
                stage.setScene(scene);
                stage.show();
                greeter.requestFocus();
                e.consume();
            }
        });
        Button seeResults = new Button("See Results");
        seeResults.setAlignment(Pos.CENTER);
        seeResults.setTranslateX(160);
        seeResults.setTranslateY(230);
        seeResults.setTextFill(Color.DARKBLUE);
        seeResults.setMinHeight(50);
        seeResults.setMinWidth(75);

        seeResults.setOnAction(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent e){

                showResults(stage);
                e.consume();
            }
        });

        this.getChildren().add(info);
        this.getChildren().add(playAgain);
        this.getChildren().add(seeResults);


    }

    private void showResults(Stage stage){
        ScoreBoardPresentation scoreBoardPresentation = new ScoreBoardPresentation(stage,scoreBoard);
        Scene scene = new Scene(scoreBoardPresentation,300,250);
        scene.setFill(Color.LIGHTBLUE);
        stage.setScene(scene);
        stage.show();
        scoreBoardPresentation.requestFocus();
    }
}
