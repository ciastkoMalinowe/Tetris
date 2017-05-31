package tetris;

import java.util.ArrayList;
import java.util.prefs.Preferences;

import static java.util.Collections.sort;

/**
 * Created by amnich on 18.01.17.
 */
public class ScoreBoard {

    public int ListSize = 7;

    private ArrayList<Player> players;

    private Preferences preferences = Preferences.userNodeForPackage(Player.class);

    public ScoreBoard(){

        players = new ArrayList<Player>();
        read();

    }

    private void save() {

        sort(players);

        for (int i =0; i<ListSize;i++) {

            preferences.putInt(i+"_SCORE", players.get(i).score);
            preferences.put(i+"_NAME", players.get(i).name);
            preferences.put(i+"_GAMESIZE", players.get(i).game);
        }
    }

    private void read(){

        for(int i=0;i<ListSize;i++){

            Player player = new Player();
            player.score = preferences.getInt(i+"_SCORE",0);
            player.name = preferences.get(i+"_NAME","-");
            player.game=preferences.get(i+"_GAMESIZE","-");
            players.add(player);

        }

    }

    public void add(Player player){

        players.add(player);
        save();
    }

    public Player get(int i){
        return players.get(i);
    }


    public int getPosition(Player player){

        sort(players);
        return players.indexOf(player);
    }

}
