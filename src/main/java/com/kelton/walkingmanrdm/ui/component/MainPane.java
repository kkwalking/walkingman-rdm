package com.kelton.walkingmanrdm.ui.component;

import com.kelton.walkingmanrdm.core.model.RedisConnectInfoProp;
import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.ConnectionService;
import com.kelton.walkingmanrdm.ui.component.ConnectionInfoStage;
import com.kelton.walkingmanrdm.ui.component.RedisConnectionCell;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.stage.Modality;

import java.util.List;

/**
 * @Author zhouzekun
 * @Date 2024/4/26 9:45
 */
public class MainPane extends BorderPane {

    public static final double CELL_HEIGHT = 30.0;

    private HBox listViewContainer;


    private HBox header;

    private VBox content;

    private TabPane tabPane;

    public MainPane(TabPane tabPane) {
        super(); // 主布局容器

        header = createHeader(); // 创建header区域
        content = createContent(); // 创建content区域
        this.tabPane = tabPane;
        setTop(header); // 设置头部区域
        setCenter(content); // 设置内容区域
        getStylesheets().add(getClass().getResource("/css/mainPane.css").toExternalForm());
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(30));
        Button newBtn = new Button("+ 新增连接");
        newBtn.setOnAction(event -> {
            // 实现打开一个对话框来添加新的Redis连接，并保存到数据库
            ConnectionInfoStage connectionInfoStage = new ConnectionInfoStage();
            // 设置为模态窗口
            connectionInfoStage.initOwner(this.getScene().getWindow()); // 获取当前主窗口作为父窗口
            connectionInfoStage.initModality(Modality.APPLICATION_MODAL); // 设置模态类型

            connectionInfoStage.showAndWait(); // 显示模态窗口并等待关闭前不允许用户交互
            refreshConnectionInfoList();
        });
        newBtn.setPrefSize(100,30);
        newBtn.setStyle("-fx-background-color:#009688;-fx-text-fill: white;-fx-font-size: 15;");
        newBtn.setOnMouseEntered(e-> {
            newBtn.setStyle("-fx-background-color:#036c5f;-fx-text-fill: white;-fx-font-size: 15;");
        });
        newBtn.setOnMouseExited(e-> {
            newBtn.setStyle("-fx-background-color:#009688;-fx-text-fill: white;-fx-font-size: 15;");
        });

        Region spaceReg = new Region();
        HBox.setHgrow(spaceReg, Priority.ALWAYS);
        header.getChildren().addAll(spaceReg, newBtn);
        return header;
    }

    private void refreshConnectionInfoList() {
        List<RedisConnectionInfo> connectionList = fetchConnectionFromDatabase();
        listViewContainer.getChildren().remove(1);
        ConnectionListView<RedisConnectInfoProp> newListView =
                new ConnectionListView<>(FXCollections.observableArrayList(RedisConnectionInfo.convertToPropList(connectionList)), CELL_HEIGHT*4, CELL_HEIGHT*10, CELL_HEIGHT);
        listViewContainer.getChildren().add(1,newListView);
        newListView.setCellFactory(param -> new RedisConnectionCell(tabPane));
        newListView.refreshHeight();
        // 设置ListView占据HBox宽度的50%
        newListView.prefWidthProperty().bind(listViewContainer.widthProperty().multiply(0.5));

    }

    private VBox createContent() {

        VBox content = new VBox();
        content.setSpacing(5); // 设置节点之间的间距
        Region leftSpace = new Region();  // 左侧空间
        Region rightSpace = new Region(); // 右侧空间
        HBox.setHgrow(leftSpace, Priority.ALWAYS);  // 左侧空间吸收额外空间
        HBox.setHgrow(rightSpace, Priority.ALWAYS); // 右侧空间吸收额外空间

        List<RedisConnectionInfo> connectionList = fetchConnectionFromDatabase();

        if (connectionList.size() > 0) {
            ListView<RedisConnectInfoProp> listView =
                    new ConnectionListView<>(FXCollections.observableArrayList(RedisConnectionInfo.convertToPropList(connectionList)), CELL_HEIGHT*4, CELL_HEIGHT*10, CELL_HEIGHT);
            listView.setCellFactory(param -> new RedisConnectionCell(tabPane));
            listView.setPrefHeight(connectionList.size() * CELL_HEIGHT + 2 * content.getSpacing());
            listViewContainer = new HBox(leftSpace, listView, rightSpace);
            // 设置ListView占据HBox宽度的50%
            listView.prefWidthProperty().bind(listViewContainer.widthProperty().multiply(0.5));
        } else {
            Label promptNewConnLabel = new Label("点击右侧新建Redis连接~");
            listViewContainer = new HBox(leftSpace, promptNewConnLabel, rightSpace);
        }

        listViewContainer.setMinHeight(300);
        listViewContainer.setFillHeight(false);

        content.getChildren().add(listViewContainer); // 将ListView添加到内容区域

        return content;
    }

    private List<RedisConnectionInfo> fetchConnectionFromDatabase() {
        return ConnectionService.INSTANT.getAllConnectList();
    }

}
