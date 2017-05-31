package tetris;

/**
 * Created by amnich on 18.01.17.
 */
public enum GameSize {

    small(16,20,20,2),
    medium(20,25,16,2),
    large(32,40,10,2),
    noSize(0,0,0,0);

    public int width;
    public int height;
    public int squareSize;
    public int top;

    private GameSize(int width, int height, int squareSize, int top){

        this.width = width;
        this.height = height;
        this.squareSize = squareSize;
        this.top = top;
    }
}
