import java.util.ArrayList;
import java.util.stream.Collectors;

public class Game {
    View view;
    Board board;
    Shape currentShape;
    Shape nextShape;

    public Game() {
        board = new Board();
        currentShape = new Shape();
        nextShape = new Shape();
    }

    void step() {
        currentShape.y += 1;
        if (board.collidesWith(currentShape)) {
            currentShape.y -= 1;
            board.dissolve(currentShape);
            int[] rows = board.checkLines();
            if (rows.length > 0) {
                view.performLineAnimation(rows, () -> {
                    board.eliminateRows(rows);
                    addShape();
                });
            } else addShape();
        }
    }

//    void stepToBottom() {
//        while (!step()) {}
//    }

    void addShape() {
        currentShape = nextShape;
        nextShape = new Shape();
        if (board.collidesWith(currentShape)) {
            performGameOver();
        }
    }

    void performGameOver() {
        board = new Board();
        currentShape = new Shape();
    }

    void moveDown() {
        currentShape.y += 1;
        if (board.collidesWith(currentShape)) {
            currentShape.y -= 1;
        }
    }

    void moveLeft() {
        currentShape.x -= 1;
        if (board.collidesWith(currentShape)) {
            currentShape.x += 1;
        }
    }

    void moveRight() {
        currentShape.x += 1;
        if (board.collidesWith(currentShape)) {
            currentShape.x -= 1;
        }
    }

    void rotate() {
        currentShape.rotate();
        if (board.collidesWith(currentShape)) currentShape.x += 1;
        if (board.collidesWith(currentShape)) currentShape.x -= 2;
        if (board.collidesWith(currentShape)) {
            currentShape.x += 1;
            for (int i = 0; i < 3; i++) currentShape.rotate();
        }
    }


    ArrayList<int[]> getAllBlockCoords() {
        ArrayList<int[]> coords = board.getCoords();
        coords.addAll(currentShape.getCoords());
        coords.addAll(nextShape.getCoords().stream().map(e -> new int[]{e[0] + 8, e[1] + 1, e[2]}).collect(Collectors.toList()));
        return coords;
    }
}
