package com.kelton.walkingmanrdm.common.util;

import com.kelton.walkingmanrdm.common.Constants;

import java.io.*;
import java.util.Properties;

public class PropertiesUtils {
    private final String propertiesFilePath;
    private Properties properties;

    public static final PropertiesUtils DEFAULT = new PropertiesUtils(Constants.PROPERTY_FILE);

    private PropertiesUtils(String propertiesFilePath) {
        this.propertiesFilePath = propertiesFilePath;
        this.loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = new FileInputStream(this.propertiesFilePath)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException ex) {
            try {
                // 尝试创建 properties 文件
                File propertiesFile = new File(this.propertiesFilePath);
                propertiesFile.getParentFile().mkdirs();  // 创建父目录
                propertiesFile.createNewFile();  // 创建文件
                properties = new Properties();   // Create empty properties object
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        this.properties.setProperty(key, value);
        this.saveProperties();
    }


    // 将修改持久化到文件的部分
    private void saveProperties() {
        try {
            FileOutputStream output = new FileOutputStream(propertiesFilePath);
            properties.store(output, "Properties updated at " + System.currentTimeMillis());
            output.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        PropertiesUtils.DEFAULT.setProperty("age", "18");
        String dataInit = PropertiesUtils.DEFAULT.getProperty(Constants.DATA_INIT);
        System.out.println(Boolean.valueOf(dataInit));
    }

}