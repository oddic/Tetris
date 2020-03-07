import java.util.ArrayList;
import java.util.Random;

public class Shape {
    static private final Random random = new Random();
    static private final int[][][] SHAPES = { // (0, 0) is the block at (4, 1), origin top left
            {{-1, -1}, {0, -1}, {1, -1}, {2, -1}}, // I
            {{0, -1}, {0, 0}, {1, 0}, {1, -1}}, // O
            {{-1, 0}, {0, 0}, {1, 0}, {0, -1}}, // T
            {{-1, -1}, {0, -1}, {0, 0}, {1, 0}}, // Z
            {{-1, 0}, {0, 0}, {0, -1}, {1, -1}}, // S
            {{-1, -1}, {-1, 0}, {0, 0}, {1, 0}}, // L
            {{-1, 0}, {0, 0}, {1, 0}, {1, -1}} // J
    };

    private final int[][] blockCoords = new int[4][2];
    final int type; // 1 - 7
    int x = 4, y = 1;

    public Shape() {
        type = random.nextInt(7) + 1;
        for (int i = 0; i < 4; i++) {
            blockCoords[i] = SHAPES[type - 1][i].clone();
        }
    }

    void rotate() {
        for (int[] coord: blockCoords) {
            int x = coord[0];
            int y = coord[1];
            coord[0] = -y;
            coord[1] = x - (type < 3 ? 1 : 0);
        }
    }

    ArrayList<int[]> getCoords() {
        var realCoords = new ArrayList<int[]>();
        for (int[] coord: blockCoords) {
            realCoords.add(new int[]{coord[0] + x, coord[1] + y, type});
        }
        return realCoords;
    }
}
