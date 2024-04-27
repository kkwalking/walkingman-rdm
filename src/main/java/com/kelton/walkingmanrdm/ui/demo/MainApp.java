package com.kelton.walkingmanrdm.ui.demo;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    // 创建一个自定义的 ListView，它可以自动调整高度
    public class DynamicListView extends ListView<String> {

        public DynamicListView(ObservableList<String> items) {
            super(items);
            items.addListener((ListChangeListener<String>) c -> {
                refreshHeight();
            });
            minHeight(20);
            maxHeight(100);
        }

        private void refreshHeight() {
            ListCell<String> cell = new ListCell<>();
            double prefHeight = 20;
            prefHeightProperty().bind(Bindings.min(100,Bindings.size(getItems()).multiply(prefHeight).add(10)));
        }
    }

    @Override
    public void start(Stage primaryStage) {
        ObservableList<String> items = FXCollections.observableArrayList();

        DynamicListView listView = new DynamicListView(items);

        Button addButton = new Button("Add Item");
        addButton.setOnAction(event -> items.add("Item " + (items.size() + 1)));

        Button removeButton = new Button("Remove Item");
        removeButton.setOnAction(event -> {
            if (!items.isEmpty()) {
                items.remove(items.size() - 1);
            }
        });

        VBox vBox = new VBox(addButton, removeButton, listView);
        Scene scene = new Scene(vBox, 300, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}