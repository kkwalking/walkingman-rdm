package com.kelton.walkingmanrdm.ui.demo;

import com.kelton.walkingmanrdm.ui.icon.RedisIcon;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * @Author zhouzekun
 * @Date 2024/4/29 9:51
 */
public class IconApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {


        BorderPane root = new BorderPane();

        RedisIcon folderIcon = new RedisIcon(RedisIcon.Type.Folder);
        RedisIcon stringIcon = new RedisIcon(RedisIcon.Type.String);
        RedisIcon hashIcon = new RedisIcon(RedisIcon.Type.Hash);
        RedisIcon zsetIcon = new RedisIcon(RedisIcon.Type.Zset);
        RedisIcon setIcon = new RedisIcon(RedisIcon.Type.Set);
        RedisIcon listIcon = new RedisIcon(RedisIcon.Type.List);

        HBox hBox = new HBox(folderIcon, stringIcon, hashIcon, zsetIcon, setIcon, listIcon);
        hBox.setSpacing(10);

        stringIcon.setOnMouseEntered( e-> {
            stringIcon.active();
        });

        stringIcon.setOnMouseExited( e-> {
            stringIcon.deactive();
        });

        hashIcon.setOnMouseEntered( e-> {
            hashIcon.active();
        });

        hashIcon.setOnMouseExited( e-> {
            hashIcon.deactive();
        });


        zsetIcon.setOnMouseEntered( e-> {
            zsetIcon.active();
        });

        zsetIcon.setOnMouseExited( e-> {
            zsetIcon.deactive();
        });

        setIcon.setOnMouseEntered( e-> {
            setIcon.active();
        });

        setIcon.setOnMouseExited( e-> {
            setIcon.deactive();
        });


        listIcon.setOnMouseEntered( e-> {
            listIcon.active();
        });

        listIcon.setOnMouseExited( e-> {
            listIcon.deactive();
        });


        root.setCenter(hBox);
        Scene sc = new Scene(root, 300, 300);

        stage.setScene(sc);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
