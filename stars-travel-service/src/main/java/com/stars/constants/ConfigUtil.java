package com.stars.constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件
 * Created by guo on 16/1/22.
 */
public class ConfigUtil {

    private static Properties properties = null;
    private static ConfigUtil instance = new ConfigUtil();

    static {
        properties = new Properties();
        InputStream inputStream = ConfigUtil.class.getClassLoader().getResourceAsStream("app.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ConfigUtil() {

    }

    public static ConfigUtil getInstance() {
        return instance;
    }

    public Properties getProperties() {
        return properties;
    }

}
