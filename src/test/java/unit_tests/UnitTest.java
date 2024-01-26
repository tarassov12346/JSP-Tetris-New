package unit_tests;

import jsp.tetris.Player;
import jsp.tetris.Stage;
import jsp.tetris.State;
import jsp.tetris.Tetromino;
import org.testng.Assert;
import org.testng.annotations.*;
import org.apache.log4j.Logger;


import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import static jsp.tetris.Stage.HEIGHT;
import static jsp.tetris.Stage.WIDTH;


@Test()
public class UnitTest {


    private static final Logger log = Logger.getLogger(UnitTest.class);
    int moveCount;



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

    @Test(dataProvider = "data")
    public void doesTetraminoMoveRight(State state) {
        State stateWithNewTetramino=state.newTetramino().orElse(state);
        int tetrominoX = stateWithNewTetramino.stage.getTetraminoX();
        int tetrominoY = stateWithNewTetramino.stage.getTetraminoY();
        State newState=stateWithNewTetramino.tryMoveRight().orElse(stateWithNewTetramino);
        Tetromino tetromino=newState.stage.getTetramino();
        int collapsedLayersCount= newState.stage.collapsedLayersCount;
        State expectedState=new State(makeEmptyStage(collapsedLayersCount).setTetraminoToStage(tetromino,tetrominoX+1,tetrominoY),true, new Player("Tester",collapsedLayersCount*10));
        log.info("Tetramino initial position x="+tetrominoX+" y="+tetrominoY);
        log.info("Tetramino moveRight new position x="+newState.stage.getTetraminoX()+" y="+newState.stage.getTetraminoY());
        Assert.assertEquals(newState,expectedState);
    }

    @Test(dataProvider = "data")
    public void doesTetraminoMoveRightStopAtBorder(State state) {
        moveCount =0;
        State stateWithNewTetramino=state.newTetramino().orElse(state);
        int tetrominoX = stateWithNewTetramino.stage.getTetraminoX();
        int tetrominoY = stateWithNewTetramino.stage.getTetraminoY();

        State newState=moveFarRight(stateWithNewTetramino);
        State expectedState;
        Tetromino tetromino=newState.stage.getTetramino();
        int collapsedLayersCount= newState.stage.collapsedLayersCount;
         switch(getKeyByValue(Tetromino.tetrominoMap,newState.stage.getTetramino()).toString()){
             case "O" ->  expectedState=new State(makeEmptyStage(collapsedLayersCount).setTetraminoToStage(tetromino,10,tetrominoY),true, new Player("Tester",collapsedLayersCount*10));
             case "J" ->  expectedState=new State(makeEmptyStage(collapsedLayersCount).setTetraminoToStage(tetromino,10,tetrominoY),true, new Player("Tester",collapsedLayersCount*10));
             case "I" ->  expectedState=new State(makeEmptyStage(collapsedLayersCount).setTetraminoToStage(tetromino,10,tetrominoY),true, new Player("Tester",collapsedLayersCount*10));
             default ->  expectedState=new State(makeEmptyStage(collapsedLayersCount).setTetraminoToStage(tetromino,9,tetrominoY),true, new Player("Tester",collapsedLayersCount*10));
         }

        log.info("Tetramino initial position x="+tetrominoX+" y="+tetrominoY);
        log.info("Tetramino moveFarRight new position x="+newState.stage.getTetraminoX()+" y="+newState.stage.getTetraminoY());
        log.info("Tetramino type "+getKeyByValue(Tetromino.tetrominoMap,newState.stage.getTetramino()));
        Assert.assertEquals(newState,expectedState);
    }

    @Test(dataProvider = "data")
    public void doesTetraminoMoveLeft(State state) {
        State stateWithNewTetramino=state.newTetramino().orElse(state);
        int tetrominoX = stateWithNewTetramino.stage.getTetraminoX();
        int tetrominoY = stateWithNewTetramino.stage.getTetraminoY();
        State newState=stateWithNewTetramino.tryMoveLeft().orElse(stateWithNewTetramino);
        Tetromino tetromino=newState.stage.getTetramino();
        int collapsedLayersCount= newState.stage.collapsedLayersCount;
        State expectedState=new State(makeEmptyStage(collapsedLayersCount).setTetraminoToStage(tetromino,tetrominoX-1,tetrominoY),true, new Player("Tester",collapsedLayersCount*10));
        log.info("Tetramino initial position x="+stateWithNewTetramino.stage.getTetraminoX()+" y="+stateWithNewTetramino.stage.getTetraminoY());
        log.info("Tetramino moveLeft new position x="+newState.stage.getTetraminoX()+" y="+newState.stage.getTetraminoY());
        Assert.assertEquals(newState,expectedState);
    }

