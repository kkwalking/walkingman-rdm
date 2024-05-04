package com.kelton.walkingmanrdm.ui.demo.icon;

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

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        StackPane root = new StackPane();

        FontIcon icon = new FontIcon(AntDesignIconsFilled.EDIT); // 使用的 ANCHOR 图标作为示例
        icon.setIconSize(50); // 设置图标大小
        icon.setIconColor(Color.BLACK); // 初始图标颜色为黑色

        ColorAdjust colorAdjust = new ColorAdjust();
        icon.setEffect(colorAdjust);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(colorAdjust.hueProperty(), 0)),
                new KeyFrame(Duration.millis(200), new KeyValue(colorAdjust.hueProperty(), 0.17))
        );

        icon.setOnMouseEntered(e-> timeline.playFromStart());

        timeline.setOnFinished(e -> {
            icon.setEffect(null);
            icon.setIconColor(Color.RED);
        });

        Timeline outTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(colorAdjust.hueProperty(), 0)),
                new KeyFrame(Duration.millis(200), new KeyValue(colorAdjust.hueProperty(), 0.17))
        );

        icon.setOnMouseExited(e-> outTimeline.playFromStart());
        outTimeline.setOnFinished(e -> {
            icon.setEffect(null);
            icon.setIconColor(Color.BLACK);
        });


        root.getChildren().add(icon);

        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}