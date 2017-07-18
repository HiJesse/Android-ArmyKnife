package cn.jesse.armyknife.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import cn.jesse.armyknife.AESUtil;
import cn.jesse.armyknife.ByteUtil;
import cn.jesse.armyknife.MD5Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean isStringMatch = MD5Util.compareMD5("a123456", "dc483e80a7a0bd9ef71d8cf973673924");
        Log.i("MD5", "is string match " + isStringMatch);
        boolean isMatch = MD5Util.compareFileMD5("/storage/emulated/0/Download/tmp.html",
                "c429fa4fdfb1c2ed9742e1ea6c1225ee");
        Log.i("MD5", "is file match " + isMatch);
    }

    private void aes() {
        try {
            String key = "0D0D0D0A0D0D0D0A0000000000000000";
            AESUtil.setTransformation("AES/CBC/PKCS7Padding");
            AESUtil.setIvParameter(ByteUtil.hexStringToBytes("26247500045987180000000000000000"));

            byte[] encrypted = AESUtil.encrypt(ByteUtil.hexStringToBytes(key), "origin".getBytes("utf-8"));
            byte[] decrypted = AESUtil.decrypt(ByteUtil.hexStringToBytes(key), encrypted);
            String decryptedString = new String(decrypted, "utf-8");

            String test = AESUtil.encrypt(ByteUtil.hexStringToBytes(key), "test");
            String decryptedTest = AESUtil.decrypt(ByteUtil.hexStringToBytes(key), test);

            AESUtil.encrypt(ByteUtil.hexStringToBytes(key), "/storage/emulated/0/Download/test.png", "/storage/emulated/0/Download/encrypt.png");
            AESUtil.decrypt(ByteUtil.hexStringToBytes(key), "/storage/emulated/0/Download/encrypt.png", "storage/emulated/0/Download/decrypt.png");

            AESUtil.decrypt(ByteUtil.hexStringToBytes(key), "/storage/emulated/0/Download/iosen", "storage/emulated/0/Download/decrypt.zip");
        } catch (Exception e) {
            Log.e("AES", e.getMessage(), e);
        }
    }
}
