package com.kelton.walkingmanrdm.ui.component;

import com.kelton.walkingmanrdm.ui.icon.MsgIcon;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

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

        FontIcon fontIcon = new FontIcon();
        switch (type) {
            case INFO -> {
                fontIcon.setIconCode(MsgIcon.SUCCESS);
                fontIcon.setIconColor(Paint.valueOf("#67c23a"));
            }
            case WARN -> {
                fontIcon.setIconCode(MsgIcon.WARN);
                fontIcon.setIconColor(Paint.valueOf("#e6a23c"));
            }
            case ERROR -> {
                fontIcon.setIconCode(MsgIcon.ERROR);
                fontIcon.setIconColor(Paint.valueOf("#f56c6c"));
            }
        }
        fontIcon.setIconSize(20);
        // StackPane iconPane = new SvgStackPane(20, 20, d, notificationColor);


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
        notificationItem.getChildren().addAll(fontIcon, messageLabel, closeButton);

        // Anchor iconPane to the left
        AnchorPane.setLeftAnchor(fontIcon, 5.0);
        AnchorPane.setTopAnchor(fontIcon, 5.0);

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