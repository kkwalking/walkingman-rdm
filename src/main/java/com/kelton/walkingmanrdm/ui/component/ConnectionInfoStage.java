package com.kelton.walkingmanrdm.ui.component;

import cn.hutool.core.util.StrUtil;
import com.kelton.walkingmanrdm.common.util.SqlUtils;
import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.ConnectionService;
import com.kelton.walkingmanrdm.core.service.RedisBasicCommand;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

/**
 * @Author zhouzekun
 * @Date 2024/4/24 10:43
 */
public class ConnectionInfoStage extends Stage {

    // 添加字段来持有你的输入控件的引用
    private TextField nameField;
    private TextField hostField;
    private TextField portField;
    private PasswordField passField;

    private Label promptLabel;

    private boolean promptIsPlay;


    public ConnectionInfoStage() {

        // 顶部label
        // Label topLabel = new Label("Redis Connection Details");

        // 提示Label初始化
        promptLabel = new Label();
        promptLabel.setTextFill(Color.RED);
        promptLabel.setVisible(false);
        promptLabel.setMaxWidth(Double.MAX_VALUE);
        promptLabel.setAlignment(Pos.CENTER);



        // 中间连接信息区域
        VBox middlePane = new VBox(20);

        this.nameField = new TextField();
        nameField.setPromptText("connection name");
        Label connLabel = new Label("连接名");
        connLabel.setAlignment(Pos.CENTER);
        connLabel.setTextAlignment(TextAlignment.CENTER);
        // 创建一个容器来包含原标签和红星
        HBox nameLabelContainer = new HBox();
        nameLabelContainer.getChildren().addAll(connLabel, createRequiredLabel());
        nameLabelContainer.setAlignment(Pos.CENTER_LEFT);

        HBox nameHbox = new HBox();
        nameHbox.getChildren().addAll(nameLabelContainer, nameField);
        nameHbox.setSpacing(20);
        nameHbox.setPadding(new Insets(0, 20, 0, 10));
        nameHbox.setAlignment(Pos.CENTER_RIGHT);


        this.hostField = new TextField();
        hostField.setPromptText("Host");
        Label hostLabel = new Label("连接名");
        hostLabel.setAlignment(Pos.CENTER);
        hostLabel.setTextAlignment(TextAlignment.CENTER);
        // 创建一个容器来包含原标签和红星
        HBox hostLabelContainer = new HBox();
        hostLabelContainer.getChildren().addAll(hostLabel, createRequiredLabel());
        hostLabelContainer.setAlignment(Pos.CENTER_LEFT);
        HBox hostHBox = new HBox();
        hostHBox.getChildren().addAll(hostLabelContainer, hostField);

        hostHBox.setSpacing(20);
        hostHBox.setPadding(new Insets(0, 20, 0, 10));
        hostHBox.setAlignment(Pos.CENTER_RIGHT);


        this.portField = new TextField();
        portField.setPromptText("Port");
        Label postLabel = new Label("端口");
        postLabel.setAlignment(Pos.CENTER);
        postLabel.setTextAlignment(TextAlignment.CENTER);
        HBox portLabelContainer = new HBox(postLabel, createRequiredLabel());
        portLabelContainer.setAlignment(Pos.CENTER_LEFT);
        HBox portHBox = new HBox(portLabelContainer,portField);
        portHBox.setSpacing(20);
        portHBox.setPadding(new Insets(0, 20, 0, 10));
        portHBox.setAlignment(Pos.CENTER_RIGHT);

        HBox passHBox = new HBox(new Label("密码  "));
        this.passField = new PasswordField();
        passField.setPromptText("Password");
        passHBox.getChildren().add(passField);
        passHBox.setSpacing(20);
        passHBox.setPadding(new Insets(0, 20, 0, 10));
        passHBox.setAlignment(Pos.CENTER_RIGHT);

        middlePane.getChildren().addAll(nameHbox,hostHBox, portHBox, passHBox);
        middlePane.setPadding(new Insets(10));


        // 底部按钮区域
        Button connectBtn = new Button("Connect");
        Button saveBtn = new Button("Save");
        HBox bottomHBox = new HBox(saveBtn, connectBtn);
        bottomHBox.setSpacing(30);
        bottomHBox.setAlignment(Pos.CENTER);

        // 在按钮上方添加提示Label
        VBox buttonAndPromptLayout = new VBox(10); // 间距为10的垂直容器
        buttonAndPromptLayout.getChildren().addAll(promptLabel, bottomHBox);
        buttonAndPromptLayout.setAlignment(Pos.CENTER);

        // 将控件添加到布局容器
        VBox layout = new VBox(10); // 10为元素间的间距
        layout.getChildren().addAll(middlePane , buttonAndPromptLayout);
        layout.setAlignment(Pos.CENTER);

        // 设置并显示窗口
        Scene scene = new Scene(layout, 300, 250); // 这里设置新窗口的大小
        this.setScene(scene);
        this.setTitle("New Connection Info");
        this.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/img/logo.png")).toExternalForm()));

//        点击事件处理
        saveBtn.setOnMouseClicked(event -> {

            String checkRequired = checkRequired();
            if (StrUtil.isNotBlank(checkRequired)) {
                showFillRequiredInfoPrompt(checkRequired);
            } else {
                RedisConnectionInfo connInfo = createConnInfo();
                saveConnInfo(connInfo);
                this.close();
            }
        });
        connectBtn.setOnMouseClicked( event -> {
            RedisConnectionInfo connInfo = createConnInfo();
            String checkRequired = checkRequired();
            if (StrUtil.isNotBlank(checkRequired)) {
                showFillRequiredInfoPrompt(checkRequired);
            } else {
                testConnInfo(connInfo);
            }
        });

    }

