package TicTacToe.view;

import TicTacToe.model.Point;
import TicTacToe.model.Points;

public final class ResultView {
    private static final int NO = 0;
    private static final int COM = 2;
    private static final int ROW_SIZE = 3;

    public static void printMatrix(final Points points) {
        for (int i = 0; i < points.points().size(); i++) {
            drawPoints(i, points.points().get(i));
        }
    }

    private static void drawPoints(final int index, final Point point) {
        System.out.print(drawMatrix(point));
        if (index % ROW_SIZE == 2)
            drawLine();
    }

    private static void drawLine() {
        System.out.println();
        System.out.println("---|---|---");
    }

    private static String drawMatrix(final Point point) {
        if (point.point() == NO) {
            return "   |";
        } else if (point.point() == COM) {
            return " X |";
        }
        return " O |";
    }
}