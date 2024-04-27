package com.kelton.walkingmanrdm.ui.component;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.security.Key;

public class NotificationManager {

    private static NotificationManager INSTANT;

    private Pane notificationContainer; // 通用的Pane容器

    public static NotificationManager getInstance() {
        if (INSTANT == null) {
            throw new RuntimeException("NotificationManager not init");
        }
        return INSTANT;
    }

    private NotificationManager(Pane notificationContainer) {
        this.notificationContainer = notificationContainer;
    }

    public static void register(Pane root) {
        INSTANT = new NotificationManager(root);
    }



    public static void showNotification(String message) {
        showNotification(message,Type.INFO);
    }



    public static void showNotification(String message, Type type) {
        Pane notification = createNotification(message, type);
        INSTANT.notificationContainer.getChildren().add(notification);
        setAnimation(notification);
    }

    private static Pane createNotification(String message, Type type) {

        // HBox for one notification item
        AnchorPane notificationItem = new AnchorPane();
        notificationItem.setStyle("-fx-background-color: WHITE;-fx-background-radius: 3px;");
        notificationItem.setPrefSize(300, 40);
        notificationItem.setMaxWidth(300);
        notificationItem.setMaxHeight(40);
        notificationItem.setPadding(new Insets(5, 5, 5, 5));

        String d = "";
        String notificationColor = "";
        switch (type) {
            case INFO -> {
                d = "M512 85.333333c235.648 0 426.666667 191.018667 426.666667 426.666667s-191.018667 426.666667-426.666667 426.666667S85.333333 747.648 85.333333 512 276.352 85.333333 512 85.333333z m-74.965333 550.4L346.453333 545.152a42.666667 42.666667 0 1 0-60.330666 60.330667l120.704 120.704a42.666667 42.666667 0 0 0 60.330666 0l301.653334-301.696a42.666667 42.666667 0 1 0-60.288-60.330667l-271.530667 271.488z";
                notificationColor = "#67c23a";
            }
            case WARN -> {
                d = "M535.893333 972.288c-244.736 0-443.733333-198.997333-443.733333-443.733333s198.997333-443.733333 443.733333-443.733334 443.733333 198.997333 443.733334 443.733334-199.168 443.733333-443.733334 443.733333z m0-819.2c-207.018667 0-375.466667 168.448-375.466666 375.466667s168.448 375.466667 375.466666 375.466666 375.466667-168.448 375.466667-375.466666-168.448-375.466667-375.466667-375.466667zM535.893333 562.688c-18.773333 0-34.133333-15.36-34.133333-34.133333V319.146667c0-18.773333 15.36-34.133333 34.133333-34.133334s34.133333 15.36 34.133334 34.133334v209.237333c0 18.944-15.36 34.304-34.133334 34.304zM535.893333 670.208m-51.712 0a51.712 51.712 0 1 0 103.424 0 51.712 51.712 0 1 0-103.424 0Z";
                notificationColor = "#e6a23c";
            }
            case ERROR -> {
                d = "M512 0C229.376 0 0 229.376 0 512s229.376 512 512 512 512-229.376 512-512S794.624 0 512 0z m218.624 672.256c15.872 15.872 15.872 41.984 0 57.856-8.192 8.192-18.432 11.776-29.184 11.776s-20.992-4.096-29.184-11.776L512 569.856l-160.256 160.256c-8.192 8.192-18.432 11.776-29.184 11.776s-20.992-4.096-29.184-11.776c-15.872-15.872-15.872-41.984 0-57.856L454.144 512 293.376 351.744c-15.872-15.872-15.872-41.984 0-57.856 15.872-15.872 41.984-15.872 57.856 0L512 454.144l160.256-160.256c15.872-15.872 41.984-15.872 57.856 0 15.872 15.872 15.872 41.984 0 57.856L569.856 512l160.768 160.256z";
                notificationColor = "#f56c6c";
            }
        }
        StackPane iconPane = new SvgStackPane(20, 20, d, notificationColor);


        // Message label
        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 14px;");
        messageLabel.setAlignment(Pos.CENTER);

        AnchorPane.setLeftAnchor(messageLabel, 40.0); // Start after the icon pane
        AnchorPane.setRightAnchor(messageLabel, 40.0); // Leave space for the close button
        AnchorPane.setTopAnchor(messageLabel, 5.0);
        AnchorPane.setBottomAnchor(messageLabel, 5.0);


        Button closeButton = new Button("X");
        closeButton.setOnAction(e -> {
            Timeline fadeOutTimeline = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(notificationItem.opacityProperty(), 0)));
            fadeOutTimeline.setOnFinished(event -> INSTANT.notificationContainer.getChildren().remove(notificationItem));
            fadeOutTimeline.play();
        });
        closeButton.getStyleClass().add("button-close");

        // Add components to the AnchorPane
        notificationItem.getChildren().addAll(iconPane, messageLabel, closeButton);

        // Anchor iconPane to the left
        AnchorPane.setLeftAnchor(iconPane, 5.0);
        AnchorPane.setTopAnchor(iconPane, 5.0);

        // Anchor closeButton to the right
        AnchorPane.setRightAnchor(closeButton, 5.0);
        AnchorPane.setTopAnchor(closeButton, 5.0);
        notificationItem.getStylesheets().add(NotificationManager.INSTANT.getClass().getResource("/css/notification.css").toExternalForm());
        return notificationItem;
    }


    private static void setAnimation(Pane notificationBox) {
        // 获取通知容器Pane的高度
        double containerHeight = INSTANT.notificationContainer.getHeight();
        System.out.println(containerHeight);
        System.out.println(notificationBox.getHeight());

        // 设置VBox的位置（相对于notificationContainer）
        notificationBox.setTranslateY(-(containerHeight / 2));
        notificationBox.setOpacity(1);

        // 窗口可见区域内的动画
        KeyValue startKeyValue = new KeyValue(notificationBox.translateYProperty(), -(containerHeight / 2));
        KeyValue endKeyValue = new KeyValue(notificationBox.translateYProperty(), 25);
        KeyFrame startKeyFrame = new KeyFrame(Duration.ZERO, startKeyValue);
        KeyFrame endKeyFrame = new KeyFrame(Duration.seconds(0.8), endKeyValue);

        // 向下移动动画
        Timeline slideInTimeline = new Timeline(startKeyFrame, endKeyFrame);

        // 暂停动画
        PauseTransition stayOnScreen = new PauseTransition(Duration.seconds(2));


        // 想上移动动画
        Timeline slideOutTimeLine = new Timeline(new KeyFrame(Duration.seconds(0), new KeyValue(notificationBox.translateYProperty(), 25)),
                new KeyFrame(Duration.seconds(0.8), new KeyValue(notificationBox.translateYProperty(), -(containerHeight / 2)))
        );

        SequentialTransition sequentialTransition = new SequentialTransition(slideInTimeline, stayOnScreen, slideOutTimeLine);
        sequentialTransition.setOnFinished(event -> INSTANT.notificationContainer.getChildren().remove(notificationBox));
        sequentialTransition.play();
    }

    public enum Type {
        INFO,
        WARN,
        ERROR
    }
}