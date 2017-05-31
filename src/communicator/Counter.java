package communicator;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by amnich on 17.01.17.
 */
public class Counter extends Text {

    private int counter;

    public Counter() {
        super();
        counter = 0;
        setText("00");
        setFill(Color.DARKBLUE);
        setTextAlignment(TextAlignment.CENTER);
        setFont(Font.font(22));
        setTranslateX(180);
        setTranslateY(20);

    }

    public void add(int c){
        counter+=c;
        setText(String.valueOf(counter));
    }

    public int get(){
        return counter;
    }


}
