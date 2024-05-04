package com.kelton.walkingmanrdm.ui.component;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @Author zhouzekun
 * @Date 2024/5/4 15:41
 */
public class RedisTypeLabel extends Label {

    private String type;

    public RedisTypeLabel(String type) {
        this.type = type;

        this.setPrefHeight(30);
        this.setMaxHeight(30);
        this.setMinHeight(30);
        this.setText(this.type.toUpperCase());
        Font font = Font.font("System", FontWeight.BOLD, 14);
        this.setFont(font);


        String backgroundColor = computeBackGroundColor();
        String textColor = computeTextColor();

        setStyle(String.format("-fx-padding: 0 10 0 10;-fx-background-radius: 10 0 0 10;-fx-background-color: %s; -fx-text-fill: %s;",
                backgroundColor, textColor));

    }

    private String computeTextColor() {
        switch (type) {
            case "string" -> {
                return "#8B5CF6";
            }
            case "list" -> {
                return "#10B981";
            }
            case "set" -> {
                return "#F5A227";
            }
            case "zset" -> {
                return "#EF4444";
            }
            case "hash" -> {
                return "#3B82F6";
            }
            default -> {
                return "#000000";
            }
        }
    }

    private String computeBackGroundColor() {
        switch (type) {
            case "string" -> {
                return "#F2EDFB";
            }
            case "list" -> {
                return "#E3F3EB";
            }
            case "set" -> {
                return "#FDF1DF";
            }
            case "zset" -> {
                return "#FAEAED";
            }
            case "hash" -> {
                return "#E4F0FC";
            }
            default -> {
                return "#ffffff";
            }
        }
    }
}
