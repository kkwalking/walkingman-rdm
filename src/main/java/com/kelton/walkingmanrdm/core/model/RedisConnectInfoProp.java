package com.kelton.walkingmanrdm.core.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @Author zhouzekun
 * @Date 2024/4/26 10:36
 */
@Data
@Accessors(fluent = true, chain = true)
public class RedisConnectInfoProp {

    private SimpleIntegerProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty host;
    private SimpleStringProperty port;
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleIntegerProperty database;

    public RedisConnectionInfo toInfo() {
        RedisConnectionInfo redisConnectionInfo = new RedisConnectionInfo();
        redisConnectionInfo.id(Objects.isNull(id) ? null :id.get())
                .title(Objects.isNull(title) ? null :title.get())
                .host(Objects.isNull(host) ? null :host.get())
                .port(Objects.isNull(port) ? null :Integer.parseInt(port.get()))
                .username(Objects.isNull(username) ? null :username.get())
                .password(Objects.isNull(password) ? null :password.get())
                .database(Objects.isNull(database) ? null :database.get());

        return redisConnectionInfo;
    }
}
