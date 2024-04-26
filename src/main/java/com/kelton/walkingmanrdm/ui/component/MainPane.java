package com.kelton.walkingmanrdm.ui.component;

import com.kelton.walkingmanrdm.core.model.RedisConnectInfoProp;
import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.ConnectionService;
import com.kelton.walkingmanrdm.ui.component.ConnectionInfoStage;
import com.kelton.walkingmanrdm.ui.component.RedisConnectionCell;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;

import java.util.List;

/**
 * @Author zhouzekun
 * @Date 2024/4/26 9:45
 */
public class MainPane extends BorderPane {

    private ListView<RedisConnectInfoProp> listView;

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
    }

    private HBox createHeader() {
        HBox header = new HBox();
        Button newBtn = new Button("新增连接");
        newBtn.setOnAction(event -> {
            // 实现打开一个对话框来添加新的Redis连接，并保存到数据库
            ConnectionInfoStage connectionInfoStage = new ConnectionInfoStage();
            connectionInfoStage.show();
            connectionInfoStage.setOnHidden(e -> {
                refreshConnectionInfoList();
            });
        });
        newBtn.setStyle("-fx-background-color:#009688;-fx-text-fill: white;");
        newBtn.setOnMouseEntered(e-> {
            newBtn.setStyle("-fx-background-color:#036c5f;-fx-text-fill: white;");
        });
        newBtn.setOnMouseExited(e-> {
            newBtn.setStyle("-fx-background-color:#009688;-fx-text-fill: white;");
        });

        Region spaceReg = new Region();
        header.setPadding(new Insets(5, 30, 5, 30));
        HBox.setHgrow(spaceReg, Priority.ALWAYS);
        header.getChildren().addAll(spaceReg, newBtn);
        return header;
    }

    private void refreshConnectionInfoList() {
        List<RedisConnectionInfo> connectionList = fetchConnectionFromDatabase();
        listView.setItems(FXCollections.observableArrayList(RedisConnectionInfo.convertToPropList(connectionList)));
    }

    private VBox createContent() {
        VBox content = new VBox();
        content.setSpacing(5); // 设置节点之间的间距

        List<RedisConnectionInfo> connectionList = fetchConnectionFromDatabase();

        // 实现一个ListView，每个Item代表一个Redis连接
        // 使用自定义的CellFactory来添加编辑和删除按钮
        listView =
                new ListView<>(FXCollections.observableArrayList(RedisConnectionInfo.convertToPropList(connectionList)));
        listView.setCellFactory(param -> new RedisConnectionCell(tabPane));

        content.getChildren().add(listView); // 将ListView添加到内容区域

        return content;
    }

    private List<RedisConnectionInfo> fetchConnectionFromDatabase() {
        return ConnectionService.INSTANT.getAllConnectList();
    }

}
