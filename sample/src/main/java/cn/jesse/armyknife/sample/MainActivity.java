package cn.jesse.armyknife.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cn.jesse.armyknife.AESUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            String key = AESUtil.generateKey(128, null);
            AESUtil.setTransformation("AES/CBC/PKCS5Padding");
            AESUtil.encryptFile(key, "/storage/emulated/0/Download/test.png", "/storage/emulated/0/Download/encrypt.png");
            AESUtil.decryptFile(key, "/storage/emulated/0/Download/encrypt.png", "storage/emulated/0/Download/decrypt.png");
        } catch (Exception e) {
            Log.e("AES", e.getMessage(), e);
        }
    }
}