    /**
     * 生成一个红色必填符号*
     * @return
     */
    private Label createRequiredLabel() {
        Label requiredLabel = new Label("*");
        requiredLabel.setTextFill(Color.RED); // 设置文本颜色为红色
        requiredLabel.setFont(new Font("Arial", 20)); // 可以根据需要调整字体和大小
        return requiredLabel;
    }

    private void testConnInfo(RedisConnectionInfo connInfo) {
        if (RedisBasicCommand.INSTANT.testConnect(connInfo)) {
            System.out.println("连接成功！");
        } else {
            System.out.println("连接失败");
        }
    }

    private void saveConnInfo(RedisConnectionInfo connInfo) {
        SqlUtils.INSTANT.exec(ConnectionService.INSTANT.buildInsertSql(connInfo));
    }

    private RedisConnectionInfo createConnInfo() {
        String name = nameField.getText();
        String host = hostField.getText();
        String port = portField.getText();
        String password = passField.getText();

        RedisConnectionInfo redisConnectionInfo = new RedisConnectionInfo();
        redisConnectionInfo.title(name).host(host).password(password);
        if (StrUtil.isNotBlank(port)) {
            redisConnectionInfo.port(Integer.valueOf(port));
        }

        return redisConnectionInfo;
    }

    public void clearData() {
        if (nameField != null) {
            nameField.setText("");  // 清空连接名字段
        }
        if (hostField != null) {
            hostField.setText("");  // 清空主机字段
        }
        if (portField != null) {
            portField.setText("");  // 清空端口字段
        }
        if (passField != null) {
            passField.clear();     // 清空密码字段
        }
    }

    private String checkRequired() {
        StringBuilder sb = new StringBuilder();
        // 检查必填项
        if (StrUtil.isBlank(nameField.getText())) {
            sb.append("请填写连接名;");
        }
        if (StrUtil.isBlank(hostField.getText())) {
            sb.append("请填写主机;");
        }
        if (StrUtil.isBlank(portField.getText())) {
            sb.append("请填写端口;");
        }
        return sb.toString();
    }

    private void showFillRequiredInfoPrompt(String prompt) {
        if (promptIsPlay) {
            return;
        }
        promptLabel.setText(prompt);
        // 显示提示
        promptLabel.setVisible(true);
        promptLabel.setOpacity(1);

        // 创建渐变消失动画
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), promptLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setDelay(Duration.seconds(1)); // 1秒后开始消失
        promptIsPlay = true;
        fadeOut.play();

        fadeOut.setOnFinished(event -> {
            promptLabel.setVisible(false);
            promptIsPlay = false;
        }); // 动画完成后不显示
    }
}
