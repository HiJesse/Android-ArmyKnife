package cn.jesse.armyknife;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.jesse.armyknife.exception.DecryptException;
import cn.jesse.armyknife.exception.EncryptException;
import cn.jesse.armyknife.exception.GenerateKeyException;

/**
 * AES相关工具
 * Created by jesse on 16/07/2017.
 */

public class AESUtil {
    private static final String TAG = AESUtil.class.getSimpleName();
    private static final String ALGORITHM = "AES";
    private static String transformation = "AES/CFB8/NoPadding";
    private static final int CACHE_SIZE = 1024;
    private static final byte[] IV_PARAMETER = {0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1, 6, 8, 0xC, 0xD, 91};
    private static IvParameterSpec ivParameterSpec = new IvParameterSpec(IV_PARAMETER);

    private AESUtil() {
        // unused
    }

    /**
     * 设置自定义ivParameter
     * @param ivParameter byte[]向量
     */
    public static void setIvParameter(byte[] ivParameter) {
        ivParameterSpec = new IvParameterSpec(ivParameter);
    }

    public static void setTransformation(String transform) {
        transformation = transform;
    }

    /**
     * 根据长度和种子创建AES key
     * @param keyLength key长度
     * @param seed 种子
     * @return 字符串key
     * @throws GenerateKeyException
     */
    public static String generateKey(int keyLength, String seed) throws GenerateKeyException{
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new GenerateKeyException(e.getMessage(), e.getCause());
        }
        SecureRandom secureRandom;
        if (seed != null && !"".equals(seed)) {
            secureRandom = new SecureRandom(seed.getBytes());
        } else {
            secureRandom = new SecureRandom();
        }
        keyGenerator.init(keyLength, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64Util.encode(secretKey.getEncoded());
    }


    /**
     * 根据key加密byte数组
     * @param key 加密所需的key
     * @param data 待加密的byte[]
     * @return 加密之后的byte[]
     * @throws EncryptException
     */
    public static byte[] encrypt(String key, byte[] data) throws EncryptException{
        Key k = new SecretKeySpec(Base64Util.decode(key), ALGORITHM);
        byte[] raw = k.getEncoded();
        byte[] encrypted;
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            encrypted = cipher.doFinal(data);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException |
                BadPaddingException |
                IllegalBlockSizeException |
                InvalidKeyException |
                InvalidAlgorithmParameterException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new EncryptException(e.getMessage(), e.getCause());
        }

        return encrypted;
    }

    /**
     * 根据key解密byte数组
     * @param key 解密所需的key
     * @param data 待解密数据
     * @return 解密后的byte[]
     * @throws DecryptException
     */
    public static byte[] decrypt(String key, byte[] data) throws DecryptException {
        Key k = new SecretKeySpec(Base64Util.decode(key), ALGORITHM);
        byte[] raw = k.getEncoded();
        byte[] decrypted;
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            decrypted = cipher.doFinal(data);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException |
                BadPaddingException |
                IllegalBlockSizeException |
                InvalidKeyException |
                InvalidAlgorithmParameterException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new DecryptException(e.getMessage(), e.getCause());
        }

        return decrypted;
    }

    /**
     * 根据key 对文件进行加密
     * @param key 加密key
     * @param sourceFilePath 待加密文件路径
     * @param destFilePath 加密成功文件路径
     * @throws EncryptException
     */
    public static void encryptFile(String key, String sourceFilePath, String destFilePath) throws EncryptException{
        File sourceFile = new File(sourceFilePath);
        File destFile = new File(destFilePath);

        if (!sourceFile.exists() || !sourceFile.isFile()) {
            throw new EncryptException("sourceFile is not exist | file");
        }

        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }

        InputStream in = null;
        OutputStream out = null;
        CipherInputStream cin = null;
        try {
            destFile.createNewFile();
            in = new FileInputStream(sourceFile);
            out = new FileOutputStream(destFile);
            Key k = new SecretKeySpec(Base64Util.decode(key), ALGORITHM);
            byte[] raw = k.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            cin = new CipherInputStream(in, cipher);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead;
            while ((nRead = cin.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
        } catch (IOException |
                NoSuchPaddingException |
                NoSuchAlgorithmException |
                InvalidKeyException |
                InvalidAlgorithmParameterException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new EncryptException(e.getMessage(), e.getCause());
        } finally {
            IOUtil.close(out);
            IOUtil.close(cin);
            IOUtil.close(in);
        }
    }

    /**
     * 根据key 对文件进行解密
     * @param key 解密key
     * @param sourceFilePath 待解密文件路径
     * @param destFilePath 解密成功文件路径
     * @throws DecryptException
     */
    public static void decryptFile(String key, String sourceFilePath, String destFilePath) throws DecryptException {
        File sourceFile = new File(sourceFilePath);
        File destFile = new File(destFilePath);

        if (!sourceFile.exists() || !sourceFile.isFile()) {
            throw new DecryptException("sourceFile is not exist | file");
        }

        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }

        FileInputStream in = null;
        FileOutputStream out = null;
        CipherOutputStream cOut = null;

        try {
            destFile.createNewFile();
            in = new FileInputStream(sourceFile);
            out = new FileOutputStream(destFile);
            Key k = new SecretKeySpec(Base64Util.decode(key), ALGORITHM);
            byte[] raw = k.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            cOut = new CipherOutputStream(out, cipher);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead;
            while ((nRead = in.read(cache)) != -1) {
                cOut.write(cache, 0, nRead);
                cOut.flush();
            }
        } catch (IOException |
                NoSuchPaddingException |
                NoSuchAlgorithmException |
                InvalidKeyException |
                InvalidAlgorithmParameterException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new DecryptException(e.getMessage(), e.getCause());
        } finally {
            IOUtil.close(cOut);
            IOUtil.close(out);
            IOUtil.close(in);
        }
    }

}
