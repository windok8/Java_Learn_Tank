package com.wdk.tank;

import java.io.IOException;
import java.util.Properties;

/**
 * @author : Windok
 * @date: 2023-10-09
 * @Description: 用于管理配置文件
 * @version: 1.0
 */
public class PropertyMgr {

    static Properties props = new Properties();

    static {
        try {
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object get(String key) {
        if (props == null) {
            return null;
        }
        return props.get(key);
    }

}
