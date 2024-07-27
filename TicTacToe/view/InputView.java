package TicTacToe.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Map<List<String>, Integer> dictionary = new HashMap<>() {
        {
            put(List.of("0", "0"), 0);
            put(List.of("0", "1"), 1);
            put(List.of("0", "2"), 2);
            put(List.of("1", "0"), 3);
            put(List.of("1", "1"), 4);
            put(List.of("1", "2"), 5);
            put(List.of("2", "0"), 6);
            put(List.of("2", "1"), 7);
            put(List.of("2", "2"), 8);
        }
    };
    
    public static Integer inputUserPoint() {
        System.out.print("사용자 턴 (x y) : ");

        return dictionary.get(List.of(SCANNER.nextLine().trim().split(" ")));
    }
}
