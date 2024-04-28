package com.kelton.walkingmanrdm.ui.component;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.Set;
import java.util.function.Consumer;

/**
 * @Author zhouzekun
 * @Date 2024/4/28 10:55
 */
public class RedisKeyTreeView extends TreeView<String> {

    public RedisKeyTreeView(Set<String> keys, Consumer<String> function) {
        super();
        // 创建根节点
        TreeItem<String> rootNode = new TreeItem<>("Redis Keys");
        rootNode.setExpanded(true);  // 默认展开根节点

        for (String key : keys) {
            String[] parts = key.split(":");
            addKeyToTree(parts, rootNode);
        }
        setRoot(rootNode);
        setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TreeItem<String> item = getSelectionModel().getSelectedItem();
                if (item != null && item.isLeaf()) {
                    String key = buildFullKeyPath(item, rootNode);
                    // 设置双击回调事件
                    function.accept(key);
                }
            }
        });
        // 设置树形列表的最大宽度为主窗口宽度的30%
        setMaxWidth(Double.MAX_VALUE);


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
}
