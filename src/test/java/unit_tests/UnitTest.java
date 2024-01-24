package unit_tests;

import jsp.tetris.Player;
import jsp.tetris.Stage;
import jsp.tetris.State;
import jsp.tetris.Tetromino;
import org.testng.Assert;
import org.testng.annotations.*;
import org.apache.log4j.Logger;


import java.util.stream.IntStream;

import static jsp.tetris.Stage.HEIGHT;
import static jsp.tetris.Stage.WIDTH;

@Test()
public class UnitTest {


    private static final Logger log = Logger.getLogger(UnitTest.class);

    @DataProvider
    public Object[][] data() {
        final char[][] cS = new char[HEIGHT][WIDTH];
        IntStream.range(0, HEIGHT-3).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> cS[y][x] = '0'));
        IntStream.range(HEIGHT-3,HEIGHT).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> cS[y][x] = 'S'));

        final char[][] cI = new char[HEIGHT][WIDTH];
        IntStream.range(0, HEIGHT-8).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> cI[y][x] = '0'));
        IntStream.range(HEIGHT-8,HEIGHT).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> cI[y][x] = 'I'));


        return new State[][] {{new State(Stage.recreateStage(cS,0),true, new Player("Tester",0))},{new State(Stage.recreateStage(cI,0),true, new Player("Tester",0))}};
    }

    @BeforeTest
    public void doBeforeTests(){
        log.info("UnitTests start");
    }

    @Test(dataProvider = "data")
    public void doFullRowsCollapseAndScoreIsUpdated(State state) {

        State newState=state.newTetramino().orElse(state);
        Tetromino tetromino=newState.stage.getTetramino();
        int tetrominoX = newState.stage.getTetraminoX();
        int tetrominoY = newState.stage.getTetraminoY();
        int collapsedLayersCount= newState.stage.collapsedLayersCount;
        log.info("collapsed layers count="+collapsedLayersCount);
        log.info("players score ="+newState.player.getPlayerScore());
        State expectedState=new State(makeEmptyStage(collapsedLayersCount).setTetraminoToStage(tetromino,tetrominoX,tetrominoY),true, new Player("Tester",collapsedLayersCount*10));
        Assert.assertEquals(newState,expectedState);
    }

    private  Stage makeEmptyStage(int collapsedLayerCount) {
        final char[][] c = new char[HEIGHT][WIDTH];
        IntStream.range(0, HEIGHT).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> c[y][x] = '0'));
        return new Stage(c, Tetromino.getTetromino('0'), 0, 0, collapsedLayerCount);
    }

    @AfterTest
    public void doAfterTests(){
        log.info("UnitTests are finished");
    }





}
