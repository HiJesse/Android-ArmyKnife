package cn.jesse.armyknife;

import android.util.Base64;

/**
 * Base64转换工具
 * Created by jesse on 16/07/2017.
 */

public class Base64Util {

    private Base64Util() {
        // unused
    }

    /**
     * 将字符串decode为byte[]
     * @param base64 字符串
     * @return byte[]
     */
    public static byte[] decode(String base64) {
        return decode(base64, Base64.DEFAULT);
    }

    /**
     * 将byte[] encode为String
     * @param bytes []
     * @return String
     */
    public static String encode(byte[] bytes) {
        return encode(bytes, Base64.DEFAULT);
    }

    /**
     * 根据flag将字符串decode为byte[]
     * @param base64 字符串
     * @param flag flag
     * @return byte[]
     */
    public static byte[] decode(String base64, int flag) {
        return Base64.decode(base64, flag);
    }

    /**
     * 根据flag将byte[] encode为String
     * @param bytes byte[]
     * @param flag flag
     * @return String
     */
    public static String encode(byte[] bytes, int flag) {
        return new String(Base64.encode(bytes, flag));
    }
}
