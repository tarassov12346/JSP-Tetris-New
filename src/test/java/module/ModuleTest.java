package module;

import jsp.tetris.Player;
import jsp.tetris.Stage;
import jsp.tetris.State;
import jsp.tetris.Tetromino;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.log4j.Logger;


import java.util.stream.IntStream;

import static jsp.tetris.Stage.HEIGHT;
import static jsp.tetris.Stage.WIDTH;

@Test()
public class ModuleTest {


    private static final Logger log = Logger.getLogger(ModuleTest.class);

    @DataProvider
    public Object[][] data() {
        final char[][] cS = new char[HEIGHT][WIDTH];
        IntStream.range(0, HEIGHT-3).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> cS[y][x] = '0'));
        IntStream.range(HEIGHT-3,HEIGHT).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> cS[y][x] = 'S'));

        final char[][] cI = new char[HEIGHT][WIDTH];
        IntStream.range(0, HEIGHT-3).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> cI[y][x] = '0'));
        IntStream.range(HEIGHT-3,HEIGHT).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> cI[y][x] = 'I'));


        return new State[][] {{new State(Stage.recreateStage(cS,0),true, new Player("Tester",0))},{new State(Stage.recreateStage(cI,0),true, new Player("Tester",0))}};
    }

    @Test(dataProvider = "data")
    public void doFullRowsCollapseTest(State state) {

        log.info("test begin!!!!!");
        State newState=state.newTetramino().orElse(state);
        Tetromino tetromino=newState.stage.getTetramino();
        int tetrominoX = newState.stage.getTetraminoX();
        int tetrominoY = newState.stage.getTetraminoY();
        int collapsedLayersCount= newState.stage.collapsedLayersCount;
        State expectedState=new State(makeEmptyStage(collapsedLayersCount).setTetraminoToStage(tetromino,tetrominoX,tetrominoY),true, new Player("Tester",30));
        Assert.assertEquals(newState,expectedState);
    }

    private  Stage makeEmptyStage(int collapsedLayerCount) {
        final char[][] c = new char[HEIGHT][WIDTH];
        IntStream.range(0, HEIGHT).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> c[y][x] = '0'));
        return new Stage(c, Tetromino.getTetromino('0'), 0, 0, collapsedLayerCount);
    }




}
