package com.kelton.walkingmanrdm.ui.component;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.RedisBasicCommand;
import com.kelton.walkingmanrdm.core.service.RedisStringCommand;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.kordamp.ikonli.coreui.CoreUiBrands;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignM;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author kelton
 * @Date 2024/4/25 16:30
 * @Version 1.0
 */
public class RedisOperaPane extends BorderPane {

    private TextArea valueTextArea;


    private List<VBox> tabList = new ArrayList<>(2);

    private List<Pane> contentPaneList = new ArrayList<>(2);

    // 此方法将创建并返回包含UI控件的BorderPane
    public RedisOperaPane(RedisConnectionInfo connectionInfo) {
        super();
        VBox sidebar = createTab();
        sidebar.setPrefWidth(80);
        sidebar.setStyle("-fx-background-color: #F2F2F2;");

        setLeft(sidebar);

        // redis 操作面板
        StackPane mainPane = craeteMainPane(connectionInfo);
        setCenter(mainPane);

        // 绑定css样式
        getStylesheets().add(getClass().getResource("/css/redisOperaPane.css").toExternalForm());

        // 绑定切换事件
        bindTabWithContent(tabList, contentPaneList);

        // 初始默认激活redis操作面板
        handleClickForTab(tabList.get(0), contentPaneList.get(0));

    }

    private void bindTabWithContent(List<VBox> tabList, List<Pane> contentPaneList) {
        for (int i = 0; i < tabList.size(); i++) {
            VBox tab = tabList.get(i);
            Pane contentPane = contentPaneList.get(i);

            // 添加css class
            tab.getStyleClass().add("tab-vbox");
            // 选中事件处理器
            tab.setOnMouseClicked( e-> {
                handleClickForTab(tab, contentPane);
            });

            tab.setOnMouseEntered( e-> {
                tab.getStyleClass().add("tab-vbox-hover");
            });
            tab.setOnMouseExited( e-> {
                tab.getStyleClass().remove("tab-vbox-hover");
            });
        }
    }

    private void handleClickForTab(VBox tab, Pane contentPane) {
        // 首先移除所有tab的tab-selected样式
        tabList.forEach(t -> {
            t.getStyleClass().remove("tab-selected");
            // icon 失活
            StackPane tNode = ((StackPane) t.getChildren().get(0));
            FontIcon icon = (FontIcon) tNode.getChildren().get(0);
            icon.setIconColor(Paint.valueOf("black"));
        });
        // 添加当前tab选中的样式
        if (!tab.getStyleClass().contains("tab-selected")) {
            tab.getStyleClass().add("tab-selected");
        }
        // icon激活
        StackPane t = ((StackPane) tab.getChildren().get(0));
        FontIcon icon = (FontIcon) t.getChildren().get(0);
        icon.setIconColor(Paint.valueOf("#009688"));
        // 切换事件
        contentPaneList.forEach(pane-> pane.setVisible(false));
        contentPane.setVisible(true);
    }

    private StackPane craeteMainPane(RedisConnectionInfo connectionInfo) {
        HBox redisMainPane = createRedisMainPane(connectionInfo);

        // redis 命令行面板
        BorderPane cmdPane = createCmdPane();

        StackPane mainPane = new StackPane(redisMainPane, cmdPane);
        // 初始设置 redis面板可见，cmd面板不可见
        redisMainPane.setVisible(true);
        cmdPane.setVisible(false);

        contentPaneList.add(redisMainPane);
        contentPaneList.add(cmdPane);
        return mainPane;
    }

    private VBox createTab() {
        StackPane redisStackPane = new StackPane();
        FontIcon redisIcon = new FontIcon(CoreUiBrands.REDIS);
        redisStackPane.getChildren().add(redisIcon);
        redisIcon.setIconSize(25);
        Label redisLabel = new Label("Redis面板");
        redisLabel.setFont(new Font(12));
        VBox redisVBox =  new VBox(redisStackPane, redisLabel);

        redisVBox.setSpacing(8);
        redisVBox.setPadding(new Insets(5));
        redisVBox.setAlignment(Pos.TOP_CENTER);

        StackPane cmdStackPane = new StackPane();
        FontIcon cmdIcon = new FontIcon(MaterialDesignM.MONITOR_EDIT);
        cmdIcon.setIconSize(25);
        cmdStackPane.getChildren().add(cmdIcon);
        Label cmdLabel = new Label("控制台");
        cmdLabel.setFont(new Font(12));
        VBox cmdVBox =  new VBox(cmdStackPane, cmdLabel );
        cmdVBox.setSpacing(8);
        cmdVBox.setPadding(new Insets(5));
        cmdVBox.setAlignment(Pos.TOP_CENTER);

        VBox vBox = new VBox(redisVBox, cmdVBox);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(5));

        tabList.add(redisVBox);
        tabList.add(cmdVBox);

        return vBox;
    }

    private BorderPane createCmdPane() {
        BorderPane cmdPane = new BorderPane();
        cmdPane.setCenter(new Label("redis cmd"));
        return cmdPane;
    }

    private HBox createRedisMainPane(RedisConnectionInfo connectionInfo) {
        // redis主操作面板
        Set<String> keys = RedisBasicCommand.INSTANT.keys(connectionInfo, "*");
        RedisKeyTreeView treeView =  new RedisKeyTreeView(keys, key -> {
            String value = RedisStringCommand.INSTANT.get(connectionInfo, key);
            valueTextArea.setText(value);
        });

        valueTextArea = new TextArea();
        valueTextArea.setEditable(false);
        valueTextArea.setWrapText(true);

        treeView.setPrefWidth(200);
        treeView.setMinWidth(150);

        HBox.setHgrow(valueTextArea, Priority.ALWAYS);  // 设置TextArea占据剩余空间

        HBox redisMainPane = new HBox(treeView, valueTextArea);
        HBox.setHgrow(redisMainPane, Priority.ALWAYS);

        return redisMainPane;
    }

}