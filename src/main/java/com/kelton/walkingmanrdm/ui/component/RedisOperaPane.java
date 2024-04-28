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
import javafx.scene.text.Font;

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
        sidebar.setStyle("-fx-background-color: #FCFCFD;");  // 仅示例，设置背景颜色以便于区分

        setLeft(sidebar);

        // redis 操作面板
        StackPane mainPane = craeteMainPane(connectionInfo);
        setCenter(mainPane);

        // 绑定css样式
        getStylesheets().add(getClass().getResource("/css/redisOperaPane.css").toExternalForm());

        // 绑定切换事件
        bindTabWithContent(tabList, contentPaneList);
    }

    private void bindTabWithContent(List<VBox> tabList, List<Pane> contentPaneList) {
        for (int i = 0; i < tabList.size(); i++) {
            VBox tab = tabList.get(i);
            Pane contentPane = contentPaneList.get(i);

            // 添加css class
            tab.getStyleClass().add("tab-vbox");
            // 选中事件处理器
            tab.setOnMouseClicked( e-> {
                // 首先移除所有tab的tab-selected样式
                tabList.forEach( t -> {
                    t.getStyleClass().remove("tab-selected");
                    // icon 失活
                    SvgStackPane tNode = ((SvgStackPane) t.getChildren().get(0));
                    tNode.deactive();
                });
                // 添加当前tab选中的样式
                if (!tab.getStyleClass().contains("tab-selected")) {
                    tab.getStyleClass().add("tab-selected");
                }
                // icon激活
                SvgStackPane node = ((SvgStackPane) tab.getChildren().get(0));
                node.active();
                // 切换事件
                contentPaneList.forEach( pane-> pane.setVisible(false));
                contentPane.setVisible(true);
            });

            tab.setOnMouseEntered( e-> {
                tab.getStyleClass().add("tab-vbox-hover");
            });
            tab.setOnMouseExited( e-> {
                tab.getStyleClass().remove("tab-vbox-hover");
            });
        }
    }

    private StackPane craeteMainPane(RedisConnectionInfo connectionInfo) {
        HBox redisMainPane = createRedisMainPane(connectionInfo);

        // redis 命令行面板
        BorderPane cmdPane = createCmdPane();

        StackPane mainPane = new StackPane(redisMainPane, cmdPane);
        redisMainPane.setVisible(true);
        cmdPane.setVisible(false);

        contentPaneList.add(redisMainPane);
        contentPaneList.add(cmdPane);
        return mainPane;
    }

    private VBox createTab() {
        String redisSvg = "M1194.332493 628.481787c-0.485319 11.386328-15.567537 24.149282-46.55445 40.319968-63.765771 33.189513-394.054451 169.044974-464.319529 205.670219-70.336242 36.624078-109.349582 36.282255-164.901097 9.706378-55.549182-26.546711-406.967901-168.563155-470.285685-198.764924C16.650172 670.292711 0.522651 657.523924-0.001167 645.502948v120.773242c0 12.058308 16.612839 24.827095 48.271732 39.983978 63.318951 30.2776 414.812334 172.21938 470.290352 198.763758 55.551515 26.58171 94.566022 26.9177 164.901097-9.74371 70.29891-36.624078 400.550258-172.443373 464.319529-205.670219 32.405537-16.874165 46.74111-30.016274 46.741111-41.925254 0-11.236999 0.037332-119.05596 0.037332-119.05596-0.037332-0.037332-0.186661-0.037332-0.261326-0.116664z m-0.037333-196.858647c-0.559983 11.386328-15.567537 24.080451-46.479784 40.282635-63.765771 33.189513-394.054451 169.044974-464.31953 205.670219-70.336242 36.624078-109.349582 36.282255-164.901097 9.74371S111.626848 518.756549 48.309064 488.516282C16.687504 473.436396 0.559983 460.627944 0.037332 448.6443v120.773243c0 12.058308 16.612839 24.789763 48.271732 39.909313 63.317784 30.2776 414.73767 172.21938 470.289185 198.762591 55.551515 26.58171 94.566022 26.9177 164.901097-9.706378 70.29891-36.624078 400.550258-172.480706 464.31953-205.669052 32.405537-16.916164 46.74111-30.053607 46.74111-41.962586 0-11.236999 0.037332-119.05596 0.037332-119.05596-0.037332-0.037332-0.186661 0-0.298658-0.037332z m0-204.214262c0.597316-12.132973-15.268879-22.811155-47.226429-34.532307-62.123153-22.739991-390.392394-153.406273-453.264524-176.478754-62.869798-22.997816-88.442371-22.064511-162.325174 4.442535C457.595062 47.382396 108.080287 184.473321 45.882469 208.81393c-31.098909 12.249636-46.330456 23.5578-45.807805 35.582276v120.770909c0 12.058308 16.612839 24.789763 48.271732 39.909314 63.317784 30.2776 414.73767 172.294044 470.289185 198.799923s94.566022 26.880368 164.901098-9.74371c70.29891-36.624078 400.550258-172.480706 464.319529-205.670219 32.405537-16.916164 46.74111-30.053607 46.74111-41.962586 0-11.199667 0.037332-119.05596 0.037332-119.055961z m-766.529711 114.314768l276.826604-42.52257-83.625348 122.600189z m612.192467-110.432217l-181.434606 71.640537-163.631802-64.661745 181.29461-71.680202zM559.366203 112.681122l-26.76837-49.392865 83.514517 32.665695 78.732492-25.795399-21.317866 51.034316 80.229282 30.053606-103.488424 10.75168-23.184477 55.738177-37.448887-62.160486-119.494614-10.750513z m-206.227868 69.62693c81.72257 0 147.952268 25.685736 147.952268 57.344628s-66.264697 57.344628-147.952268 57.344629-147.9896-25.723069-147.989601-57.344629c0-31.658892 66.264697-57.344628 147.989601-57.344628z";
        SvgStackPane redisSvgNode = new SvgStackPane(25, 25, redisSvg, "black");
        Label redisLabel = new Label("Redis面板");
        redisLabel.setFont(new Font(12));
        VBox redisVBox =  new VBox(redisSvgNode, redisLabel);
        redisVBox.setSpacing(8);
        redisVBox.setPadding(new Insets(5));
        redisVBox.setAlignment(Pos.TOP_CENTER);

        String cmdSvg = "M909.7 132.6v620H114.3v-620h795.4m50-50H64.3v720h895.5v-720h-0.1zM317.9 208.3L150.8 407.4l0.2 0.2 167.1 199.2 38.3-32.1-140.3-167.3 140.2-167-38.4-32.1z m389 0l-38.3 32.1 140.2 167-140.4 167.3 38.3 32.1 167.1-199.2 0.2-0.2-167.1-199.1z m-143.5 1.2l-149 403.4 46.9 17.3 149-403.4-46.9-17.3zM957 855.8H67v50h890v-50z";
        SvgStackPane cmdSvgNode = new SvgStackPane(25, 25, cmdSvg, "black");
        Label cmdLabel = new Label("控制台");
        cmdLabel.setFont(new Font(12));
        VBox cmdVBox =  new VBox(cmdSvgNode, cmdLabel );
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

        HBox.setHgrow(treeView, Priority.SOMETIMES);  // 设置树形列表占据主窗口的30%
        HBox.setHgrow(valueTextArea, Priority.ALWAYS);  // 设置TextArea占据剩余空间
        HBox.setHgrow(treeView, Priority.ALWAYS);

        HBox redisMainPane = new HBox(treeView, valueTextArea);
        HBox.setHgrow(redisMainPane, Priority.ALWAYS);

        return redisMainPane;
    }

}