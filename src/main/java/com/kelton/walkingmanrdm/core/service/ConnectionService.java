package com.kelton.walkingmanrdm.core.service;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.impl.JedisConnectionService;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @Author kelton
 * @Date 2024/4/20 21:54
 * @Version 1.0
 */
public interface ConnectionService {

    ConnectionService INSTANT = new JedisConnectionService();

    List<RedisConnectionInfo> getConnectListByName(String name);

    List<RedisConnectionInfo> getAllConnectList();

    RedisConnectionInfo getById(Integer id);

    void save(RedisConnectionInfo connectInfo);

    void update(RedisConnectionInfo connectInfo);

    void deleteById(Integer id);

    void initDatabase();
    /**
     * id,title,host,port,username,password,database
     * @param connInfo
     * @return
     */
    default String buildUpdateSql(RedisConnectionInfo connInfo) {
        boolean isFirstColumn = true;
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("update rdm_connect_info ");
        if (StringUtils.isNotBlank(connInfo.title())) {
            if (isFirstColumn) {
                sqlBuilder.append("set ");
                sqlBuilder.append(String.format("title='%s'",connInfo.title()));
                isFirstColumn = false;
            } else {
                sqlBuilder.append(String.format(",title='%s'",connInfo.title()));
            }
        }
        if (StringUtils.isNotBlank(connInfo.host())) {
            if (isFirstColumn) {
                sqlBuilder.append("set ");
                sqlBuilder.append(String.format("host='%s'",connInfo.host()));
                isFirstColumn = false;
            } else {
                sqlBuilder.append(String.format(",host='%s'",connInfo.host()));
            }
        }
        if (!Objects.isNull(connInfo.port())) {
            if (isFirstColumn) {
                sqlBuilder.append("set ");
                sqlBuilder.append(String.format("port='%s'",connInfo.port()));
                isFirstColumn = false;
            } else {
                sqlBuilder.append(String.format(",port='%s'",connInfo.port()));
            }
        }
        if (StringUtils.isNotBlank(connInfo.username())) {
            if (isFirstColumn) {
                sqlBuilder.append("set ");
                sqlBuilder.append(String.format("username='%s'",connInfo.username()));
                isFirstColumn = false;
            } else {
                sqlBuilder.append(String.format(",username='%s'",connInfo.username()));
            }
        }
        if (StringUtils.isNotBlank(connInfo.password())) {
            if (isFirstColumn) {
                sqlBuilder.append("set ");
                sqlBuilder.append(String.format("password='%s'",connInfo.password()));
                isFirstColumn = false;
            } else {
                sqlBuilder.append(String.format(",password='%s'",connInfo.password()));
            }
        }
        sqlBuilder.append(String.format(" where id=%s", connInfo.id()));
        return sqlBuilder.toString();
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
