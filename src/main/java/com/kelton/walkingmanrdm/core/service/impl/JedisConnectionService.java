package com.kelton.walkingmanrdm.core.service.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import com.kelton.walkingmanrdm.common.Constants;
import com.kelton.walkingmanrdm.common.util.PropertiesUtils;
import com.kelton.walkingmanrdm.common.util.SqlUtils;
import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.ConnectionService;

import java.util.List;
import java.util.Map;

/**
 * @Author kelton
 * @Date 2024/4/20 22:11
 * @Version 1.0
 */
public class JedisConnectionService implements ConnectionService {

    public JedisConnectionService() {
        if (Boolean.TRUE.toString().equalsIgnoreCase(PropertiesUtils.DEFAULT.getProperty(Constants.DATA_INIT))) {
            this.initDatabase();
            PropertiesUtils.DEFAULT.setProperty(Constants.DATA_INIT, Boolean.FALSE.toString());
        }
    }

    @Override
    public List<RedisConnectionInfo> getConnectListByName(String name) {
        String sql = "select * form rdm_connect_info where name like '%".concat(name).concat("%'");
        return SqlUtils.INSTANT.querySql(sql).stream().map(this::toConnectionInfo).toList();
    }

    @Override
    public List<RedisConnectionInfo> getAllConnectList() {
        String sql = "select * from rdm_connect_info";
        List<Map<String, Object>> result = SqlUtils.INSTANT.querySql(sql);
        return result.stream().map(this::toConnectionInfo).toList();
    }

    @Override
    public RedisConnectionInfo getById(Integer id) {
        var sql = "select * from rdm_connect_info where id ="+ id;
        return SqlUtils.INSTANT.querySql(sql).stream().map(this::toConnectionInfo).findAny().orElse(null);
    }

    @Override
    public void save(RedisConnectionInfo connectInfo) {
        String insertSql = buildInsertSql(connectInfo);
        SqlUtils.INSTANT.exec(insertSql);
    }

    @Override
    public void update(RedisConnectionInfo connectInfo) {
        String updateSql = buildUpdateSql(connectInfo);
        SqlUtils.INSTANT.exec(updateSql);

    }

    @Override
    public void deleteById(Integer id) {
        String sql = "delete from rdm_connect_info where id =".concat(id.toString());
        SqlUtils.INSTANT.exec(sql);
    }

    @Override
    public void initDatabase() {
        var sqlData = ResourceUtil.readUtf8Str("sql/walkingman-rdm.sql");
        SqlUtils.INSTANT.exec(sqlData);

    }

     RedisConnectionInfo toConnectionInfo(Map<String, Object> map) {
        return new RedisConnectionInfo()
                .id((Integer) map.get("id"))
                .title((String) map.get("title"))
                .host((String) map.get("host"))
                .port((Integer) map.get("port"))
                .username((String) map.get("username"))
                .password((String) map.get("password"))
                .database((Integer) map.get("database"))
                ;
    }
}
