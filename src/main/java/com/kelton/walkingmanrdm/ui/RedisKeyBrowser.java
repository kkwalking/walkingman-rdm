package com.kelton.walkingmanrdm.ui;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.RedisBasicCommand;
import com.kelton.walkingmanrdm.core.service.RedisStringCommand;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Set;

/**
 * @Author kelton
 * @Date 2024/4/25 16:30
 * @Version 1.0
 */
public class RedisKeyBrowser extends Application {

    private TextArea valueTextArea;

    @Override
    public void start(Stage primaryStage) {

        RedisConnectionInfo redisConnectionInfo = new RedisConnectionInfo();
        redisConnectionInfo.host("127.0.0.1").port(6379).password("0754zzk");
        Set<String> keys = RedisBasicCommand.INSTANT.keys(redisConnectionInfo, "*");

        // 创建根节点
        TreeItem<String> rootNode = new TreeItem<>("Redis Keys");
        rootNode.setExpanded(true);  // 默认展开根节点

        // 模拟从Redis读取的key集合
//        String[] keys = {"alice:name", "alice:age", "kelton:address", "kelton:school"};

        for (String key : keys) {
            String[] parts = key.split(":");
            addKeyToTree(parts, rootNode);
        }
        // 创建TreeView
        TreeView<String> treeView = new TreeView<>(rootNode);

        treeView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // 双击检测
                TreeItem<String> currentItemSelected = treeView.getSelectionModel().getSelectedItem();
                if (currentItemSelected != null && currentItemSelected.isLeaf()) { // 是叶子节点
                    String key = buildFullKeyPath(currentItemSelected, rootNode); // 传递根节点参数
                    String value = RedisStringCommand.INSTANT.get(redisConnectionInfo, key);
                    valueTextArea.setText(value);
                }
            }
        });

        // 创建用于展示value的TextArea
        valueTextArea = new TextArea();
        valueTextArea.setEditable(false);

        // 使用BorderPane作为主布局
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(treeView); // TreeView放在左侧
        borderPane.setCenter(valueTextArea); // TextArea放在中间展示value

        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setTitle("Redis Key Browser");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addKeyToTree(String[] parts, TreeItem<String> parent) {
        for (int i = 0; i < parts.length; i++) {
            boolean found = false;
            for (TreeItem<String> child : parent.getChildren()) {
                if (child.getValue().equals(parts[i])) {
                    parent = child;  // 移动到这个子节点作为下一个父节点
                    found = true;
                    break;  
                }
            }
            if (!found) {
                // 没有找到对应的子节点，创建一个新的节点
                TreeItem<String> child = new TreeItem<>(parts[i]);
                parent.getChildren().add(child);
                parent = child;  // 更新父节点为刚创建的节点
            }
        }
    }

    private String buildFullKeyPath(TreeItem<String> item, TreeItem<String> root) {
        StringBuilder keyPath = new StringBuilder(item.getValue());
        TreeItem<String> parent = item.getParent();

        // 循环向上构建路径，直到到达根节点位置
        while (parent != null && parent != root) { // parent != root 用来检查是否为根节点
            keyPath.insert(0, parent.getValue() + ":");
            parent = parent.getParent();
        }

        return keyPath.toString();
    }

    public static void main(String[] args) {
        launch(args);
    }
}