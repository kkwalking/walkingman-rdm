package com.kelton.walkingmanrdm.ui.component;

import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;

/**
 * @Author kelton
 * @Date 2024/4/27 20:42
 * @Version 1.0
 */
public class SvgStackPane extends StackPane {


    private double prefWidth;
    private double prefHeight;

    private String svgPathContent;

    private Region region;

    private String color;

    public SvgStackPane(double prefWidth, double prefHeight, String svgPathContent, String color) {
        super();
        this.prefWidth = prefWidth;
        this.prefHeight = prefHeight;
        this.svgPathContent = svgPathContent;
        this.color = color;
        this.setPrefSize(prefWidth, prefHeight);
        this.setMaxSize(prefWidth, prefHeight);

        region = new Region();
        SVGPath svgPath = new SVGPath();
        svgPath.setContent(svgPathContent);
        region.setShape(svgPath);
        region.setMaxWidth(prefWidth);
        region.setMaxHeight(prefHeight);
        region.setStyle(region.getStyle() + "-fx-background-color: " + color + ";");
        getChildren().add(region);
    }

    public void active() {
        region.setStyle("-fx-background-color: #009688;");
    }

    public void deactive() {
        region.setStyle(region.getStyle() + "-fx-background-color: " + color + ";");
    }



}
