package cn.jesse.armyknife.exception;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * 加密异常信息
 * Created by jesse on 16/07/2017.
 */

public class EncryptException extends Exception {

    public EncryptException() {
        super();
    }

    public EncryptException(String message) {
        super(message);
    }

    public EncryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncryptException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected EncryptException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
