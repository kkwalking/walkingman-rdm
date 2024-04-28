package com.kelton.walkingmanrdm.ui.component;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * @Author zhouzekun
 * @Date 2024/4/26 17:18
 */
public class ConnectionListView<T> extends ListView<T> {


    private final double min;
    private final double max;
    private final double cellHeight;

    public ConnectionListView(ObservableList<T> items, double min, double max, double cellHeight) {
        super(items);

        this.min = min;
        this.max = max;
        this.cellHeight = cellHeight;
        items.addListener((ListChangeListener<T>) c -> {
            refreshHeight();
        });
        if (this.min > 0) {
            minHeight(min);
        }
        maxHeight(max);
    }


    public void refreshHeight() {
        ObservableList<T> items = getItems();
        if (items.size() > 0) {
            final double currentHeight = Bindings.size(items).multiply(cellHeight).add(10).doubleValue();

            double heightNotLowerThanMin = Math.max(currentHeight, min);

            double finalHeight = Math.min(heightNotLowerThanMin, max);

            setPrefHeight(finalHeight);
        } else {
            Label promptNewConnLabel = new Label("点击右侧新建Redis连接~");
            ((HBox) this.getParent()).getChildren().set(1, promptNewConnLabel);
        }

    }
}
