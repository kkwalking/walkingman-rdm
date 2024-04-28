package com.kelton.walkingmanrdm.ui.component;

import com.kelton.walkingmanrdm.core.model.RedisConnectInfoProp;
import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.ConnectionService;
import com.kelton.walkingmanrdm.core.service.RedisBasicCommand;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

public class RedisConnectionCell extends ListCell<RedisConnectInfoProp> {

    private static final int HEIGHT = 30;

    private HBox hbox = new HBox();

    private Label nameLabel = new Label("");
    private Button editButton = new Button("编辑");
    private Button deleteButton = new Button("删除");
    private Region spacer = new Region(); // 创建一个弹性空白区域

    private TabPane tabPane;
    
    public RedisConnectionCell(TabPane tabPane) {
        super();
        this.tabPane = tabPane;

        hbox.setAlignment(Pos.CENTER);

        HBox.setHgrow(spacer, Priority.ALWAYS); // 让空白区域可以伸缩
        editButton.setOnAction(event -> {
            RedisConnectInfoProp connection = getItem();
            // 实现编辑逻辑，打开编辑窗口并传入当前连接
            editConnection(connection);

        });

        deleteButton.setOnAction(event -> {
            RedisConnectInfoProp connection = getItem();
            // 实现删除逻辑，从数据库中删除并刷新ListView
            deleteConnection(connection);
        });

        hbox.getChildren().addAll(nameLabel, spacer, editButton, deleteButton); // 添加到HBox
        setPrefHeight(HEIGHT);
        setMaxHeight(HEIGHT);
        setMinHeight(HEIGHT);
    }

    @Override
    protected void updateItem(RedisConnectInfoProp item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);
        if (empty) {
            setGraphic(null);
        } else {
            // 设置文本为连接名称
            nameLabel.setText(item.title().get());
            nameLabel.setStyle("-fx-font-size: 16px;-fx-padding: 5");
            setGraphic(hbox); // 设置图形为HBox
            setOnMouseClicked(event -> {
                // 左键双击
                if(event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                    if(checkConnection(item)) {
                        openAndSelectNewTab(item);
                    } else {
                        NotificationManager.showNotification("["+item.title().get() + "] 连接到redis失败", NotificationManager.Type.ERROR);
                    }
                }
            });
        }
    }

    private boolean checkConnection(RedisConnectInfoProp item) {
        RedisConnectionInfo info = item.toInfo();
        return RedisBasicCommand.INSTANT.testConnect(info);

    }

    private void editConnection(RedisConnectInfoProp connection) {
        ConnectionInfoStage connectionInfoStage = new ConnectionInfoStage(connection);
        connectionInfoStage.show();
        connectionInfoStage.setOnHidden(event -> {
            getListView().refresh();
        });
    }

    private void deleteConnection(RedisConnectInfoProp connection) {
        ConnectionService.INSTANT.deleteById(connection.id().get());
        getListView().getItems().remove(connection);
    }


    private void openAndSelectNewTab(RedisConnectInfoProp connectionInfo) {
        String tabTitle = connectionInfo.title().get();
        // 避免创建重复的Tab
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equals(tabTitle)) {
                tabPane.getSelectionModel().select(tab);
                return;
            }
        }
        // 如果不存在则创建新的Tab并选中
        Tab newTab = new Tab(tabTitle);
        BorderPane redisContent = new RedisOperaPane(connectionInfo.toInfo());
        VBox.setVgrow(redisContent, Priority.ALWAYS);
        newTab.setContent(redisContent);
        newTab.setClosable(true);
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab); // 切换到新创建的Tab
    }


}