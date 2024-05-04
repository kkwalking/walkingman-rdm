package com.kelton.walkingmanrdm.ui.demo.icon;

import com.kelton.walkingmanrdm.ui.icon.ColorIcon;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsFilled;
import org.kordamp.ikonli.javafx.FontIcon;

public class IconColorTransition extends Application {

    @Override
    public void start(Stage primaryStage) {

        ColorIcon icon = new ColorIcon(Color.BLACK, Color.RED);
        icon.setIconCode(AntDesignIconsFilled.EDIT);
        icon.setIconSize(30); // 设置图标大小
        icon.setIconColor(Color.BLACK); // 初始图标颜色为黑色

//        FillTransition fillToRed = new FillTransition(Duration.millis(200), icon, Color.BLACK, Color.valueOf("#72b6ad"));
//        FillTransition fillToBlack = new FillTransition(Duration.millis(200), icon, Color.valueOf("#72b6ad"), Color.BLACK);
//
//        // 鼠标移入时变红色
//        icon.setOnMouseEntered(event -> fillToRed.playFromStart());
//
//        // 鼠标移出时恢复黑色
//        icon.setOnMouseExited(event -> fillToBlack.playFromStart());

        StackPane root = new StackPane(icon);
        Scene scene = new Scene(root, 300, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}