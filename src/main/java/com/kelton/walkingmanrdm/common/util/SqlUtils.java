package com.kelton.walkingmanrdm.common.util;

import cn.hutool.core.io.FileUtil;
import com.kelton.walkingmanrdm.common.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * @Author kelton
 * @Date 2024/4/20 16:48
 * @Version 1.0
 */
@Slf4j
public class SqlUtils {

    private static Connection conn;

    public static final SqlUtils INSTANT = new SqlUtils();

    private SqlUtils() {
    }

    public static void init() {
        try {
            var derbyDir = new File(Constants.HOME_DERBY_PATH);
            if (FileUtil.isEmpty(derbyDir) || !FileUtil.exist(derbyDir)) {
                var dirCreated = FileUtil.mkdir(derbyDir);
                log.info("derby log dir, created: {}", dirCreated);
                var fileCreated = FileUtil.newFile(Constants.HOME_DERBY_LOG_FILE);
                // no need to create data dir, because jdbc will create automatically
                log.info("derby log file, created: {}", fileCreated);
            }

            System.setProperty("derby.stream.error.file", (Constants.HOME_DERBY_LOG_FILE));

            if (Arrays.stream(Objects.requireNonNull(derbyDir.listFiles())).noneMatch(file -> "data".equalsIgnoreCase(file.getName()))) {
                PropertiesUtils.DEFAULT.setProperty(Constants.DATA_INIT, Boolean.TRUE.toString());
            }

            Class.forName("org.apache.derby.iapi.jdbc.InternalDriver");
            conn = DriverManager.getConnection("jdbc:derby:" + Constants.HOME_DERBY_DATA_FILE + ";create=true");
        } catch (Exception e) {
            log.error("Derby init failed - {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> querySql(String sql) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try (var ps = conn.prepareStatement(sql);
             var rs = ps.executeQuery()) {
            if (rs != null) {
                var md = rs.getMetaData();
                while (rs.next()) {
                    Map<String, Object> rowData = new HashMap<>();
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        rowData.put(md.getColumnName(i).toLowerCase(), rs.getObject(i));
                    }
                    resultList.add(rowData);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultList;
    }

    public boolean exec(String sql) {
        try (var stmt = conn.createStatement()) {
            return stmt.execute(sql);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return false;
    }

}
