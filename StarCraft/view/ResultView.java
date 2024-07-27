package view;

import model.Iterator;
import model.PlayerUnits;

public class ResultView {
    public static void printUnits(PlayerUnits playerUnits) {
        Iterator iterator = playerUnits.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            System.out.println(index + " : " + iterator.next().toString());
            index++;
        }
    }

    public static void printResult(boolean isPlayerWin) {
        if (isPlayerWin) {
            System.out.println("승리했습니다.");
        } else {
            System.out.println("패배했습니다.");
        }
    }
}
