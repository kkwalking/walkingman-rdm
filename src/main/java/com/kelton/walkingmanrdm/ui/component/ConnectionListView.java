package com.kelton.walkingmanrdm.ui.component;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

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
            ObservableList<? extends T> list = c.getList();
            refreshHeight(list);
        });
        minHeight(min);
        maxHeight(max);
    }

    private void refreshHeight( ObservableList<?> addList) {
//        // 计算当前高度，包括 items 的总高度和额外的 10px 边距。
//        final double currentHeight = Bindings.size(getItems()).multiply(cellHeight).add(10).doubleValue();
//
//        // 确保当前高度不低于最小高度。
//        double heightNotLowerThanMin = Math.max(currentHeight, min);
//
//        // 最后，确保高度不超过最大高度。
//        double finalHeight = Math.min(heightNotLowerThanMin, max);
//
//        // 设置 ListView 的最佳高度。
//        setPrefHeight(finalHeight);
//        prefHeightProperty().bind(Bindings.min(min,Bindings.size(getItems()).multiply(cellHeight).add(10)));

        setPrefHeight(getHeight() + addList.size() * cellHeight);
    }
}
