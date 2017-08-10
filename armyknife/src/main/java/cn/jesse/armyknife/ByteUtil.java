package cn.jesse.armyknife;

import android.text.TextUtils;

import cn.jesse.armyknife.exception.UnsupportedOperationException;

/**
 * byte相关工具
 * Created by jesse on 16/07/2017.
 */

public class ByteUtil {
    private ByteUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 转换byte[] 为 hex String
     *
     * @param src source byte[]
     * @return String
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 转换hex String 为 byte[]
     *
     * @param hexString String
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (TextUtils.isEmpty(hexString)) {
            return new byte[0];
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | (charToByte(hexChars[pos + 1]) & 0xff));
        }
        return d;
    }

    /**
     * char 转化为byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
