package TicTacToe.model;

public enum Point {
    NO(0),
    USER(1),
    COM(2);

    private final Integer point;

    Point(final Integer point) {
        this.point = point;
    }

    public Integer point() {
        return point;
    }

    public static Point findPoint(final int point) {
        if (point == 1) return Point.USER;
        if (point == 2)
            return Point.COM;
        return Point.NO;
    }
}
