package com.kelton.walkingmanrdm.ui.demo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RedisShellSimulator extends Application {

    private TextField inputField; // 命令输入框

    private ScrollPane scrollPane;

    private VBox outputContainer;
    private Jedis jedis;          // Redis客户端对象

    @Override
    public void init() {
        jedis = new Jedis("localhost", 6379); // 初始化Redis客户端
        jedis.auth("0754zzk");
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();

        scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background: black; -fx-control-inner-background: black;-fx-padding: 5");
        outputContainer = new VBox(); // 用于添加输出内容的容器
        scrollPane.setContent(outputContainer);

        HBox commandArea = new HBox();
        Label hostInfo = new Label("127.0.0.1:6379> ");
        inputField = new TextField();
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
        root.getChildren().addAll(scrollPane);

        // 给面板添加鼠标点击事件处理器
        scrollPane.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            inputField.requestFocus(); // 调用requestFocus方法获取焦点
            inputField.end();
        });

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("动态Shell接口");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void processCommand(String commandLine) {
        inputField.clear(); // 清除输入框

        // 显示输入命令
        Text hostPart = new Text("127.0.0.1:6379>  ");
        hostPart.setFill(Color.GREEN); // 设置文本颜色为白色
        Text commandPart = new Text(commandLine);
        commandPart.setFill(Color.WHITE); // 设置文本颜色为白色
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

            String response = sendRedisCommand(redisCommand, args);
            sb.append(response);
        } catch (Exception e) {
            e.printStackTrace();
            sb.append("(error) ERROR unknown command ");
            sb.append("`").append(commandParts[0]).append("`, with args beginning with:");
            for (int i = 1; i < commandParts.length;i++) {
                sb.append("`").append(commandParts[i]).append("`,");
            }
            sb.append("\n");

        }
        Label responseLabel = new Label(sb.toString());
        responseLabel.setWrapText(true);
        outputContainer.getChildren().addAll(responseLabel, node);
        commandEchoLabel.maxWidthProperty().bind(scrollPane.widthProperty().subtract(15));
        responseLabel.maxWidthProperty().bind(scrollPane.widthProperty().subtract(15));
        // 确保滚动条自动滚到底部
        scrollPane.applyCss();
        scrollPane.layout();
        scrollPane.setVvalue(1.0);

    }

    private String sendRedisCommand(String command, String... args) {
        // Convert the command to the Protocol.Command enum
        redis.clients.jedis.commands.ProtocolCommand protocolCommand = redis.clients.jedis.Protocol.Command.valueOf(command.toUpperCase());

        // Send the command using Jedis. This assumes that 'args' can be directly passed to Jedis.
        try {
            Object response = jedis.sendCommand(protocolCommand, args);
            return decodeResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return "(error) " + e.getMessage();
        }
    }

    private String decodeResponse(Object response) {
        if (response instanceof byte[]) {
            return new String((byte[]) response, StandardCharsets.UTF_8);

        } else if (response instanceof List) {
            List<String> ret = ((List<?>) response).stream()
                    .map(this::decodeResponse)
                    .toList();
            StringBuilder retSb = new StringBuilder();
            for(int i = 0; i< ret.size(); i++) {
                retSb.append(i+1).append(") \"").append(ret.get(i)).append("\"\n");
            }
            return retSb.toString();

        } else if (response instanceof Map) {
            return ((Map<?, ?>) response).entrySet().stream()
                    .map(entry -> decodeResponse(entry.getKey()) + ": " + decodeResponse(entry.getValue()))
                    .collect(Collectors.joining(", "));

        } else if (response instanceof String || response instanceof Long) {

            return String.valueOf(response);

        } else {
            return Arrays.toString((byte[]) response);
        }
    }

    @Override
    public void stop() {
        if (jedis != null) {
            jedis.close(); // 程序结束时关闭Redis连接
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}