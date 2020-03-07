import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int[][] field = new int[20][10];

    public Board() {
        for (int[] row: field) {
            Arrays.fill(row, 0);
        }
    }

    Boolean collidesWith(Shape shape) {
        for (int[] coord: shape.getCoords()) {
            int x = coord[0];
            int y = coord[1];
            if (y < 0 || y >= field.length || x < 0 || x >= field[0].length) return true;
            if (field[y][x] != 0) return true;
        }
        return false;
    }

    void dissolve(Shape shape) {
        for (int[] coord: shape.getCoords()) {
            int x = coord[0];
            int y = coord[1];
            field[y][x] = shape.type;
        }
    }

    int[] checkLines() {
        var eliminatedIndexes = new ArrayList<Integer>();
        for (int y = 0; y < field.length; y++) {
            if (Arrays.stream(field[y]).allMatch(x -> x != 0)) {
                eliminatedIndexes.add(y);
            }
        }
        return eliminatedIndexes.stream().mapToInt(i -> i).toArray();
    }

    void eliminateRows(int[] indexes) {
        for (int y: indexes) {
            for (int i = y; i > 0; i--) {
                field[i] = field[i - 1].clone();
            }
            Arrays.fill(field[0], 0);
        }
    }

    ArrayList<int[]> getCoords() {
        ArrayList<int[]> coords = new ArrayList<>();
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[0].length; x++) {
                if (field[y][x] != 0) {
                    coords.add(new int[]{x, y, field[y][x]});
                }
            }
        }
        return coords;
    }
}
