package TicTacToe.common.exception;

public final class InvalidRangeArgumentException extends RuntimeException {
    public InvalidRangeArgumentException() {
        super("0, 1, 2 중에 입력해주세요.");
    }
}
