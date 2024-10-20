package view;

import java.util.Scanner;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static Integer inputPlayersCount() {
        System.out.print("플레이어의 수를 입력하세요. : ");
        return SCANNER.nextInt();
    }

    public static Integer inputCardShape() {
        System.out.println("카드 모양을 숫자로 입력하세요. 1:♣ 2:♦ 3:♥ 4:♠");
        return SCANNER.nextInt();
    }
}
