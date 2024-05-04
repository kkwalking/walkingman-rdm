package com.kelton.walkingmanrdm.ui.pane;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhouzekun
 * @Date 2024/5/4 13:36
 */
public class RedisDetailPaneContainer extends StackPane {

    private Map<String, Pane> container;

    private BorderPane emptyPane;

    private RedisConnectionInfo connectionInfo;
    public RedisDetailPaneContainer(RedisConnectionInfo connectionInfo) {
        super();
        this.connectionInfo = connectionInfo;

        this.container = new HashMap<>();
        this.emptyPane = new BorderPane();
        Label label = new Label("请双击一个key进行操作~");
        label.setFont(Font.font(15));
        label.setTextFill(Paint.valueOf("#1f1f1f"));
        emptyPane.setCenter(label);
        emptyPane.setVisible(true);
        getChildren().add(emptyPane);
    }

    public void addPane(String key, Pane pane) {
        if (!this.container.containsKey(key)) {
            this.container.put(key, pane);
            getChildren().add(pane);
        }
    }

    public void removePane(String key, Pane pane) {
        if (this.container.containsKey(key)) {
            this.container.remove(key);
            getChildren().remove(pane);
        }
    }

    public void showPane(String key) {
        if (!this.container.containsKey(key)) {
            addPane(key, new RedisDetailPane(connectionInfo, key));
        }

        Pane pane = this.container.get(key);
        Collection<Pane> values = container.values();
        values.forEach(value -> value.setVisible(false));
        if (pane instanceof RedisDetailPane) {
            ((RedisDetailPane) pane).refresh();
        }
        pane.setVisible(true);

    }
}
