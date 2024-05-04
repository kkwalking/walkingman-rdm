package com.kelton.walkingmanrdm.ui.pane;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.RedisBasicCommand;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import redis.clients.jedis.Jedis;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author zhouzekun
 * @Date 2024/5/1 21:32
 */
public class RedisCmdPane extends VBox {

    private TextField inputField; // 命令输入框

    private ScrollPane scrollPane;

    private VBox outputContainer;

    private RedisConnectionInfo connectionInfo;


    public RedisCmdPane(RedisConnectionInfo info) {
        this.connectionInfo = info;
        inputField = new TextField();
        inputField.setFont(Font.font(14));

        scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background: black; -fx-control-inner-background: black;-fx-padding: 5");
        outputContainer = new VBox(); // 用于添加输出内容的容器
        scrollPane.setContent(outputContainer);
        createLayout();
    }

    private void createLayout() {
        HBox commandArea = new HBox();
        Label hostInfo = new Label("127.0.0.1:6379> ");
        hostInfo.setFont(Font.font(14));
        hostInfo.setStyle("-fx-text-fill: green;");
        commandArea.getChildren().addAll(hostInfo, inputField);
        commandArea.setAlignment(Pos.CENTER_LEFT);
        commandArea.minWidthProperty().bind(scrollPane.widthProperty().subtract(30));
        HBox.setHgrow(inputField, Priority.ALWAYS);
        inputField.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        outputContainer.getChildren().add(commandArea);

        // 输入监听器
        inputField.setOnAction(event -> {
            String command = inputField.getText();
            // 输出命令结果的处理
            processCommand(command);
        });

        // 将滚动面板（ScrollPane）的增长优先级设置为ALWAYS，以确保它会随内容增长
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // 把输入字段和输出区布局加到VBox
        getChildren().addAll(scrollPane);

        // 给面板添加鼠标点击事件处理器
        scrollPane.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            inputField.requestFocus(); // 调用requestFocus方法获取焦点
            inputField.end();
        });
    }

    private void processCommand(String commandLine) {
        inputField.clear(); // 清除输入框


        // 显示输入命令
        Text hostPart = new Text("127.0.0.1:6379>  ");
        hostPart.setFill(Color.GREEN); // 设置文本颜色为白色
        hostPart.setFont(Font.font(14));
        Text commandPart = new Text(commandLine);
        commandPart.setFill(Color.WHITE); // 设置文本颜色为白色
        commandPart.setFont(Font.font(14));
        TextFlow commandEchoLabel = new TextFlow(hostPart, commandPart);

        Node node = outputContainer.getChildren().removeLast();
        outputContainer.getChildren().add(commandEchoLabel);


        String[] commandParts = commandLine.split("\\s+"); // Split command line into parts
        StringBuilder sb = new StringBuilder();
        try {
            if (commandParts.length == 0) {
                throw new IllegalArgumentException("No Redis command entered.");
            }

            String redisCommand = commandParts[0];
            String[] args = Arrays.copyOfRange(commandParts, 1, commandParts.length);

            String response = RedisBasicCommand.INSTANT.sendCommand(this.connectionInfo, redisCommand, args);
            sb.append(response);
        } catch (Exception e) {
            sb.append("(error) ERROR unknown command ");
            sb.append("`").append(commandParts[0]).append("`, with args beginning with:");
            for (int i = 1; i < commandParts.length;i++) {
                sb.append("`").append(commandParts[i]).append("`,");
            }
            sb.append("\n");

        }
        Label responseLabel = new Label(sb.toString());
        responseLabel.setFont(Font.font(14));
        responseLabel.setWrapText(true);
        outputContainer.getChildren().addAll(responseLabel, node);
        commandEchoLabel.maxWidthProperty().bind(scrollPane.widthProperty().subtract(15));
        responseLabel.maxWidthProperty().bind(scrollPane.widthProperty().subtract(15));
        // 确保滚动条自动滚到底部
        scrollPane.applyCss();
        scrollPane.layout();
        scrollPane.setVvalue(1.0);

    }


}
