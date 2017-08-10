package cn.jesse.armyknife.exception;

/**
 * 非法操作运行时异常
 * Created by jesse on 10/08/2017.
 */

public class UnsupportedOperationException extends RuntimeException {
    public UnsupportedOperationException() {
        super();
    }

    public UnsupportedOperationException(String message) {
        super(message);
    }

    public UnsupportedOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedOperationException(Throwable cause) {
        super(cause);
    }
}
