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
        return new State[][]{{new State(makeStageWith2FilledRows(), true, new Player("Tester", 0)),}, {new State(makeStageWith3FilledRows(), true, new Player("Tester", 0))}};
    }

    @BeforeTest
    public void doBeforeTests() {
        log.info("UnitTests start");
    }

    @BeforeMethod
    public void doBeforeEachTestMethod() {
        log.info("Test Method  is called");
    }

    @Test(dataProvider = "data", groups = {"rowsProcessingChecks"})
    public void doFullRowsCollapseAndScoreIsUpdated(State state) {
        log.info("doFullRowsCollapseAndScoreIsUpdated Test start");
        log.info("filled rows number is " + countFilledCells(state));
        State newState = state.newTetramino().orElse(state);
        Tetromino tetromino = newState.stage.getTetramino();
        log.info("new tetramino is called with the shape type " + getShapeTypeByTetromino(tetromino));
        int tetrominoX = newState.stage.getTetraminoX();
        int tetrominoY = newState.stage.getTetraminoY();
        int collapsedLayersCount = newState.stage.collapsedLayersCount;
        log.info("collapsed layers count=" + collapsedLayersCount);
        log.info("players score =" + newState.player.getPlayerScore());
        State expectedState = new State(makeStageWithOnlyLeftUnfilledRows(collapsedLayersCount).setTetraminoToStage(tetromino, tetrominoX, tetrominoY), true, new Player("Tester", collapsedLayersCount * 10));
        Assert.assertEquals(newState, expectedState);
    }

    @Test(dataProvider = "data", groups = {"tetraminoBehaviourChecks"})
    public void doesTetraminoMoveRight(State state) {
        log.info("doesTetraminoMoveRight Test start");
        State stateWithNewTetramino = state.newTetramino().orElse(state);
        log.info("new tetramino is called with the shape type " + getShapeTypeByTetromino(stateWithNewTetramino.stage.getTetramino()));
        int tetrominoX = stateWithNewTetramino.stage.getTetraminoX();
        int tetrominoY = stateWithNewTetramino.stage.getTetraminoY();
        State newState = stateWithNewTetramino.tryMoveRight().orElse(stateWithNewTetramino);
        Tetromino tetromino = newState.stage.getTetramino();
        int collapsedLayersCount = newState.stage.collapsedLayersCount;
        State expectedState = new State(makeStageWithOnlyLeftUnfilledRows(collapsedLayersCount).setTetraminoToStage(tetromino, tetrominoX + 1, tetrominoY), true, new Player("Tester", collapsedLayersCount * 10));
        log.info("Tetramino initial position x=" + tetrominoX + " y=" + tetrominoY);
        log.info("moveRight is called");
        log.info("Tetramino after moveRight new position x=" + newState.stage.getTetraminoX() + " y=" + newState.stage.getTetraminoY());
        Assert.assertEquals(newState, expectedState);
    }

    @Test(dataProvider = "data", groups = {"tetraminoBehaviourChecks"})
    public void doesTetraminoMoveRightStopAtBorder(State state) {
        log.info("doesTetraminoMoveRightStopAtBorder Test start");
        moveCount = 0;
        State stateWithNewTetramino = state.newTetramino().orElse(state);
        log.info("new tetramino is called with the shape type " + getShapeTypeByTetromino(stateWithNewTetramino.stage.getTetramino()));
        int tetrominoX = stateWithNewTetramino.stage.getTetraminoX();
        int tetrominoY = stateWithNewTetramino.stage.getTetraminoY();
        State newState = moveFarRight(stateWithNewTetramino);
        State expectedState;
        Tetromino tetromino = newState.stage.getTetramino();
        int collapsedLayersCount = newState.stage.collapsedLayersCount;
        switch (getShapeTypeByTetromino(newState.stage.getTetramino()).toString()) {
            case "O", "J", "I" -> expectedState = new State(makeStageWithOnlyLeftUnfilledRows(collapsedLayersCount).setTetraminoToStage(tetromino, 10, tetrominoY), true, new Player("Tester", collapsedLayersCount * 10));
            default -> expectedState = new State(makeStageWithOnlyLeftUnfilledRows(collapsedLayersCount).setTetraminoToStage(tetromino, 9, tetrominoY), true, new Player("Tester", collapsedLayersCount * 10));
        }
        log.info("Tetramino initial position x=" + tetrominoX + " y=" + tetrominoY);
        log.info("moveRight 13 times is performed");
        log.info("Tetramino moveRight 13 times new position x=" + newState.stage.getTetraminoX() + " y=" + newState.stage.getTetraminoY());
        log.info("Tetramino type " + getShapeTypeByTetromino(newState.stage.getTetramino()));
        Assert.assertEquals(newState, expectedState);
    }

    @Test(dataProvider = "data", groups = {"tetraminoBehaviourChecks"})
    public void doesTetraminoMoveLeft(State state) {
        log.info("doesTetraminoMoveLeft Test start");
        State stateWithNewTetramino = state.newTetramino().orElse(state);
        log.info("new tetramino is called with the shape type " + getShapeTypeByTetromino(stateWithNewTetramino.stage.getTetramino()));
        int tetrominoX = stateWithNewTetramino.stage.getTetraminoX();
        int tetrominoY = stateWithNewTetramino.stage.getTetraminoY();
        State newState = stateWithNewTetramino.tryMoveLeft().orElse(stateWithNewTetramino);
        Tetromino tetromino = newState.stage.getTetramino();
        int collapsedLayersCount = newState.stage.collapsedLayersCount;
        State expectedState = new State(makeStageWithOnlyLeftUnfilledRows(collapsedLayersCount).setTetraminoToStage(tetromino, tetrominoX - 1, tetrominoY), true, new Player("Tester", collapsedLayersCount * 10));
        log.info("Tetramino initial position x=" + stateWithNewTetramino.stage.getTetraminoX() + " y=" + stateWithNewTetramino.stage.getTetraminoY());
        log.info("moveLeft is called");
        log.info("Tetramino after moveLeft new position x=" + newState.stage.getTetraminoX() + " y=" + newState.stage.getTetraminoY());
        Assert.assertEquals(newState, expectedState);
    }

    @Test(dataProvider = "data", groups = {"tetraminoBehaviourChecks"})
    public void doesTetraminoMoveLeftStopAtBorder(State state) {
        log.info("doesTetraminoMoveLeftStopAtBorder Test start");
        moveCount = 0;
        State stateWithNewTetramino = state.newTetramino().orElse(state);
        log.info("new tetramino is called with the shape type " + getShapeTypeByTetromino(stateWithNewTetramino.stage.getTetramino()));
        int tetrominoX = stateWithNewTetramino.stage.getTetraminoX();
        int tetrominoY = stateWithNewTetramino.stage.getTetraminoY();
        State expectedState;
        State newState = moveFarLeft(stateWithNewTetramino);
        Tetromino tetromino = newState.stage.getTetramino();
        int collapsedLayersCount = newState.stage.collapsedLayersCount;
        switch (getShapeTypeByTetromino(newState.stage.getTetramino()).toString()) {
            case "L", "I" -> expectedState = new State(makeStageWithOnlyLeftUnfilledRows(collapsedLayersCount).setTetraminoToStage(tetromino, -1, tetrominoY), true, new Player("Tester", collapsedLayersCount * 10));
            default -> expectedState = new State(makeStageWithOnlyLeftUnfilledRows(collapsedLayersCount).setTetraminoToStage(tetromino, 0, tetrominoY), true, new Player("Tester", collapsedLayersCount * 10));
        }
        log.info("Tetramino initial position x=" + tetrominoX + " y=" + tetrominoY);
        log.info("moveLeft 13 times is performed");
        log.info("Tetramino moveLeft 13 times new position x=" + newState.stage.getTetraminoX() + " y=" + newState.stage.getTetraminoY());
        log.info("Tetramino type " + getShapeTypeByTetromino(newState.stage.getTetramino()));
        Assert.assertEquals(newState, expectedState);
    }

    @Test(dataProvider = "data", groups = {"tetraminoBehaviourChecks"})
    public void doesTetraminoMoveDown(State state) {
        log.info("doesTetraminoMoveDown Test start");
        State stateWithNewTetramino = state.newTetramino().orElse(state);
        log.info("new tetramino is called with the shape type " + getShapeTypeByTetromino(stateWithNewTetramino.stage.getTetramino()));
        int tetrominoX = stateWithNewTetramino.stage.getTetraminoX();
        int tetrominoY = stateWithNewTetramino.stage.getTetraminoY();
        State newState = stateWithNewTetramino.tryMoveDown(1).orElse(stateWithNewTetramino);
        Tetromino tetromino = newState.stage.getTetramino();
        int collapsedLayersCount = newState.stage.collapsedLayersCount;
        State expectedState = new State(makeStageWithOnlyLeftUnfilledRows(collapsedLayersCount).setTetraminoToStage(tetromino, tetrominoX, tetrominoY + 1), true, new Player("Tester", collapsedLayersCount * 10));
        log.info("Tetramino initial position x=" + stateWithNewTetramino.stage.getTetraminoX() + " y=" + stateWithNewTetramino.stage.getTetraminoY());
        log.info("moveDown is called");
        log.info("Tetramino after moveDown with step 1 new position x=" + newState.stage.getTetraminoX() + " y=" + newState.stage.getTetraminoY());
        Assert.assertEquals(newState, expectedState);
    }

    @Test(dataProvider = "data", groups = {"tetraminoBehaviourChecks"})
    public void doesTetraminoMoveDownStopAtUnfilledLayers(State state) {
        log.info("doesTetraminoMoveDownStopAtUnfilledLayers Test start");
        moveCount = 0;
        State stateWithNewTetramino = state.newTetramino().orElse(state);
        log.info("new tetramino is called with the shape type " + getShapeTypeByTetromino(stateWithNewTetramino.stage.getTetramino()));
        int tetrominoX = stateWithNewTetramino.stage.getTetraminoX();
        int tetrominoY = stateWithNewTetramino.stage.getTetraminoY();
        State expectedState;
        State newState = moveDeepDown(stateWithNewTetramino);
        Tetromino tetromino = newState.stage.getTetramino();
        int collapsedLayersCount = newState.stage.collapsedLayersCount;
        switch (getShapeTypeByTetromino(newState.stage.getTetramino()).toString()) {
            case "L", "J" -> expectedState = new State(makeStageWithOnlyLeftUnfilledRows(collapsedLayersCount).setTetraminoToStage(tetromino, tetrominoX, 15), true, new Player("Tester", collapsedLayersCount * 10));
            case "K" -> expectedState = new State(makeStageWithOnlyLeftUnfilledRows(collapsedLayersCount).setTetraminoToStage(tetromino, tetrominoX, 17), true, new Player("Tester", collapsedLayersCount * 10));
            default -> expectedState = new State(makeStageWithOnlyLeftUnfilledRows(collapsedLayersCount).setTetraminoToStage(tetromino, tetrominoX, 16), true, new Player("Tester", collapsedLayersCount * 10));
        }
        log.info("Tetramino initial position x=" + tetrominoX + " y=" + tetrominoY);
        log.info("moveDown 25 times is performed");
        log.info("Tetramino moveDown 25 times new position x=" + newState.stage.getTetraminoX() + " y=" + newState.stage.getTetraminoY());
        log.info("Tetramino type " + getShapeTypeByTetromino(newState.stage.getTetramino()));
        Assert.assertEquals(newState, expectedState);
    }

    @Test(dataProvider = "data", groups = {"tetraminoBehaviourChecks"})
    public void doesTetraminoRotate(State state) {
        log.info("doesTetraminoRotate Test start");
        State stateWithNewTetramino = state.newTetramino().orElse(state);
        log.info("new tetramino is called with the shape type " + getShapeTypeByTetromino(stateWithNewTetramino.stage.getTetramino()));
        int tetrominoX = stateWithNewTetramino.stage.getTetraminoX();
        int tetrominoY = stateWithNewTetramino.stage.getTetraminoY();
        State newState = stateWithNewTetramino.tryRotate().orElse(stateWithNewTetramino);
        Tetromino newTetromino = new Tetromino(rotateMatrix(stateWithNewTetramino.stage.getTetramino().getShape()));
        int collapsedLayersCount = newState.stage.collapsedLayersCount;
        State expectedState = new State(makeStageWithOnlyLeftUnfilledRows(collapsedLayersCount).setTetraminoToStage(newTetromino, tetrominoX, tetrominoY), true, new Player("Tester", collapsedLayersCount * 10));
        log.info("Tetramino initial shape " + matrixToString(stateWithNewTetramino.stage.getTetramino().getShape()));
        log.info("Tetramino after rotate new shape " + matrixToString(newState.stage.getTetramino().getShape()));
        Assert.assertEquals(newState, expectedState);
    }

    private Stage makeStageWithOnlyLeftUnfilledRows(int collapsedLayerCount) {
        final char[][] c = new char[HEIGHT][WIDTH];
        IntStream.range(0, HEIGHT - 2).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> c[y][x] = '0'));
        IntStream.range(HEIGHT - 2, HEIGHT).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> {
                    switch (x % 3) {
                        case 0 -> c[y][x] = 'S';
                        case 1 -> c[y][x] = 'I';
                        default -> c[y][x] = '0';
                    }
                })
        );
        return new Stage(c, Tetromino.getTetromino('0'), 0, 0, collapsedLayerCount);
    }

    private Stage makeStageWith2FilledRows() {
        final char[][] c = new char[HEIGHT][WIDTH];
        IntStream.range(0, HEIGHT - 4).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> c[y][x] = '0'));
        IntStream.range(HEIGHT - 4, HEIGHT - 2).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> {
                    switch (x % 3) {
                        case 0 -> c[y][x] = 'S';
                        case 1 -> c[y][x] = 'L';
                        case 2 -> c[y][x] = 'O';
                        default -> c[y][x] = 'I';
                    }
                })
        );
        IntStream.range(HEIGHT - 2, HEIGHT).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> {
                    switch (x % 3) {
                        case 0 -> c[y][x] = 'S';
                        case 1 -> c[y][x] = 'I';
                        default -> c[y][x] = '0';
                    }
                })
        );
        return new Stage(c, Tetromino.getTetromino('0'), 0, 0, 0);
    }

    private Stage makeStageWith3FilledRows() {
        final char[][] c = new char[HEIGHT][WIDTH];
        IntStream.range(0, HEIGHT - 5).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> c[y][x] = '0'));
        IntStream.range(HEIGHT - 5, HEIGHT - 2).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> {
                    switch (x % 3) {
                        case 0 -> c[y][x] = 'O';
                        case 1 -> c[y][x] = 'S';
                        case 2 -> c[y][x] = 'I';
                        default -> c[y][x] = 'L';
                    }
                })
        );
        IntStream.range(HEIGHT - 2, HEIGHT).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> {
                    switch (x % 3) {
                        case 0 -> c[y][x] = 'S';
                        case 1 -> c[y][x] = 'I';
                        default -> c[y][x] = '0';
                    }
                })
        );
        return new Stage(c, Tetromino.getTetromino('0'), 0, 0, 0);
    }

    private State moveFarRight(State state) {
        state = state.tryMoveRight().orElse(state);
        log.info("move tetramino 1 step right");
        log.info("tetramino position x=" + state.stage.getTetraminoX() + " y=" + state.stage.getTetraminoY());
        moveCount++;
        if (moveCount > 12) return state;
        return moveFarRight(state);
    }

    private State moveFarLeft(State state) {
        state = state.tryMoveLeft().orElse(state);
        log.info("move tetramino 1 step left");
        log.info("tetramino position x=" + state.stage.getTetraminoX() + " y=" + state.stage.getTetraminoY());
        moveCount++;
        if (moveCount > 12) return state;
        return moveFarLeft(state);
    }

    private State moveDeepDown(State state) {
        state = state.tryMoveDown(1).orElse(state);
        log.info("move tetramino 1 step down");
        log.info("tetramino position x=" + state.stage.getTetraminoX() + " y=" + state.stage.getTetraminoY());
        moveCount++;
        if (moveCount > 25) return state;
        return moveDeepDown(state);
    }

    private Character getShapeTypeByTetromino(Tetromino value) {
        for (Map.Entry<Character, Tetromino> entry : Tetromino.tetrominoMap.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private int countFilledCells(State state) {
        char[][] cells = state.stage.getCells();
        int count = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (cells[i][j] == '0') {
                    count++;
                    break;
                }
            }
        }
        return HEIGHT - count;
    }

    private char[][] rotateMatrix(char[][] m) {
        final int h = m.length;
        final int w = m[0].length;
        final char[][] t = new char[h][w];
        IntStream.range(0, h).forEach(y -> IntStream.range(0, w).forEach(x -> t[w - x - 1][y] = m[y][x]));
        return t;
    }

    private String matrixToString(char[][] m) {
        StringBuilder expectedStr = new StringBuilder();
        expectedStr.append("{");
        for (char[] strings : m) {
            expectedStr.append("{");
            for (char s : strings) {
                expectedStr.append('"');
                expectedStr.append(s);
                expectedStr.append('"');
                expectedStr.append(',');
            }
            expectedStr.deleteCharAt(expectedStr.length() - 1);
            expectedStr.append("},");
        }
        expectedStr.deleteCharAt(expectedStr.length() - 1);
        expectedStr.append("}");
        return expectedStr.toString();
    }

    @AfterMethod
    public void doAfterEachTestMethod() {
        log.info("Test Method  is finished");
    }

    @AfterTest
    public void doAfterTests() {
        log.info("UnitTests are finished");
    }
}
