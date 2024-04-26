package com.kelton.walkingmanrdm;

import com.kelton.walkingmanrdm.common.util.SqlUtils;
import com.kelton.walkingmanrdm.ui.component.MainPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @Author zhouzekun
 * @Date 2024/4/17 17:27
 */
@Slf4j
public class RdmBootApplication extends Application {

    private static final double MIN_WIDTH = 1450; // 最小宽度
    private static final double MIN_HEIGHT = 820; // 最小高度

    public static TabPane tabPane;

    private Scene scene;

    public static void main(String[] args) {
        System.out.println("初始化数据库");
        SqlUtils.init();
        launch();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        Scene scene = new Scene(root, null);

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setFullScreenExitHint("");
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/img/logo.png")).toExternalForm()));
        primaryStage.setTitle("walkingman-rdm");
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        System.out.println("启动状态服务器中...");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("关闭资源中...");
        Platform.exit();


    }
}
