package com.kelton.walkingmanrdm.ui.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ModalWindowWithOverlay extends Application {

    private Pane overlay;

    @Override
    public void start(Stage primaryStage) {
        Button btnOpenModal = new Button("Edit Redis");
        btnOpenModal.setOnAction(event -> showModalWindow(primaryStage));

        StackPane root = new StackPane();
        root.getChildren().add(btnOpenModal);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Main Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showModalWindow(Stage parentStage) {
        // 遮罩
        overlay = createOverlay(parentStage);
        StackPane root = (StackPane) parentStage.getScene().getRoot();
        root.getChildren().add(overlay);

        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(parentStage);
        modalStage.initStyle(StageStyle.UNDECORATED); // 无装饰

        Button buttonClose = new Button("Close");
        buttonClose.setOnAction(e -> {
            modalStage.close();
            root.getChildren().remove(overlay); // 移除遮罩
        });

        StackPane modalContent = new StackPane(buttonClose);
        modalContent.setStyle("-fx-background-color: white; -fx-padding: 10;");

        Scene modalScene = new Scene(modalContent, 200, 100);
        modalStage.setScene(modalScene);

        // 在显示模态窗口前，添加遮罩
        modalStage.showAndWait();
    }

    // 创建半透明遮罩
    private Pane createOverlay(Stage parentStage) {
        Rectangle rect = new Rectangle(parentStage.getScene().getWidth(), parentStage.getScene().getHeight(), Color.rgb(0, 0, 0, 0.4));
        // 使遮罩层大小与主舞台同步变化
        rect.widthProperty().bind(parentStage.getScene().widthProperty());
        rect.heightProperty().bind(parentStage.getScene().heightProperty());

        // 遮罩层容器
        Pane overlay = new Pane(rect);
        overlay.setVisible(true);

        return overlay;
    }

    public static void main(String[] args) {
        launch(args);
    }
}