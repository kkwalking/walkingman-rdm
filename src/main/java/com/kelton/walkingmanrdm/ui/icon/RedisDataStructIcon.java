package com.kelton.walkingmanrdm.ui.icon;


import org.kordamp.ikonli.Ikon;

/**
 * @Author zhouzekun
 * @Date 2024/4/29 14:12
 */
public enum RedisDataStructIcon implements Ikon {


    REDIS_FOLDER("myicon-folder", '\ue900'),
    REDIS_STRING("myicon-s", '\ue904'),
    REDIS_LIST("myicon-l", '\ue901'),
    REDIS_SET("myicon-e", '\ue902'),
    REDIS_HASH("myicon-h", '\ue903'),
    REDIS_ZSET("myicon-z", '\ue905'),


    ;

    private String description;

    private int code;

    RedisDataStructIcon(String description, int code) {
        this.description = description;
        this.code = code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getCode() {
        return code;
    }


    public static RedisDataStructIcon getByDescription(String description) {
        for (RedisDataStructIcon icon : values()) {
            if (icon.description.equals(description)) {
                return icon;
            }
        }
        throw new RuntimeException("icon not found by description");
    }
}
