package communicator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import tetris.ScoreBoard;

import static java.lang.Integer.valueOf;
import static java.util.Collections.sort;

/**
 * Created by amnich on 19.01.17.
 */
public class ScoreBoardPresentation extends Group{

    public ScoreBoardPresentation(Stage stage, ScoreBoard scoreBoard){

        Button back = new Button("Back");
        back.setTextFill(Color.DARKBLUE);
        back.setMinWidth(75);
        back.setMinHeight(50);
        back.setAlignment(Pos.CENTER);
        back.setTranslateX(115);
        back.setTranslateY(165);
        back.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {

                Greeter greeter = new Greeter(stage);
                stage.setScene(new Scene(greeter,greeter.greeter_width,greeter.greeter_height));
                stage.show();
                greeter.requestFocus();
            }
        });

        HBox ranking = display(scoreBoard);
        ranking.setAlignment(Pos.CENTER);
        ranking.setTranslateY(50);
        ranking.setTranslateX(5);

        Text title = new Text("Best results");
        title.setFont(Font.font(22));
        title.setFill(Color.DARKBLUE);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setTranslateY(20);
        title.setTranslateX(100);

        this.getChildren().add(title);
        this.getChildren().add(ranking);
        this.getChildren().add(back);
    }

    private HBox display(ScoreBoard scoreBoard){

        //System.out.println("display");
        HBox ranking = new HBox();
        VBox names = new VBox();
        VBox games = new VBox();
        games.setTranslateX(75);
        VBox scores = new VBox();
        scores.setTranslateX(150);

        for(int i=0;i<scoreBoard.ListSize;i++){
            Text n = new Text();
            n.setFill(Color.DARKBLUE);
            Text g = new Text();
            g.setFill(Color.DARKBLUE);
            Text s = new Text();
            s.setFill(Color.DARKBLUE);
            n.setText( scoreBoard.get(i).name);
            g.setText(scoreBoard.get(i).game);
            s.setText(String.valueOf(valueOf(scoreBoard.get(i).score)));
            names.getChildren().add(n);
            games.getChildren().add(g);
            scores.getChildren().add(s);

        }
        ranking.getChildren().addAll(names,games,scores);
        return ranking;
    }
}
