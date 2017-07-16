package cn.jesse.armyknife.exception;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * 解密异常信息
 * Created by jesse on 16/07/2017.
 */

public class DecryptException extends Exception {
    public DecryptException() {
        super();
    }

    public DecryptException(String message) {
        super(message);
    }

    public DecryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecryptException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected DecryptException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
