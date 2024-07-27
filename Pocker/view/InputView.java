package view;

import java.util.Scanner;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static Integer inputPlayersCount() {
        System.out.print("플레이어의 수를 입력하세요. : ");
        return SCANNER.nextInt();
    }

    public static int inputSeedMoney() {
        System.out.println("시드 머니를 입력하세요. : ");
        return SCANNER.nextInt();    
    }

    public static int inputBettingMoney() {
        System.out.println("배팅 머니를 입력하세요. : ");
        return SCANNER.nextInt();  
    }
}
