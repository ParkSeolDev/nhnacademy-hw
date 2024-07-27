package model;

import java.util.List;
import java.util.Objects;

public enum WinnerCardsArray {
    RoyalStraightFlush(List.of(10, 11, 12, 13, 14), true),
    BackStraightFlush(List.of(14, 2, 3, 4, 5), true),
    None(List.of(0, 0, 0, 0, 0), false);

    private final List<Integer> winNums;
    private final Boolean isSameShape;

    WinnerCardsArray(final List<Integer> winNums, final Boolean isSameShape) {
        this.winNums = winNums;
        this.isSameShape = isSameShape;
    }

    public static WinnerCardsArray findWinnerCardArray(final List<Integer> winNums, final Boolean isSameShape) {
        for (WinnerCardsArray winner : WinnerCardsArray.values()) {
            if (Objects.equals(winner.winNums, winNums) && winner.isSameShape.equals(isSameShape)) {
                return winner;
            }
        }
        return WinnerCardsArray.None;
    }
}
