package com.kelton.walkingmanrdm.ui.controller;

import com.kelton.walkingmanrdm.ui.component.ConnectionInfoStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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

    private ConnectionInfoStage connectionModal;


    @FXML
    private void handleNewConnection(ActionEvent event) {
        flushConnectionModal();
        connectionModal.showAndWait();
    }


    private void initConnectionModal() {
        // 创建窗口
        ConnectionInfoStage newConnectionStage = new ConnectionInfoStage();
        newConnectionStage.initModality(Modality.APPLICATION_MODAL);
        this.connectionModal = newConnectionStage;

    }

    private void flushConnectionModal() {
        if (this.connectionModal == null) {
            initConnectionModal();
        }
        // 清空connectionModal中的所有信息
        this.connectionModal.clearData();
    }


}
