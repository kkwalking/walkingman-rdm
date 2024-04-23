package com.kelton.walkingmanrdm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @Author zhouzekun
 * @Date 2024/4/17 17:27
 */
@Slf4j
public class RdmBootApplication extends Application {

    private static final double MIN_WIDTH = 800; // 最小宽度
    private static final double MIN_HEIGHT = 600; // 最小高度

    private Scene scene;

    public static void main(String[] args) {
        launch();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        scene = new Scene(root, null);
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
