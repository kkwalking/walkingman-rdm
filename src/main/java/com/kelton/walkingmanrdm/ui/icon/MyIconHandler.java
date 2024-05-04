package com.kelton.walkingmanrdm.ui.icon;

import org.kordamp.ikonli.AbstractIkonHandler;
import org.kordamp.ikonli.Ikon;

import java.io.InputStream;
import java.net.URL;

/**
 * @Author zhouzekun
 * @Date 2024/4/29 14:17
 */
public class MyIconHandler extends AbstractIkonHandler {
    @Override
    public boolean supports(String s) {
        return s!=null && s.startsWith("myicon-");
    }

    @Override
    public Ikon resolve(String description) {
        return RedisDataStructIcon.getByDescription(description);
    }

    @Override
    public URL getFontResource() {
        return MyIconHandler.class.getResource("/font/RedisKey.ttf");
    }

    @Override
    public InputStream getFontResourceAsStream() {
        return MyIconHandler.class.getResourceAsStream("/font/RedisKey.ttf");
    }

    @Override
    public String getFontFamily() {
        return "RedisKey";
    }
}
