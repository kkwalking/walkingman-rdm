package com.kelton.walkingmanrdm.ui.svg;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * @Author zhouzekun
 * @Date 2024/4/29 11:17
 */
public class RedisIcon extends Button{

    private Type type;

    private FontIcon icon;

    public RedisIcon(Type type) {
        this.type = type;
        icon = new FontIcon();
        icon.setIconSize(15);
        setPrefSize(15, 15);
        setMaxSize(15, 15);
        switch (type) {
            case Folder -> icon.setIconCode(MyIcon.REDIS_FOLDER);
            case String -> icon.setIconCode(MyIcon.REDIS_STRING);
            case Set -> icon.setIconCode(MyIcon.REDIS_SET);
            case Hash -> icon.setIconCode(MyIcon.REDIS_HASH);
            case List -> icon.setIconCode(MyIcon.REDIS_LIST);
            case Zset -> icon.setIconCode(MyIcon.REDIS_ZSET);
        }

        deactive();
        setGraphic(icon);
    }

    public void deactive() {
        switch (type) {

            case Folder -> {
                icon.setIconColor(Paint.valueOf("black"));
                setStyle("-fx-border-radius: 5;-fx-border-width: 0;-fx-border-color: transparent;-fx-background-color: transparent;");
            }
            case String -> {
                icon.setIconColor(Paint.valueOf("#8B5CF6"));
                setStyle("-fx-border-radius: 5;-fx-border-width: 0;-fx-background-color: #e7dcfd;");
            }
            case Set -> {
                icon.setIconColor(Paint.valueOf("#F59E0B"));
                setStyle("-fx-border-radius: 5;-fx-border-width: 0;-fx-background-color: #fae2bf;");
            }
            case Hash -> {
                icon.setIconColor(Paint.valueOf("#3B82F6"));
                setStyle("-fx-border-radius: 5;-fx-border-width: 0;-fx-background-color: #afd7ff;");
            }
            case List -> {
                icon.setIconColor(Paint.valueOf("#10B981"));
                setStyle("-fx-border-radius: 5;-fx-border-width: 0;-fx-background-color: #E3F3EB;");
            }
            case Zset -> {
                icon.setIconColor(Paint.valueOf("#EF4444"));
                setStyle("-fx-border-radius: 5;-fx-border-width: 0;-fx-background-color: #fca9b8;");
            }
        }
    }

    public void active() {
        switch (type) {
            case String -> {
                icon.setIconColor(Paint.valueOf("#EBC8F8"));
                setStyle("-fx-border-radius: 5;-fx-border-color: #E0E0E6;-fx-border-width: 0;-fx-background-color: #8B5CF6;");
            }
            case Set -> {
                icon.setIconColor(Paint.valueOf("#FDF1DF"));
                setStyle("-fx-border-radius: 5;-fx-border-color: #E0E0E6;-fx-border-width: 0;-fx-background-color: #F59E0B;");
            }
            case Hash -> {
                icon.setIconColor(Paint.valueOf("#E4F0FC"));
                setStyle("-fx-border-radius: 5;-fx-border-color: #E0E0E6;-fx-border-width: 0;-fx-background-color: #3B82F6;");
            }
            case List -> {
                icon.setIconColor(Paint.valueOf("#E3F3DA"));
                setStyle("-fx-border-radius: 5;-fx-border-color: #E0E0E6;-fx-border-width: 0;-fx-background-color: #10B981;");
            }
            case Zset -> {
                icon.setIconColor(Paint.valueOf("#FAEAED"));
                setStyle("-fx-border-radius: 5;-fx-border-color: #E0E0E6;-fx-border-width: 0;-fx-background-color: #EF4444;");
            }
        }
    }

    public enum Type {
        Folder,
        String,
        Set,
        Hash,
        List,
        Zset;
    }
}
