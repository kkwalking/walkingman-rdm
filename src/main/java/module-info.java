open module com.zzk.filehelper {
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

    exports com.kelton.walkingmanrdm;
}