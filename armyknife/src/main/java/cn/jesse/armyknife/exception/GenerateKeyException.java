package cn.jesse.armyknife.exception;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * 加密算法创建key异常
 * Created by jesse on 16/07/2017.
 */

public class GenerateKeyException extends Exception {
    public GenerateKeyException() {
        super();
    }

    public GenerateKeyException(String message) {
        super(message);
    }

    public GenerateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenerateKeyException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected GenerateKeyException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
