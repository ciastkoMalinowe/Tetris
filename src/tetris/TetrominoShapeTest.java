package tetris;

import javafx.scene.Group;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by amnich on 17.01.17.
 */
public class TetrominoShapeTest {
//złe testy
    @Test
    public void ZvsS(){
        Group Z = TetrominoShape.Z.getVisibleTetromino(2.0);
        Group S = TetrominoShape.S.getVisibleTetromino(2.0);
        Assert.assertFalse("Z jest różne od S",Z.equals(S));
    }

    @Test
    public void rightRotationTest() {
        TetrominoShape testerT = TetrominoShape.T;
        TetrominoShape testerL = TetrominoShape.L;
        TetrominoShape testerZ = TetrominoShape.Z;

        for(int i=0;i<4;i++) {
            testerT.rotateRight();
            testerL.rotateRight();
            testerZ.rotateRight();
        }


        //Assert.assertTrue("T: 4 rotacje się znoszą", T1.getChildren().get(3).equals(testerT.getVisibleTetromino(2.0).getChildren().get(3) ));
        //Assert.assertTrue("L: 4 rotacje się znoszą", L1.getChildren().get(2).equals(testerL.getVisibleTetromino(2.0).getChildren().get(2) ));
        //Assert.assertTrue("Z: 4 rotacje się znoszą", Z1.getChildren().get(1).equals(testerZ.getVisibleTetromino(2.0).getChildren().get(1) ));


    }
}

