package com.kelton.walkingmanrdm.ui.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kelton.walkingmanrdm.common.util.JsonUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class JsonFormatterWithGsonApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 示例JSON字符串
        String jsonString = "{\"name\":\"John\", \"age\":30, \"car\":null, \"cities\":[\"New York\",\"Los Angeles\"]}";

        // 使用Gson库格式化JSON字符串
        String formattedJson = JsonUtil.formatJson(jsonString);

        // 创建TextArea用于展示格式化后的JSON
        TextArea textArea = new TextArea(formattedJson);
        textArea.setEditable(false);  // 设置为只读以防止编辑

        // 设置场景和舞台
        BorderPane root = new BorderPane();
        root.setCenter(textArea);
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Formatted JSON Display with Gson");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}