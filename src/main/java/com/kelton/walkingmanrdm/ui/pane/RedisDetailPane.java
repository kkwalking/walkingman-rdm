package com.kelton.walkingmanrdm.ui.pane;

import com.kelton.walkingmanrdm.common.util.JsonUtil;
import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.*;
import com.kelton.walkingmanrdm.ui.component.RedisTypeLabel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.javafx.FontIcon;
import redis.clients.jedis.resps.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * redis详细操作面板
 *
 * @Author zhouzekun
 * @Date 2024/5/3 22:57
 */
public class RedisDetailPane extends BorderPane {

    private String key;

    private RedisConnectionInfo connectionInfo;

    private TextArea valuePane;

    private VBox headerPane;

    private String type;

    private Long ttl = (long) -1;

    public RedisDetailPane(RedisConnectionInfo connectionInfo, String key) {
        this.key = key;
        this.connectionInfo = connectionInfo;
        this.valuePane = new TextArea();
        this.valuePane.setWrapText(true);
        this.valuePane.setEditable(false);
        this.valuePane.setPrefSize(200,200);

        this.type = RedisBasicCommand.INSTANT.type(connectionInfo, key);
        this.ttl = RedisBasicCommand.INSTANT.ttl(connectionInfo, key);
        fillText();
        setCenter(valuePane);

        Label typeLabel = new Label();
        typeLabel.setFont(Font.font(18));
        headerPane = new VBox();
        headerPane.getStyleClass().add("headerPane");
        RedisTypeLabel redisTypeLabel = new RedisTypeLabel(type);

        Label textArea = new Label(key);
        textArea.setPrefWidth(100);
        textArea.setMinHeight(28);
        textArea.setMaxHeight(28);
        textArea.setAlignment(Pos.CENTER_LEFT);
        textArea.getStyleClass().add("key-text");

        FontIcon reLoad = new FontIcon(AntDesignIconsOutlined.RELOAD);
        reLoad.getStyleClass().add("reload-icon");
        reLoad.setIconSize(18);
        StackPane reloadPane = new StackPane(reLoad);
        reloadPane.getStyleClass().add("reloadPane");


        FontIcon copyIcon = new FontIcon(BoxiconsRegular.PASTE);
        copyIcon.getStyleClass().add("copy-icon");
        copyIcon.setIconSize(18);
        StackPane copyPane = new StackPane(copyIcon);
            copyPane.getStyleClass().add("copyPane");


        Region spacing = new Region();
        HBox keyHbox = new HBox(textArea, spacing, reloadPane);
        keyHbox.getStyleClass().add("keyHbox");
        keyHbox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(spacing, Priority.ALWAYS);
        keyHbox.setPrefWidth(200);

        HBox keyOperaHBox = new HBox(redisTypeLabel, keyHbox, copyPane);

        HBox ttlHbox = new HBox();
        FontIcon clockIcon = new FontIcon(AntDesignIconsOutlined.CLOCK_CIRCLE);
        clockIcon.setIconSize(18);
        Label ttlLabel = new Label();
        ttlLabel.setText(ttl.toString());
        ttlLabel.setFont(new Font(13));
        ttlHbox.getChildren().addAll(clockIcon, ttlLabel);
        ttlHbox.getStyleClass().add("ttlHbox");

        keyOperaHBox.setAlignment(Pos.CENTER_LEFT);

        HBox firstRow = new HBox(keyOperaHBox, ttlHbox);
        firstRow.setAlignment(Pos.CENTER_LEFT);
        firstRow.getStyleClass().add("firstRow");

        headerPane.getChildren().addAll(firstRow);
        setTop(headerPane);

        getStylesheets().add(this.getClass().getResource("/css/redisDetailPane.css").toExternalForm());

    }

    public void refresh() {
        fillText();
    }

    private void fillText() {
        switch (type) {
            case "string" -> {
                valuePane.setText(RedisStringCommand.INSTANT.get(connectionInfo, key));
            }
            case "set" -> {
                valuePane.setText(String.join(",", RedisSetCommand.INSTANT.smembers(connectionInfo, key)));
            }

            case "list" -> {
                valuePane.setText(String.join("\r\n", RedisListCommand.INSTANT.lrange(connectionInfo, key, 0, -1)));
            }

            case "zset" -> {
                List<Tuple> membersWithScores = RedisZSetCommand.INSTANCE.zrangeWithScores(connectionInfo, key, 0, -1);
                String text = membersWithScores.stream().map(e -> e.getElement() + "-" + e.getScore()).collect(Collectors.joining("\r\n"));
                valuePane.setText(text);
            }
            case "hash" -> {
                Map<String, String> hgetall = RedisHashCommand.INSTANT.hgetall(connectionInfo, key);
                valuePane.setText(JsonUtil.toJson(hgetall));
            }
            default -> {
                valuePane.setText("暂不支持该类型");
            }
        }
    }
}
