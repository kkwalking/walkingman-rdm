package com.kelton.walkingmanrdm.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Author zhouzekun
 * @Date 2024/5/3 21:14
 */
public class JsonUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * 使用Gson格式化JSON字符串。
     *
     * @param inputJson 输入的JSON字符串
     * @return 格式化后的JSON字符串
     */
    public static String formatJson(String inputJson) {
        Object json = gson.fromJson(inputJson, Object.class);
        return gson.toJson(json);
    }


    public static String toJson(Object obj) {
        String json = gson.toJson(obj);
        return formatJson(json);
    }

}
