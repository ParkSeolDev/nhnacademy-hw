package TicTacToe.common.exception;

public final class InvalidCountException extends RuntimeException {
    public InvalidCountException() {
        super("좌표 개수 틀림");
    }
}
