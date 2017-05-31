package tetris;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by amnich on 06.01.17.
 */
public enum TetrominoShape {
    //tworząca kształt Tetromino

    T(0),
    J(1),
    L(2),
    S(3),
    Z(4),
    I(5),
    Dot(6);

    private int[] x;
    private int[] y;

    private Color color;

    private int[][] xTemplate = {
            { -1, 0, 0, 1 },
            { -1, 0, 0, 0 },
            {  0, 0, 0, 1 },
            { -1,-1, 0, 0 },
            {  0, 0, 1, 1 },
            {  0, 0, 0, 0 },
            { -1, 0,-1, 0 }
    };
    private int[][] yTemplate = {
            {  0, 0,-1, 0 },
            { -1,-1, 0, 1 },
            { -1, 0, 1,-1 },
            {  0, 1, 0,-1 },
            {  0,-1, 0, 1 },
            {  1, 0,-1,-2 },
            {  1, 1, 0, 0 }
    };
    private Color[] colorTemplate = {
            Color.BLUE,
            Color.GREEN,
            Color.PURPLE,
            Color.YELLOW,
            Color.LIME,
            Color.PLUM,
            Color.AQUA
    };

    private TetrominoShape(int nr){
        x = xTemplate[nr];
        y = yTemplate[nr];
        color = colorTemplate[nr];
    }

    //błędne, ale nieużywane
    public void rotateLeft(){

        for(int i=0;i<4;i++){

            int xTmp = x[i];
            x[i] = -1 * y[i];
            y[i] = xTmp;
        }
    };


    //współrzędne kwadratu to współrzędne lewego górnego rogu stąd rotując w takim układzie trzeba jakby rotować róg na prawo (czyli x+1) i podmienić
    public void rotateRight(){

        for(int i=0;i<4;i++){

            int rotX = x[i]+1;
            int rotY = y[i];
            y[i] = -1 * rotX;
            x[i] = rotY;
        }
    };

    public Group getVisibleTetromino(double squareSize){

        Group visibleTetromino = new Group();

        //tworzę kwadrat 4 na 4 z przezroczystych kwadratów (musi być, żeby obrót się zgadzał)
        Rectangle[][] tmp = new Rectangle[4][4];
        for(int i =0;i<4;i++) {
            for(int j=0;j<4;j++){

                tmp[i][j]= new Rectangle(squareSize,squareSize);
                tmp[i][j].setTranslateX(i*squareSize);
                tmp[i][j].setTranslateY(j*squareSize);
                tmp[i][j].setFill(Color.TRANSPARENT);
                visibleTetromino.getChildren().add(tmp[i][j]);
            }
        }

        //koloruję tylko te, które muszą być widoczne
        for(int i=0;i<4;i++){
            Rectangle square = new Rectangle(squareSize,squareSize);
            //przesunięcie , bo nie mogą być zera
            tmp[x[i]+2][y[i]+2].setFill(color);
        }

        return visibleTetromino;
    }

    public boolean moveable(BottomBlock b, int toX, int toY){

        boolean moveable=true;
        for(int i = 0;i<4;i++){
            if(!b.isFree(toX+x[i], toY+y[i]))
                moveable = false;
        }

        return moveable;
    }

    public boolean rotateable(BottomBlock b, int positionX, int positionY){

        boolean rotateable = true;

        for(int i=0;i<4;i++){

            int beforeRotationX = x[i]+positionX;
            int beforeRotationY = y[i]+positionY;

            int afterRotationX = y[i]+positionX;
            int afterRotationY = -x[i]-1+positionY;

            //tak jakby po okręgu
            for(int iX = min(beforeRotationX,afterRotationX); iX <= max(beforeRotationX,afterRotationX);iX++){
                for(int iY = min(beforeRotationY,afterRotationY); iY <= max(beforeRotationY,afterRotationY);iY++){
                    if(! b.isFree(iX, iY)) rotateable = false;
                }
            }

        }
        return rotateable;
    }

    public Color getColor(){

        return color;
    }

    public ArrayList<Integer> joinAndGetRowsToDelete(BottomBlock b, int onX, int onY){

        ArrayList<Integer> rowsToDelete = new ArrayList<Integer>();
        for(int i=0;i<4;i++){
            Rectangle r = new Rectangle();
            r.setFill(color);
            b.add(r,onX+x[i],onY+y[i]);

            if(b.isFull(onY+y[i]))
                rowsToDelete.add(onY+y[i]);
        }
        return rowsToDelete;
    }


}
