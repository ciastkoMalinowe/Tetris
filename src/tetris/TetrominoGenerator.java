package tetris;

/**
 * Created by amnich on 07.01.17.
 */
public class TetrominoGenerator {

    private int numberOfTetrominos;

    private int numberOfRotatePositions;
    private TetrominoShape[] tetrominos;
    private GameSize gameSize;

    public TetrominoGenerator(GameSize gameSize){

        this.gameSize = gameSize;
        this.numberOfTetrominos = 7;
        this.numberOfRotatePositions = 4;

        tetrominos = new TetrominoShape[numberOfTetrominos];
        tetrominos[0] = TetrominoShape.Dot;
        tetrominos[1] = TetrominoShape.T;
        tetrominos[2] = TetrominoShape.J;
        tetrominos[3] = TetrominoShape.L;
        tetrominos[4] = TetrominoShape.S;
        tetrominos[5] = TetrominoShape.Z;
        tetrominos[6] = TetrominoShape.I;
    }

    private TetrominoShape getRandomShape(){
        TetrominoShape t = tetrominos[(int) (Math.random() * numberOfTetrominos)];
        return t;
    }

    public Tetromino getRandom(){

        int w = (int)(Math.random()*(gameSize.width-4)+2);
        int r = (int)(Math.random()*numberOfRotatePositions);
        Tetromino t = new Tetromino(w,gameSize.top,gameSize.squareSize,getRandomShape());

        int rotationAngle = 0;
        for(int i=0;i<r;i++) {
            t.rotate();
            rotationAngle-=90;
        }
        t.setRotate(rotationAngle);

        //jest przesunięcie o -2, bo przy visible tetromino bylo z racji na dopełnienie do kwadratu 4 na 4
        // i przesunięcie o +2 bo jest ramka
        t.setTranslateX((w)*gameSize.squareSize);
        t.setTranslateY(gameSize.top*gameSize.squareSize);
        return t;

    }
}