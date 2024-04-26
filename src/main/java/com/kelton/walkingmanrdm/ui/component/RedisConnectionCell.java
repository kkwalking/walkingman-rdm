package com.kelton.walkingmanrdm.ui.component;

import com.kelton.walkingmanrdm.core.model.RedisConnectInfoProp;
import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.ConnectionService;
import com.kelton.walkingmanrdm.ui.component.ConnectionInfoStage;
import com.kelton.walkingmanrdm.ui.test.RedisKeyBrowser;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.List;

public class RedisConnectionCell extends ListCell<RedisConnectInfoProp> {
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
    }

    @Override
    protected void updateItem(RedisConnectInfoProp item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);  // No text in label of super class
        if (empty) {
            setGraphic(null); // Don't display anything
        } else {
            // 设置文本为连接名称
            nameLabel.setText(item.title().get());
            setGraphic(hbox); // 设置图形为HBox
            setOnMouseClicked(event -> {
                // 左键双击
                if(event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                    openAndSelectNewTab(item);
                }
            });
        }
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
        // 重新提取数据，更新ListView
        List<RedisConnectionInfo> connectionList = ConnectionService.INSTANT.getAllConnectList();
        List<RedisConnectInfoProp> props = RedisConnectionInfo.convertToPropList(connectionList);
        getListView().getItems().clear();
        getListView().getItems().addAll(props);
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
        BorderPane redisContent = new RedisKeyBrowser().createContent(connectionInfo.toInfo()); // 假设方法返回BorderPane，传入连接信息
        newTab.setContent(redisContent);
        newTab.setClosable(true); // 设置新Tab可关闭
        tabPane.getTabs().add(newTab); // 添加新的Tab到TabPane中
        tabPane.getSelectionModel().select(newTab); // 切换到新创建的Tab
    }


}