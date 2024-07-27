package TicTacToe.common.util;

import java.util.Random;

public final class RandomPointGenerator implements PointGenerator {
	private static final Random RANDOM = new Random();

	@Override
    public Integer generate() {
        return RANDOM.nextInt(9);
	}
}