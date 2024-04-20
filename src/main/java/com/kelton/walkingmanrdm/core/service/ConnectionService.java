package com.kelton.walkingmanrdm.core.service;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.impl.JedisConnectionService;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @Author kelton
 * @Date 2024/4/20 21:54
 * @Version 1.0
 */
public interface ConnectionService {

    ConnectionService INSTANT = new JedisConnectionService();

    List<RedisConnectionInfo> getConnectListByName(String name);

    List<RedisConnectionInfo> getAllConnectList();

    RedisConnectionInfo getConnect(Object id);

    void save(RedisConnectionInfo connectInfo);

    void update(RedisConnectionInfo connectInfo);

    void delete(Object id);

    void initDatabase();
    /**
     * id,title,host,port,username,password,database
     * @param connInfo
     * @return
     */
    default String buildUpdateSql(RedisConnectionInfo connInfo) {
        return "update rdm_connect_info" +
                " set " +
                "title ='" +
                connInfo.title() +
                "'," +
                "host ='" +
                connInfo.host() +
                "'," +
                "port =" +
                connInfo.port() +
                "," +
                (StringUtils.isNotBlank(connInfo.username()) ? "" : "username ='" + connInfo.username() + "',") +
                (StringUtils.isNotBlank(connInfo.password()) ? "" : "password ='" + connInfo.password()) +
                "' where id =" +
                connInfo.id();

    }

    default String buildInsertSql(RedisConnectionInfo connInfo) {
        return "insert into rdm_connect_info" +
                "(" +
                "title" +
                ", " +"host" +
                ", " +"port" +
                (StringUtils.isNotBlank(connInfo.username()) ? ", username" : "") +
                (StringUtils.isNotBlank(connInfo.password()) ? ", password" : "") +
                ") " +
                "values(" +
                "'" + connInfo.title() + "'" +
                ",'" + connInfo.host() + "'" +
                "," + connInfo.port() +
                (StringUtils.isNotBlank(connInfo.username()) ? ",'" + connInfo.username() + "'" : "") +
                (StringUtils.isNotBlank(connInfo.password()) ? ",'" + connInfo.password() + "'" : "") +
                ")";
    }
}
