package com.kelton.walkingmanrdm.ui.pane;

import com.kelton.walkingmanrdm.ui.component.NotificationManager;
import com.kelton.walkingmanrdm.ui.global.GlobalObjectPool;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * @Author zhouzekun
 * @Date 2024/5/2 21:20
 */
public class RootStackPane extends StackPane {

    public static TabPane tabPane;

    private Pane maskPane;

    public RootStackPane() {


        tabPane = new TabPane(); // 标签页容器
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        // 主窗口标签页
        Tab homeTab = new Tab("主窗口");

        FontIcon homeIcon = new FontIcon(BoxiconsRegular.HOME);
        homeIcon.setIconSize(18);
        homeIcon.iconColorProperty().bind(
                homeTab.selectedProperty().map(selected -> selected ? Paint.valueOf("white") : Paint.valueOf("black")));
        homeTab.setGraphic(homeIcon);
        HomePane homePane = new HomePane(tabPane);
        homeTab.setContent(homePane);
        homeTab.setClosable(false);

        // 将标签页添加到TabPane中
        tabPane.getTabs().addAll(homeTab);
        this.getChildren().add(tabPane);
        NotificationManager.register(this);
    }

    public void addMaskLayout() {
        if (this.maskPane == null) {
            this.maskPane = createOverlay((Stage) GlobalObjectPool.getBy(GlobalObjectPool.PRIMARY_STAGE));
        }
        this.maskPane.setVisible(true);
        getChildren().add(this.maskPane);
    }

    public void removeMaskLayout() {
        if (this.maskPane != null) {
            maskPane.setVisible(false);
            getChildren().remove(this.maskPane);
        }
    }

    private Pane createOverlay(Stage parentStage) {

        Rectangle rect = new Rectangle(parentStage.getScene().getWidth(), parentStage.getScene().getHeight(), Color.rgb(0, 0, 0, 0.4));
        // 使遮罩层大小与主舞台同步变化
        rect.widthProperty().bind(parentStage.getScene().widthProperty());
        rect.heightProperty().bind(parentStage.getScene().heightProperty());

        // 遮罩层容器
        Pane overlay = new Pane(rect);

        return overlay;
    }
}
