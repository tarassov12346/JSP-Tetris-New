package jsp.tetris;

import java.util.*;

public class Tetromino {
    private static final Map<Character, Tetromino> tetrominoMap;

    static {
        tetrominoMap = new HashMap<>();
        tetrominoMap.put('0', new Tetromino(new char[][]{{'0'}}));
        tetrominoMap.put('I', new Tetromino(new char[][]{{'0', 'I', '0', '0'}, {'0', 'I', '0', '0'}, {'0', 'I', '0', '0'}, {'0', 'I', '0', '0'}}));
        tetrominoMap.put('J', new Tetromino(new char[][]{{'0', 'J', '0'}, {'0', 'J', '0'}, {'J', 'J', '0'}}));
        tetrominoMap.put('L', new Tetromino(new char[][]{{'0', 'L', '0'}, {'0', 'L', '0'}, {'0', 'L', 'L'}}));
        tetrominoMap.put('O', new Tetromino(new char[][]{{'O', 'O'}, {'O', 'O'}}));
        tetrominoMap.put('S', new Tetromino(new char[][]{{'0', 'S', 'S'}, {'S', 'S', '0'}, {'0', '0', '0'}}));
        tetrominoMap.put('T', new Tetromino(new char[][]{{'0', '0', '0'}, {'T', 'T', 'T'}, {'0', 'T', '0'}}));
        tetrominoMap.put('Z', new Tetromino(new char[][]{{'Z', 'Z', '0'}, {'0', 'Z', 'Z'}, {'0', '0', '0'}}));
        tetrominoMap.put('K', new Tetromino(new char[][]{{'K', 'K', 'K'}, {'0', 'K', '0'}, {'0', 'K', '0'}}));
    }

    private final char[][] shape;

    public Tetromino(char[][] shape) {
        this.shape = shape;
    }

    public static Tetromino getTetromino(Character tetromino) {
        return tetrominoMap.get(tetromino);
    }

    public static Tetromino getRandomTetromino() {
        final char[] tetrominos = "IJLOSTZK".toCharArray();
        return tetrominoMap.get(tetrominos[new Random().nextInt(tetrominos.length)]);
    }

    public char[][] getShape() {
        return this.shape;
    }
}
