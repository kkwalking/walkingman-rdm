package com.kelton.walkingmanrdm;

import com.kelton.walkingmanrdm.common.util.SqlUtils;
import com.kelton.walkingmanrdm.ui.component.MainPane;
import com.kelton.walkingmanrdm.ui.component.NotificationManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * @Author zhouzekun
 * @Date 2024/4/17 17:27
 */
@Slf4j
public class RdmBootApplication extends Application {

    private double MIN_WIDTH; // 最小宽度
    private double MIN_HEIGHT; // 最小高度

    public static TabPane tabPane;

    private Scene scene;

    public static void main(String[] args) {
        System.out.println("初始化数据库");
        SqlUtils.init();
        launch();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        MIN_WIDTH = bounds.getWidth() * 0.75;
        MIN_HEIGHT = bounds.getHeight() * 0.75;
        // 计算中心位置
        double centerX = bounds.getMinX() + (bounds.getWidth() - MIN_WIDTH) / 2;
        double centerY = bounds.getMinY() + (bounds.getHeight() - MIN_HEIGHT) / 2;
        primaryStage.setX(centerX);
        primaryStage.setY(centerY);
        primaryStage.setWidth(MIN_WIDTH);  //设置窗口宽度为屏幕宽度的四分之三
        primaryStage.setHeight(MIN_HEIGHT);  //设置窗口高度为屏幕高度的四分之三


        tabPane = new TabPane(); // 标签页容器
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        // 主窗口标签页
        Tab homeTab = new Tab("主窗口");

        FontIcon homeIcon = new FontIcon(BoxiconsRegular.HOME);
        homeIcon.setIconSize(18);
        homeIcon.iconColorProperty().bind(
                homeTab.selectedProperty().map(selected-> selected?Paint.valueOf("white"):Paint.valueOf("black")));
        homeTab.setGraphic(homeIcon);
        MainPane mainPane = new MainPane(tabPane);
        homeTab.setContent(mainPane);
        homeTab.setClosable(false);

        // 将标签页添加到TabPane中
        tabPane.getTabs().addAll(homeTab);

        tabPane.setPrefHeight(MIN_HEIGHT);


        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(MIN_WIDTH, MIN_HEIGHT);
        stackPane.setAlignment(Pos.TOP_CENTER); // 将通知对齐到顶部左
        stackPane.getChildren().add(tabPane);
        // 注册消息通知器
        NotificationManager.register(stackPane);

        // 显示Stage
        Scene scene = new Scene(stackPane, null);
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());


        primaryStage.setScene(scene);
        primaryStage.setFullScreenExitHint("");
        primaryStage.getIcons().add(new Image("/img/logo.png"));
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
