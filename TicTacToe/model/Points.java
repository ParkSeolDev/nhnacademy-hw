package TicTacToe.model;

import java.util.ArrayList;
import java.util.List;

import TicTacToe.common.exception.InvalidCountException;

public final class Points {
    private static final int POINTS_COUNT = 9;
    private static final int ROW_SIZE = 3;
    private static final int NO_SCORE = 0;

    private List<Point> points = new ArrayList<>(POINTS_COUNT);

    private Points(final List<Point> points) {
        validate(points);
        this.points = points;
    }

    private void validate(final List<Point> points) {
        if (points.size() != POINTS_COUNT)
            throw new InvalidCountException();
    }

    public static Points pointsFactory() {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < POINTS_COUNT; i++) {
            Point initPoint = Point.NO;
            points.add(initPoint);
        }

        return new Points(points);
    }

    public void setPoint(final int index, final Point point) {
        points.set(index, point);
    }

    public List<Point> points() {
        return this.points;
    }

    public int winner() {
        List<Integer> comparisonList = new ArrayList<>();
        for (int j = 0; j < 7; j = j + ROW_SIZE) {
            comparisonList.clear();
            for (int i = j; i < j + ROW_SIZE; i++) {
                comparisonList.add(Point.findPoint(points.get(i).point()).point());
            }
            if (compare(comparisonList)) {
                return Point.findPoint(points.get(j).point()).point();
            }
        }

        for (int j = 0; j < ROW_SIZE; j++) {
            comparisonList.clear();
            for (int i = j; i < j + 7; i = i + ROW_SIZE) {
                comparisonList.add(Point.findPoint(points.get(i).point()).point());
            }
            if (compare(comparisonList)) {
                return Point.findPoint(points.get(j).point()).point();
            }
        }
        
        comparisonList.clear();
        for (int i = 0; i < POINTS_COUNT; i = i + 4) {
            comparisonList.add(Point.findPoint(points.get(i).point()).point());
        }
        if (compare(comparisonList)) {
            return Point.findPoint(points.get(0).point()).point();
        }
        
        comparisonList.clear();
        for (int i = 2; i < 7; i = i + 2) {
            comparisonList.add(Point.findPoint(points.get(i).point()).point());
        }
        if (compare(comparisonList)) {
            return Point.findPoint(points.get(2).point()).point();
        }

        return NO_SCORE;
    }

    private boolean compare(List<Integer> line) {
        if (line.get(0) == line.get(1) && line.get(1) == line.get(2)) {
            return true;
        }

        return false;
    }

    public boolean endGame() {
        return points.stream().allMatch(num -> num.point() != 0);
    }
}
