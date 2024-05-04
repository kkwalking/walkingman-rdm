package com.kelton.walkingmanrdm.ui.scheme;

import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * @Author zhouzekun
 * @Date 2024/5/2 16:36
 */
public class ColorScheme {
    public static void setColor(FontIcon icon) {
        FillTransition fillToRed = new FillTransition(Duration.millis(200), icon, Color.BLACK, Color.RED);
        FillTransition fillToBlack = new FillTransition(Duration.millis(200), icon, Color.RED, Color.BLACK);

        // 鼠标移入时变红色
        icon.setOnMouseEntered(event -> fillToRed.playFromStart());

        // 鼠标移出时恢复黑色
        icon.setOnMouseExited(event -> fillToBlack.playFromStart());
    }
}
