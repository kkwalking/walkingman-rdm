package com.kelton.walkingmanrdm.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 标签页试验代码
 */
public class MainApplicationWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane(); // 主布局容器

        TabPane tabPane = new TabPane(); // 标签页容器

        // 创建“远程redis”标签页
        Tab tabRemoteRedis = new Tab("远程 redis");
        ListView<String> remoteListView = new ListView<>(); // 这里填充远程Redis的内容
        remoteListView.getItems().addAll("远程Item 1", "远程Item 2", "远程Item 3"); // 示例内容
        tabRemoteRedis.setContent(remoteListView); // 将ListView设置为标签页内容

        // 创建“本地redis”标签页
        Tab tabLocalRedis = new Tab("本地 redis");
        // 本地标签页内容使用上文提到的RedisKeyBrowser布局
        // 请确保将原始的RedisKeyBrowser布局抽象成可以被复用的组件
        BorderPane localRedisContent = new RedisKeyBrowser().createContent(); // 假设方法返回BorderPane
        tabLocalRedis.setContent(localRedisContent); // 设置本地Redis标签页内容

        // 将标签页添加到TabPane中
        tabPane.getTabs().addAll(tabRemoteRedis, tabLocalRedis);

        new Thread(() -> {
            try {
                // 模拟点击操作
                Thread.sleep(3000);
                Platform.runLater(() -> {

                    // 创建“远程redis”标签页2
                    Tab tabRemoteRedis2 = new Tab("远程 redis2");
                    ListView<String> remoteListView2 = new ListView<>(); // 这里填充远程Redis的内容
                    remoteListView2.getItems().addAll("远程Item 2-1", "远程Item 2-2", "远程Item 2-3"); // 示例内容
                    tabRemoteRedis2.setContent(remoteListView2); // 将ListView设置为标签页内容
                    tabPane.getTabs().add(tabRemoteRedis2);
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();



        // 将TabPane设置为主布局容器的顶部
        root.setTop(tabPane);

        // 显示Stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("标签页示例");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
