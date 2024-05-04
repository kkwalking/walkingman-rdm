package com.kelton.walkingmanrdm.ui.demo;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        VBox content = new VBox();
        root.getChildren().add(content);

        Button showRedisDialogButton = new Button("编辑Redis信息");
        content.getChildren().add(showRedisDialogButton);

        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();

        showRedisDialogButton.setOnAction(event -> {
            // 创建遮罩层
            Rectangle mask = new Rectangle();
            mask.setFill(Color.web("black", 0.15));
            mask.widthProperty().bind(root.widthProperty());
            mask.heightProperty().bind(root.heightProperty());
            mask.setVisible(false);
            root.getChildren().add(mask);

            GridPane dialog = new GridPane();
            dialog.setHgap(10);
            dialog.setVgap(10);
            dialog.setPadding(new Insets(0, 10, 0, 10));
            dialog.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-border-color: black; -fx-border-radius: 20; -fx-background-radius: 20;");

            dialog.add(new Label("请输入Redis信息"), 0, 0);
            TextField redisInfoTextField = new TextField();
            dialog.add(redisInfoTextField, 0, 1);
            Button saveButton = new Button("保存");
            dialog.add(saveButton, 0, 2, 2, 1);
            GridPane.setHalignment(saveButton, HPos.RIGHT);

            dialog.setVisible(false);
            dialog.setPrefSize(200,100);
            dialog.setMaxSize(200,100);
            root.getChildren().add(dialog);

            mask.setVisible(true);
            dialog.setVisible(true);

            saveButton.setOnAction(e -> {
                System.out.println("Redis信息已保存: " + redisInfoTextField.getText());
                mask.setVisible(false);
                dialog.setVisible(false);
            });
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}