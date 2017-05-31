package communicator;

import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import tetris.Board;
import tetris.GameSize;

/**
 * Created by amnich on 06.01.17.
 */
public class Game extends VBox {


    private Board board;
    private HBox hbox;
    private GameSize gameSize;
    private Counter points;
    private boolean stopped;
    private boolean ended;
    private boolean firstStart;
    private Button start;
    private Button stop;


    public Game(Stage stage, GameSize gameSize) {

        board = new Board(gameSize);
        hbox = new HBox();
        this.gameSize = gameSize;
        points = new Counter();
        stopped = true;
        ended = false;
        firstStart = true;


        this.getChildren().add(board);
        this.getChildren().add(hbox);

        start = new Button();
        start.setText("Start");
        start.setMinSize(160,100);
        start.setTextAlignment(TextAlignment.CENTER);
        start.setFont(Font.font(18));
        start.setTextFill(Color.DARKBLUE);
        start.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if(stopped && !ended){

                    if(firstStart){

                        firstStart=false;
                        board.startNew();
                    }
                    else

                        board.start();

                    stopped=false;
                }
                event.consume();
            }
        });


        stop = new Button();
        stop.setText("Stop");
        stop.setMinSize(160,100);
        stop.setTextAlignment(TextAlignment.CENTER);
        stop.setFont(Font.font(18));
        stop.setTextFill(Color.DARKBLUE);

        stop.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if(!stopped && !ended){

                    stopped = true;
                    board.stop();
                }
                event.consume();
            }
        });

        hbox.getChildren().add(start);
        this.getChildren().add(0,points);
        hbox.getChildren().add(stop);

        hbox.setAlignment(Pos.TOP_CENTER);
        start.setAlignment(Pos.CENTER);
        stop.setAlignment(Pos.CENTER);

        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {

                if (ke.getCode() == KeyCode.DOWN) {

                    if (!stopped && board.state!= Board.State.Stop) {
                        board.state = Board.State.MoveDownFast;
                    }
                }

                if (ke.getCode() == KeyCode.RIGHT) {

                    if (!stopped) {
                        board.moveRight();
                    }
                }


                if (ke.getCode() == KeyCode.LEFT) {

                    if (!stopped) {
                        board.moveLeft();
                    }
                }

                if (ke.getCode() == KeyCode.UP) {

                    if (!stopped) {
                        board.rotate();
                    }
                }
                ke.consume();
            }
        });

        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.DOWN) {

                    if (board.state != Board.State.Stop)
                        board.state = Board.State.MoveDown;

                    event.consume();

                }
            }
        });

        this.addEventHandler(GameEvent.BOTTOM_MEET, new EventHandler<GameEvent>(){
            @Override
            public void handle(GameEvent ge){
                points.add(ge.getPoints());
                ge.consume();
            }
        });

        this.addEventHandler(GameEvent.GAME_END, new EventHandler<GameEvent>(){

            @Override
            public void handle(GameEvent ge){
                ended = true;
                endGame(stage);


            }
        } );

    }

    private void endGame(Stage stage){
        GameOver gameOver = new GameOver(stage, this ,points.get(),gameSize);
    }


    public static class GameEvent extends Event {

        private int points;

        public int getPoints(){
            return points;
        }


        public static final EventType<GameEvent> GAME_END =
                new EventType<>(Event.ANY, "GAME_END");

        public static final EventType<GameEvent> BOTTOM_MEET =
                new EventType<>(Event.ANY, "BOTTOM_MEET");

        public GameEvent(Object source, EventTarget target, EventType<GameEvent> ge,int points){
            super(source,target,ge);
            this.points=points;
        }


    }

}
