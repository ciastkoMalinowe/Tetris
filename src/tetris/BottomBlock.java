package tetris;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by amnich on 06.01.17.
 */
public class BottomBlock extends Group {

    private GameSize gameSize;


    //podklasa Row prezentująca wiersze w takim bloku na dole.
    private class Row extends Group{

        private int freeLeft;
        private Rectangle[] row;

        Row(int width){

            row = new Rectangle[width];
            freeLeft = width;
        }

        public boolean isFree(int x){

            return (row[x]==null);
        }

        public boolean isFull(){

            return(freeLeft == 0);
        }

        public void add(Rectangle r, int x){

            row[x] = r;
            this.getChildren().add(r);
            freeLeft--;
        }

    }

    LinkedList<Row> rows;

    public BottomBlock(GameSize gameSize){

        this.gameSize = gameSize;

        rows = new LinkedList<Row>();

        for(int i=0;i<gameSize.height;i++){

            Row tmp = new Row(gameSize.width);
            tmp.setTranslateY(i*gameSize.squareSize);
            rows.add(tmp);
            this.getChildren().add(tmp);

        }

    }

    public boolean isFree(int x, int y){

        if(y<gameSize.height && (x<gameSize.width && x>=0))
            return (rows.get(y).isFree(x));
        return false;
    }

    private FadeTransition fadeRow(int y){

        Row tmp = new Row(gameSize.width);
        tmp.setTranslateY(-gameSize.squareSize);
        FadeTransition fade = new FadeTransition(Duration.millis(1000),rows.remove(y));
        fade.setToValue(0);

        //dodaję pusty na początek (a właściwie miejsce przed początkiem, żeby jak będzie przesunięcie to żeby wjechało na 0)
        this.getChildren().add(tmp);
        rows.add(0,tmp);
        return fade;
    }

    //metoda do zrzucania reszty -> zwraca Grupę na której robi się transition
    private ParallelTransition fallDown(int y){

        ParallelTransition fallAll = new ParallelTransition();

        for(int i = y;i>=0;i--){
            TranslateTransition fallOne = new TranslateTransition(Duration.millis(300),rows.get(i));
            fallOne.setByY(gameSize.squareSize);
            fallAll.getChildren().add(fallOne);
        }
        return fallAll;
    }

    public SequentialTransition deleteRows(ArrayList<Integer> rowsToDelete){

        SequentialTransition all = new SequentialTransition();

        Collections.sort(rowsToDelete);//żeby się dobrze usunęły
        for(int y : rowsToDelete){

            SequentialTransition fadeAndFall = new SequentialTransition();

            fadeAndFall.getChildren().add(fadeRow(y));
            fadeAndFall.getChildren().add(fallDown(y));
            all.getChildren().add(fadeAndFall);
        }

        return all;
    }


    public void add(Rectangle r, int x, int y){

        r.setWidth(gameSize.squareSize);
        r.setHeight(gameSize.squareSize);
        r.setTranslateX(x*gameSize.squareSize);
        r.setTranslateY(0); //bo cały row jest odpowiednio przesunięty
        rows.get(y).add(r,x);
    }

    public boolean isFull(int y){

        if(y>=0 && y<gameSize.height){
            return rows.get(y).isFull();
        }
        return false;
    }

    public boolean isTop(){
        return(rows.get(gameSize.top).freeLeft!=gameSize.width);
    }

    public Row remove(int y){
        return rows.remove(y);
    }

    public Row get(int y){
        return rows.get(y);
    }
}
