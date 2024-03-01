package jsp.tetris.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Tetramino {
    public static final Map<Character, Tetramino> tetraminoMap;

    static {
        tetraminoMap = new HashMap<>();
        tetraminoMap.put('0', new Tetramino(new char[][]{{'0'}}));
        tetraminoMap.put('I', new Tetramino(new char[][]{{'0', 'I', '0', '0'}, {'0', 'I', '0', '0'}, {'0', 'I', '0', '0'}, {'0', 'I', '0', '0'}}));
        tetraminoMap.put('J', new Tetramino(new char[][]{{'0', 'J', '0'}, {'0', 'J', '0'}, {'J', 'J', '0'}}));
        tetraminoMap.put('L', new Tetramino(new char[][]{{'0', 'L', '0'}, {'0', 'L', '0'}, {'0', 'L', 'L'}}));
        tetraminoMap.put('O', new Tetramino(new char[][]{{'O', 'O'}, {'O', 'O'}}));
        tetraminoMap.put('S', new Tetramino(new char[][]{{'0', 'S', 'S'}, {'S', 'S', '0'}, {'0', '0', '0'}}));
        tetraminoMap.put('T', new Tetramino(new char[][]{{'0', '0', '0'}, {'T', 'T', 'T'}, {'0', 'T', '0'}}));
        tetraminoMap.put('Z', new Tetramino(new char[][]{{'Z', 'Z', '0'}, {'0', 'Z', 'Z'}, {'0', '0', '0'}}));
        tetraminoMap.put('K', new Tetramino(new char[][]{{'K', 'K', 'K'}, {'0', 'K', '0'}, {'0', 'K', '0'}}));
    }

    private final char[][] shape;

    public Tetramino(char[][] shape) {
        this.shape = shape;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tetramino tetramino = (Tetramino) o;
        return Arrays.deepEquals(shape, tetramino.shape);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(shape);
    }

    public static Tetramino getTetramino(Character tetramino) {
        return tetraminoMap.get(tetramino);
    }

    public static Tetramino getRandomTetramino() {
        final char[] tetraminos = "IJLOSTZK".toCharArray();
        return tetraminoMap.get(tetraminos[new Random().nextInt(tetraminos.length)]);
    }

    public char[][] getShape() {
        return this.shape;
    }
}
