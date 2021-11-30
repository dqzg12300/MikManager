package com.mik.mikmanager.Common;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class ProperTies {
    //private static String configPath = getExternalStorageDirectory() + File.separator + "appConfig";
    private static String configPath = "appConfig";

    public static Properties getProperties(Context context) {
        Log.e("configPath", configPath);

        Properties urlProps;
        Properties props = new Properties();
        try {
            //方法一：通过activity中的context攻取setting.properties的FileInputStream
            //注意这地方的参数appConfig在eclipse中应该是appConfig.properties才对,但在studio中不用写后缀
            //InputStream in = c.getAssets().open("appConfig.properties");

            //props.load(context.getAssets().open(configName));

            //方法二：通过class获取setting.properties的FileInputStream
            //InputStream in = PropertiesUtill.class.getResourceAsStream("/assets/  setting.properties "));

            // 方法三
            props.load(context.openFileInput(configPath));
            // props.load(new FileInputStream(configPath));

        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        urlProps = props;
        return urlProps;
    }

    //保存配置文件
    public static String setProperties(Context context, String keyName, String keyValue) {
        Properties props = new Properties();
        try {
            props.load(context.openFileInput(configPath));
            props.setProperty(keyName, keyValue);
            // FileOutputStream out = context.getAssets().openFd(configPath).createOutputStream();
            FileOutputStream out = context.openFileOutput(configPath,Context.MODE_PRIVATE);
            // FileOutputStream out = new FileOutputStream(configPath);
            props.store(out, null);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("setPropertiesError", e.toString());
            return "修改配置文件失败!";
        }
        return "设置成功";
    }

}