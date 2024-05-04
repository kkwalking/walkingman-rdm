package com.kelton.walkingmanrdm.ui.icon;

import org.kordamp.ikonli.AbstractIkonHandler;
import org.kordamp.ikonli.Ikon;

import java.io.InputStream;
import java.net.URL;

/**
 * @Author kelton
 * @Date 2024/4/29 21:50
 * @Version 1.0
 */
public class MyLevelHandler extends AbstractIkonHandler {
    @Override
    public boolean supports(String s) {
        return s!=null && s.startsWith("my-level-");
    }

    @Override
    public Ikon resolve(String s) {
        return MsgIcon.getByDescription(s);
    }

    @Override
    public URL getFontResource() {
        return this.getClass().getResource("/font/MyLevel.ttf");
    }

    @Override
    public InputStream getFontResourceAsStream() {
        return this.getClass().getResourceAsStream("/font/MyLevel.ttf");
    }

    @Override
    public String getFontFamily() {
        return "MyLevel";
    }
}
