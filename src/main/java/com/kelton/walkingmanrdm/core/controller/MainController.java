package com.kelton.walkingmanrdm.core.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @Author kelton
 * @Date 2024/4/23 21:30
 * @Version 1.0
 */
public class MainController {
    // 这个方法与FXML中定义的onAction属性对应
    @FXML
    private void handleNewConnection(ActionEvent event) {
        // 创建窗口
        Stage newConnectionStage = new Stage();
        newConnectionStage.initModality(Modality.APPLICATION_MODAL);

        // 顶部label
        Label topLabel = new Label("Redis Connection Details");

        // 中间连接信息区域
        VBox middlePane = new VBox(20);

        HBox nameHbox = new HBox(new Label("连接名"));
        TextField nameField = new TextField();
        nameField.setPromptText("connection name");
        nameHbox.getChildren().add(nameField);
        middlePane.getChildren().add(nameHbox);

        HBox hostHBox = new HBox(new Label("主机"));
        TextField hostField = new TextField();
        hostField.setPromptText("Host");
        hostHBox.getChildren().add(hostField);
        middlePane.getChildren().add(hostHBox);

        HBox portHBox = new HBox(new Label("端口"));
        TextField portField = new TextField();
        portField.setPromptText("Port");
        portHBox.getChildren().add(portField);
        middlePane.getChildren().add(portHBox);

        HBox passHBox = new HBox(new Label("密码"));
        TextField passField = new TextField();
        passField.setPromptText("Password");
        passHBox.getChildren().add(passField);
        middlePane.getChildren().add(passHBox);

        // 底部按钮区域
        Button connectButton = new Button("Connect");
        Button saveBtn = new Button("Save");
        HBox bottomHBox = new HBox(saveBtn, connectButton);
        bottomHBox.setSpacing(30);

        // 将控件添加到布局容器
        VBox layout = new VBox(10); // 10为元素间的间距
        layout.getChildren().addAll(topLabel, middlePane , bottomHBox);

        // 设置并显示窗口
        Scene scene = new Scene(layout, 300, 250); // 这里设置新窗口的大小
        newConnectionStage.setScene(scene);
        newConnectionStage.setTitle("New Connection");
        newConnectionStage.showAndWait();
    }



}
