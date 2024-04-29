import com.kelton.walkingmanrdm.ui.svg.MyIconHandler;

open module com.kelton.walkingmanrdm {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;
    requires static lombok;
    requires cn.hutool;
    requires java.sql;
    requires java.base;
    requires org.apache.commons.pool2;
    requires redis.clients.jedis;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.boxicons;
    requires org.kordamp.ikonli.coreui;
    requires org.kordamp.ikonli.materialdesign2;

    provides org.kordamp.ikonli.IkonHandler with MyIconHandler;

    exports com.kelton.walkingmanrdm;
    exports com.kelton.walkingmanrdm.core.model;
}