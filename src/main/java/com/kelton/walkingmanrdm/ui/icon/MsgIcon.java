package com.kelton.walkingmanrdm.ui.icon;

import org.kordamp.ikonli.Ikon;

/**
 * @Author kelton
 * @Date 2024/4/29 22:46
 * @Version 1.0
 */
public enum MsgIcon implements Ikon {

    INFO("my-level-info", '\ue901'),
    SUCCESS("my-level-success", '\ue902'),
    WARN("my-level-warn", '\ue903'),
    ERROR("my-level-error", '\ue900'),
    ;



    private final String description;

    private final int code;

    MsgIcon(String description, int code) {
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


    public static MsgIcon getByDescription(String description) {
        for (MsgIcon icon : values()) {
            if (icon.description.equals(description)) {
                return icon;
            }
        }
        throw new RuntimeException("icon not found by description");
    }
}
