package cn.jesse.armyknife;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.jesse.armyknife.exception.UnsupportedOperationException;

/**
 * MD5相关工具
 * Created by jesse on 18/07/2017.
 */

public class MD5Util {
    private static final String TAG = MD5Util.class.getSimpleName();

    private MD5Util() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取字符串MD5
     *
     * @param str 待加密字符串
     * @return 加密后md5
     */
    public static String getMD5(String str) {
        MessageDigest md;
        String md5 = "";
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            md5 = new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return md5;
    }

    /**
     * 获取文件MD5
     *
     * @param filePath 文件路径
     * @return 文件MD5
     */
    public static String getFileMD5(String filePath) {
        String fileMD5 = "";
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            BigInteger bigInt = new BigInteger(1, md.digest());
            fileMD5 = bigInt.toString(16);
        } catch (NoSuchAlgorithmException |
                IOException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            IOUtil.close(fis);
        }
        return fileMD5;
    }

    /**
     * 校验文件的md5是否匹配
     *
     * @param filePath 文件路径
     * @param md5      预匹配的md5
     * @return 是否匹配
     */
    public static boolean compareFileMD5(String filePath, String md5) {
        boolean isMatch = false;
        String fileMD5 = getFileMD5(filePath);

        if (!TextUtils.isEmpty(fileMD5) && !TextUtils.isEmpty(md5) && fileMD5.equals(md5)) {
            isMatch = true;
        }
        return isMatch;
    }

    /**
     * 校验字符串的md5是否匹配
     *
     * @param str 字符串
     * @param md5 预匹配的md5
     * @return 是否匹配
     */
    public static boolean compareMD5(String str, String md5) {
        boolean isMatch = false;
        String strMd5 = getMD5(str);

        if (!TextUtils.isEmpty(strMd5) && !TextUtils.isEmpty(md5) && strMd5.equals(md5)) {
            isMatch = true;
        }
        return isMatch;
    }
}
