package tetris;

import communicator.Game;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

import static communicator.Game.GameEvent.BOTTOM_MEET;
import static communicator.Game.GameEvent.GAME_END;

/**
 * Created by amnich on 06.01.17.
 */
public class Board extends Group {

    private BottomBlock bottomBlock;
    private TetrominoGenerator tetrominoGenerator;


    private Tetromino currentTetromino;

    private int squareSize;

    private TranslateTransition moveDownTransition;
    private TranslateTransition moveHorizontalTransition;
    private RotateTransition rotateTransition;
    private SequentialTransition bottomBlockTransition;

    private int moveDownTime;
    private int moveFastTime;
    private int rotateTime;
    private int moveHorizontalTime;



    public enum State{
        MoveDown,
        MoveDownFast,
        Stop
    };

    public State state;


    public Board(GameSize gameSize){

        this.squareSize = gameSize.squareSize;

        Rectangle frame = new Rectangle((gameSize.width+4)*squareSize,(gameSize.height+4)*squareSize);
        frame.setTranslateX(0);
        frame.setTranslateY(0);
        frame.setFill(Color.TRANSPARENT);
        this.getChildren().add(frame);

        Rectangle background = new Rectangle(gameSize.width*squareSize,gameSize.height*squareSize);
        background.setTranslateX(2*squareSize);
        background.setTranslateY(2*squareSize);
        background.setStroke(Color.DARKBLUE);
        this.getChildren().add(background);

        bottomBlock = new BottomBlock(gameSize);
        bottomBlock.setTranslateX(2*squareSize);
        bottomBlock.setTranslateY(2*squareSize);
        this.getChildren().add(bottomBlock);

        tetrominoGenerator = new TetrominoGenerator(gameSize);

        moveDownTime=500;
        moveFastTime=100;
        rotateTime=500;
        moveHorizontalTime=100;

        moveDownTransition = new TranslateTransition(Duration.millis(moveDownTime));
        moveDownTransition.setByX(0);
        moveDownTransition.setByY(squareSize);

        moveDownTransition.setOnFinished(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent AE){
                if(state != State.Stop)
                    moveDown();
            }
        });

        moveHorizontalTransition = new TranslateTransition(Duration.millis(moveHorizontalTime));
        moveHorizontalTransition.setByY(0);


        rotateTransition = new RotateTransition(Duration.millis(rotateTime));
        rotateTransition.setByAngle(-90);


        bottomBlockTransition = new SequentialTransition();

        bottomBlockTransition.setOnFinished(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent AE){

                state = State.MoveDown;
                generate();
            }
        });
    }

    private void generate(){

        if(!bottomBlock.isTop()) {
            currentTetromino = tetrominoGenerator.getRandom();
            this.getChildren().add(currentTetromino);

            moveDownTransition.setNode(currentTetromino);
            moveHorizontalTransition.setNode(currentTetromino);
            rotateTransition.setNode(currentTetromino);
            moveDown();
        }
        else{
            state = State.Stop;

            fireEvent(new Game.GameEvent(this,this.getParent(),GAME_END,0));

        }
    }


    private void moveDown(){

        if(currentTetromino.moveable(bottomBlock,0,1)){

            currentTetromino.moveBy(0,1);

            if(state == State.MoveDownFast)
                moveDownTransition.setDuration(Duration.millis(moveFastTime));
            else
                moveDownTransition.setDuration(Duration.millis(moveDownTime));
            moveDownTransition.play();
        }

        //znaczy, że tetromino dotknęło dna
        else{
            state = State.Stop;
            //join zwraca odpowiednią kombinację Transition (w szczególności pustą)
            ArrayList<Integer> rowsToDelete = currentTetromino.joinAndGetRowsToDelete(bottomBlock);
            this.getChildren().remove(currentTetromino);

            bottomBlockTransition = bottomBlock.deleteRows(rowsToDelete);

            if(! bottomBlockTransition.getChildren().isEmpty()) {

                bottomBlockTransition.play();
              ;
                state = State.MoveDown;

                fireEvent(new Game.GameEvent(this,this.getParent(),BOTTOM_MEET,rowsToDelete.size()*10));
                generate();
            }

            else{
                state =State.MoveDown;
                generate();
            }
        }


    }

    private void moveHorizontal(int x){

        if(! (moveHorizontalTransition.getStatus()== Animation.Status.RUNNING)) {

            if (currentTetromino.moveable(bottomBlock, x, 0)) {

                currentTetromino.moveBy(x, 0);
                moveHorizontalTransition.setByX(squareSize*x);
                moveHorizontalTransition.play();
            }
        }
    }

    public void moveRight(){

        moveHorizontal(1);
    }

    public void moveLeft(){

        moveHorizontal(-1);
    }

    public void rotate(){

        if(! (rotateTransition.getStatus()==Animation.Status.RUNNING)) {

            if (currentTetromino.rotateable(bottomBlock)) {

                currentTetromino.rotate();
                rotateTransition.play();
            }
        }

    }
    public void startNew(){
        generate();
    }

    public void stop(){

        if(moveDownTransition.getStatus() == Animation.Status.RUNNING)
            moveDownTransition.pause();

        if(rotateTransition.getStatus() == Animation.Status.RUNNING)
            rotateTransition.pause();

        if(moveHorizontalTransition.getStatus() == Animation.Status.RUNNING)
            moveHorizontalTransition.pause();

        if(bottomBlockTransition.getStatus() == Animation.Status.RUNNING)
            bottomBlockTransition.pause();
    }

    public void start(){

        if(moveDownTransition.getStatus() == Animation.Status.PAUSED)
            moveDownTransition.play();
        else
            moveDown();

        if(rotateTransition.getStatus() == Animation.Status.PAUSED)
            rotateTransition.play();

        if(moveHorizontalTransition.getStatus() == Animation.Status.PAUSED)
            moveHorizontalTransition.play();

        if(bottomBlockTransition.getStatus() == Animation.Status.PAUSED)
            bottomBlockTransition.play();
    }
}
