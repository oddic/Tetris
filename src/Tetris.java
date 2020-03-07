public class Tetris {
    public static void main(String[] args) {
        Game game = new Game();
        game.view = new View(game);
    }

    public static String get2DArrayPrint(int[][] matrix) {
        StringBuilder output = new StringBuilder();
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                output.append(anInt).append("\t");
            }
            output.append("\n");
        }
        return output.toString();
    }
}
