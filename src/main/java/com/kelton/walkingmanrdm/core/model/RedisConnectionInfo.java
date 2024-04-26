package com.kelton.walkingmanrdm.core.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author kelton
 * @Date 2024/4/20 21:55
 * @Version 1.0
 */
@Data
@Accessors(fluent = true, chain = true)
public class RedisConnectionInfo implements Serializable {

    private Integer id;
    private String title;
    private String host;
    private Integer port;
    private String username;
    private String password;
    private Integer database;

    public static List<RedisConnectInfoProp> convertToPropList(List<RedisConnectionInfo> connectionList) {
        return connectionList.stream().map(RedisConnectionInfo::toProp).toList();
    }

    public static RedisConnectInfoProp toProp(RedisConnectionInfo info) {
        RedisConnectInfoProp prop = new RedisConnectInfoProp();
        prop.id(new SimpleIntegerProperty(info.id))
                .title(new SimpleStringProperty(info.title))
                .host(new SimpleStringProperty(info.host))
                .port(new SimpleStringProperty(info.port.toString()))
                .password(new SimpleStringProperty(info.password))
        ;
        return prop;
    }
}
