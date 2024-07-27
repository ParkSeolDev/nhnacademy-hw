package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static Integer inputRaces() {
        System.out.println("종족을 선택하세요.");
        System.out.println("1.테란, 2.프로토스, 3.저그");
        return SCANNER.nextInt();
    }

    public static List<Integer> inputAttack() {
        String[] inputs;
        do {
           inputs = SCANNER.nextLine().trim().split(" ");
       } while (inputs[0].equals(""));

        return toInts(inputs);
    }

    private static List<Integer> toInts(String[] inputs) {
        List<Integer> inputIntegers = new ArrayList<>();
        for (String input : inputs) {
            inputIntegers.add(Integer.parseInt(input));
        }
        return inputIntegers;
    }
}
