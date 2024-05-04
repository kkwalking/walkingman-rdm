package com.kelton.walkingmanrdm;

import com.kelton.walkingmanrdm.common.util.SqlUtils;
import com.kelton.walkingmanrdm.ui.pane.RootStackPane;
import com.kelton.walkingmanrdm.ui.global.GlobalObjectPool;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author zhouzekun
 * @Date 2024/4/17 17:27
 */
@Slf4j
public class RdmBootApplication extends Application {

    private double MIN_WIDTH; // 最小宽度
    private double MIN_HEIGHT; // 最小高度

    public static void main(String[] args) {
        System.out.println("初始化数据库");
        SqlUtils.init();
        launch();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 注册primaryStage
        GlobalObjectPool.register(GlobalObjectPool.PRIMARY_STAGE, primaryStage);

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



        StackPane stackPane = new RootStackPane();
        stackPane.setPrefSize(MIN_WIDTH, MIN_HEIGHT);
        stackPane.setAlignment(Pos.TOP_CENTER); // 将通知对齐到顶部左

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
