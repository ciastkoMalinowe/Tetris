package tetris;

import static java.lang.Integer.compare;

/**
 * Created by amnich on 18.01.17.
 */
public class Player implements Comparable<Player>{

    public String name;
    public GameSize gameSize;
    public String game;
    public int score;

    public Player(){
        name="-";
        gameSize=GameSize.noSize;
        game = gameSize.toString();
        score=0;
    }

    public Player(String name, GameSize gameSize, int score){

        this.name = name;
        this.gameSize=gameSize;
        this.game=gameSize.toString();
        this.score=score;

    }

    public int compareTo(Player other){
        int pointsDiff = compare(other.score,score);

        if(pointsDiff==0){
            if(name.equals("-"))
                return 1;
            else return -1;
        }
        else return pointsDiff;
    }
}
