package com.kelton.walkingmanrdm.ui.component;

import com.kelton.walkingmanrdm.ui.svg.RedisIcon;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.util.Set;
import java.util.function.Consumer;

/**
 * @Author zhouzekun
 * @Date 2024/4/28 10:55
 */
public class RedisKeyTreeView extends TreeView<Pane> {

    private TreeItem<Pane> rootNode;



    public RedisKeyTreeView(Set<String> keys, Consumer<String> function) {
        super();
        // 创建根节点
        rootNode = new TreeItem<>(new HBox(new Pane(), new Label("Redis Keys")));
        rootNode.setExpanded(true);  // 默认展开根节点
        setShowRoot(false);

        for (String key : keys) {
            String[] parts = key.split(":");
            addKeyToTree(parts, rootNode);
        }
        setRoot(rootNode);
        setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TreeItem<Pane> item = getSelectionModel().getSelectedItem();
                if (item != null && item.isLeaf()) {
                    String key = buildFullKeyPath(item);
                    // 设置双击回调事件
                    function.accept(key);
                }
            }
        });

        // 更新叶子节点的图标
        freshLeafIcon(rootNode);

        setMaxWidth(Double.MAX_VALUE);

        this.setCellFactory(tv -> {
            TreeCell<Pane> cell = new TreeCell<>() {
                @Override
                protected void updateItem(Pane item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(item);
                    }
                    getStyleClass().removeAll("tree-cell-odd", "tree-cell-even");
                    if (getIndex() % 2 == 0) {
                        getStyleClass().add("tree-cell-even");
                    } else {
                        getStyleClass().add("tree-cell-odd");
                    }
                }
            };
            // 添加cell自定义CSS类
            cell.getStyleClass().add("custom-tree-cell");
            return cell;
        });
        this.getStyleClass().add("redis-tree-view");

        this.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                // 取消选中时的处理逻辑
                updateTreeItemIcon(oldValue, false);
            }
            if (newValue != null) {
                // 选中时的处理逻辑
                updateTreeItemIcon(newValue, true);
            }
        });
    }

    private void updateTreeItemIcon(TreeItem<Pane> item, boolean active) {
        RedisIcon redisIcon = (RedisIcon) item.getValue().getChildren().get(0);
        if (active) {
            redisIcon.active();
        } else {
            redisIcon.deactive();
        }
    }

    private void freshLeafIcon(TreeItem<Pane> node) {
        ObservableList<TreeItem<Pane>> children = node.getChildren();
        children.forEach( e-> {
            boolean leaf = e.isLeaf();
            if (leaf) {
                 e.getValue().getChildren().remove(0);
                e.getValue().getChildren().add(0, new RedisIcon(RedisIcon.Type.String));
            } else {
                freshLeafIcon(e);
            }
        });
    }

    private String buildFullKeyPath(TreeItem<Pane> item) {
        String key = ((Label) item.getValue().getChildren().get(1)).getText();
        StringBuilder keyPath = new StringBuilder(key);
        TreeItem<Pane> parent = item.getParent();

        // 循环向上构建路径，直到到达根节点位置
        while (parent != null && parent != rootNode) { // parent != root 用来检查是否为根节点
            String parentKey = ((Label) parent.getValue().getChildren().get(1)).getText();
            keyPath.insert(0, parentKey + ":");
            parent = parent.getParent();
        }

        return keyPath.toString();
    }

    private void addKeyToTree(String[] parts, TreeItem<Pane> parent) {
        for (int i = 0; i < parts.length; i++) {
            boolean found = false;
            for (TreeItem<Pane> child : parent.getChildren()) {
                String key = ((Label) child.getValue().getChildren().get(1)).getText();
                if (key.equals(parts[i])) {
                    parent = child;  // 移动到这个子节点作为下一个父节点
                    found = true;
                    break;
                }
            }
            if (!found) {
                // 没有找到对应的子节点，创建一个新的节点
                Label label = new Label(parts[i]);
                RedisIcon redisIcon = new RedisIcon(RedisIcon.Type.Folder);
                HBox hBox = new HBox(redisIcon, label);
                hBox.setAlignment(Pos.CENTER_LEFT);
                TreeItem<Pane> child = new TreeItem<>(hBox);

                parent.getChildren().add(child);
                parent = child;  // 更新父节点为刚创建的节点
            }
        }
    }
}
