package com.kelton.walkingmanrdm.ui.demo;

import com.kelton.walkingmanrdm.common.util.SqlUtils;
import com.kelton.walkingmanrdm.ui.component.MainPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 标签页试验代码
 */
public class TabWindow extends Application {

    public static TabPane tabPane;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane(); // 主布局容器

        tabPane = new TabPane(); // 标签页容器

        // 主窗口标签页
        Tab homeTab = new Tab("主窗口");
        MainPane mainPane = new MainPane(tabPane);
        homeTab.setContent(mainPane);
        homeTab.setClosable(false);

        // 将标签页添加到TabPane中
        tabPane.getTabs().addAll(homeTab);

        // 将TabPane设置为主布局容器的顶部
        root.setTop(tabPane);

        // 显示Stage
        Scene scene = new Scene(root, 1450, 820);
        primaryStage.setTitle("标签页示例");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        SqlUtils.init();
        launch(args);
    }
}
