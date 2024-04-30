package com.kelton.walkingmanrdm.ui.component;

import com.kelton.walkingmanrdm.core.model.RedisConnectInfoProp;
import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.ConnectionService;
import com.kelton.walkingmanrdm.core.service.RedisBasicCommand;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsFilled;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.coreui.CoreUiBrands;
import org.kordamp.ikonli.javafx.FontIcon;

public class RedisConnectionCell extends ListCell<RedisConnectInfoProp> {

    private static final int HEIGHT = 30;

    private HBox hbox = new HBox();

    private Label nameLabel = new Label("");
    private StackPane editButton = new StackPane();
    private StackPane deleteButton = new StackPane();
    private Region spacer = new Region(); // 创建一个弹性空白区域

    private TabPane tabPane;
    
    public RedisConnectionCell(TabPane tabPane) {
        super();
        this.tabPane = tabPane;

        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(5);

        HBox.setHgrow(spacer, Priority.ALWAYS); // 让空白区域可以伸缩
        FontIcon editIcon = new FontIcon(AntDesignIconsFilled.EDIT);
        editIcon.setIconSize(20);
        editButton.getChildren().add(editIcon);
        editButton.getStyleClass().add("edit-btn");
        editButton.setMaxSize(30, 30);
        editButton.setMinSize(30, 30);
        editButton.setOnMouseClicked(event -> {
            RedisConnectInfoProp connection = getItem();
            // 实现编辑逻辑，打开编辑窗口并传入当前连接
            editConnection(connection);

        });
        FontIcon delIcon = new FontIcon(AntDesignIconsFilled.DELETE);
        delIcon.setIconSize(20);
        deleteButton.getChildren().add(delIcon);
        deleteButton.getStyleClass().add("del-btn");
        deleteButton.setMaxSize(30, 30);
        deleteButton.setMinSize(30, 30);
        deleteButton.setOnMouseClicked(event -> {
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
            getStyleClass().removeAll("list-cell-odd", "list-cell-even");
            if (getIndex() % 2 == 0) {
                getStyleClass().add("list-cell-even");
            } else {
                getStyleClass().add("list-cell-odd");
            }
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
        Tab redisTab = new Tab(tabTitle);

        FontIcon redisIcon = new FontIcon(BoxiconsRegular.DATA);
        redisIcon.setIconSize(18);
        redisIcon.iconColorProperty().bind(
                redisTab.selectedProperty().map(selected-> selected? Paint.valueOf("white"):Paint.valueOf("black")));

        redisTab.setGraphic(redisIcon);
        BorderPane redisContent = new RedisOperaPane(connectionInfo.toInfo());
        VBox.setVgrow(redisContent, Priority.ALWAYS);
        redisTab.setContent(redisContent);
        redisTab.setClosable(true);
        tabPane.getTabs().add(redisTab);
        tabPane.getSelectionModel().select(redisTab); // 切换到新创建的Tab
    }


}