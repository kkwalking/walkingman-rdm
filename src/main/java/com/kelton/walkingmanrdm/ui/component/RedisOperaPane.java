package com.kelton.walkingmanrdm.ui.component;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.RedisBasicCommand;
import com.kelton.walkingmanrdm.core.service.RedisStringCommand;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

import java.util.Set;

/**
 * @Author kelton
 * @Date 2024/4/25 16:30
 * @Version 1.0
 */
public class RedisOperaPane extends BorderPane {

    private TextArea valueTextArea;


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

    // 此方法将创建并返回包含UI控件的BorderPane
    public RedisOperaPane(RedisConnectionInfo connectionInfo) {
        valueTextArea = new TextArea();
        valueTextArea.setEditable(false);

        Set<String> keys = RedisBasicCommand.INSTANT.keys(connectionInfo, "*");

        // 创建根节点
        TreeItem<String> rootNode = new TreeItem<>("Redis Keys");
        rootNode.setExpanded(true);  // 默认展开根节点

        for (String key : keys) {
            String[] parts = key.split(":");
            addKeyToTree(parts, rootNode);
        }

        // 创建TreeView
        TreeView<String> treeView = new TreeView<>(rootNode);
        treeView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
                if (item != null && item.isLeaf()) {
                    String key = buildFullKeyPath(item, treeView.getRoot());
                    String value = RedisStringCommand.INSTANT.get(connectionInfo, key);
                    valueTextArea.setText(value);
                }
            }
        });
        setLeft(treeView);  // TreeView 放在左侧
        setCenter(valueTextArea);  // TextArea 放在中间展示 value
    }

}