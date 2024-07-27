package util;

import java.util.Random;

public class RandomUnitGenerator {
    private static final Random RANDOM = new Random();

    public static Integer generate(int start, int end) {
        return RANDOM.nextInt(start, end);
    }
}
