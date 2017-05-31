package tetris;

import javafx.scene.Group;

import java.util.ArrayList;

/**
 * Created by amnich on 06.01.17.
 */
public class Tetromino extends Group {

    //kształt
    private TetrominoShape shape;

    //pozycja
    private  int x;
    private  int y;

    //długość boku kwadratu
    private double squareSize;

    public Tetromino(int x, int y,double squareSize, TetrominoShape shape){

        this.x = x;
        this.y = y;
        this.squareSize = squareSize;
        this.shape = shape;
        this.getChildren().add(shape.getVisibleTetromino(squareSize));
    }

    //metoda czy można się ruszyć o wektor()
    public boolean moveable (BottomBlock b, int moveByX, int moveByY){

        return shape.moveable(b,moveByX+x,moveByY+y);
    }

    public void moveBy(int moveByX, int moveByY){

        x+=moveByX;
        y+=moveByY;
    }

    public boolean rotateable(BottomBlock b){

        return shape.rotateable(b,x,y);
    }

    public void rotate(){

        shape.rotateRight();
    }

    public ArrayList<Integer> joinAndGetRowsToDelete (BottomBlock b){

        return shape.joinAndGetRowsToDelete(b, x, y);

    }

}