    @Test(dataProvider = "data")
    public void doesTetraminoMoveLeftStopAtBorder(State state) {
        moveCount =0;
        State stateWithNewTetramino=state.newTetramino().orElse(state);
        int tetrominoX = stateWithNewTetramino.stage.getTetraminoX();
        int tetrominoY = stateWithNewTetramino.stage.getTetraminoY();
        State expectedState;

        State newState=moveFarLeft(stateWithNewTetramino);
        Tetromino tetromino=newState.stage.getTetramino();
        int collapsedLayersCount= newState.stage.collapsedLayersCount;
        switch(getKeyByValue(Tetromino.tetrominoMap,newState.stage.getTetramino()).toString()){
            case "L" -> expectedState=new State(makeEmptyStage(collapsedLayersCount).setTetraminoToStage(tetromino,-1,tetrominoY),true, new Player("Tester",collapsedLayersCount*10));
            case "I" -> expectedState=new State(makeEmptyStage(collapsedLayersCount).setTetraminoToStage(tetromino,-1,tetrominoY),true, new Player("Tester",collapsedLayersCount*10));
            default -> expectedState=new State(makeEmptyStage(collapsedLayersCount).setTetraminoToStage(tetromino,0,tetrominoY),true, new Player("Tester",collapsedLayersCount*10));
        }

        log.info("Tetramino initial position x="+tetrominoX+" y="+tetrominoY);
        log.info("Tetramino moveFarLeft new position x="+newState.stage.getTetraminoX()+" y="+newState.stage.getTetraminoY());
        log.info("Tetramino type "+getKeyByValue(Tetromino.tetrominoMap,newState.stage.getTetramino()));
        Assert.assertEquals(newState,expectedState);
    }

    @Test(dataProvider = "data")
    public void doesTetraminoMoveDown(State state) {
        State stateWithNewTetramino=state.newTetramino().orElse(state);
        int tetrominoX = stateWithNewTetramino.stage.getTetraminoX();
        int tetrominoY = stateWithNewTetramino.stage.getTetraminoY();
        State newState=stateWithNewTetramino.tryMoveDown(1).orElse(stateWithNewTetramino);
        Tetromino tetromino=newState.stage.getTetramino();
        int collapsedLayersCount= newState.stage.collapsedLayersCount;
        State expectedState=new State(makeEmptyStage(collapsedLayersCount).setTetraminoToStage(tetromino,tetrominoX,tetrominoY+1),true, new Player("Tester",collapsedLayersCount*10));
        log.info("Tetramino initial position x="+stateWithNewTetramino.stage.getTetraminoX()+" y="+stateWithNewTetramino.stage.getTetraminoY());
        log.info("Tetramino moveDown step1 new position x="+newState.stage.getTetraminoX()+" y="+newState.stage.getTetraminoY());
        Assert.assertEquals(newState,expectedState);
    }



    private  Stage makeEmptyStage(int collapsedLayerCount) {
        final char[][] c = new char[HEIGHT][WIDTH];
        IntStream.range(0, HEIGHT).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> c[y][x] = '0'));
        return new Stage(c, Tetromino.getTetromino('0'), 0, 0, collapsedLayerCount);
    }




    private State moveFarRight(State state){
        state=state.tryMoveRight().orElse(state);
        log.info("x="+state.stage.getTetraminoX());
        moveCount++;
        if (moveCount >12) return state;
     return moveFarRight(state);
    }

    private State moveFarLeft(State state){
        state=state.tryMoveLeft().orElse(state);
        log.info("x="+state.stage.getTetraminoX());
        moveCount++;
        if (moveCount >12) return state;
        return moveFarLeft(state);
    }

    private Character getKeyByValue(Map<Character, Tetromino> map, Tetromino value) {
        for (Map.Entry<Character, Tetromino> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }




    @AfterTest
    public void doAfterTests(){
        log.info("UnitTests are finished");
    }





}
