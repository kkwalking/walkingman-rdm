package com.kelton.walkingmanrdm.ui.icon;

import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 *
 * 当鼠标hover时提供迅速渐变至目标颜色的icon
 * @Author zhouzekun
 * @Date 2024/5/2 16:42
 */
public class ColorIcon extends FontIcon {
    public ColorIcon(Color from, Color to) {
        super();
        FillTransition fillToRed = new FillTransition(Duration.millis(200), this, from, to);
        FillTransition fillToBlack = new FillTransition(Duration.millis(200), this, to, from);
        // 鼠标移入时渐变至目标色
        this.setOnMouseEntered(event -> fillToRed.playFromStart());
        // 鼠标移出时恢复原始色
        this.setOnMouseExited(event -> fillToBlack.playFromStart());
    }
}
