package com.kelton.walkingmanrdm.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author kelton
 * @Date 2024/4/20 21:55
 * @Version 1.0
 */
@Data
@Accessors(fluent = true, chain = true)
public class RedisConnectionInfo implements Serializable {

    private int id;
    private String title;
    private String host;
    private Integer port;
    private String username;
    private String password;
    private Integer database;
}
