package org.lzz.generate.app.datasource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author zeze.li
 * @version 1.0.0
 * @ClassName DataSourceWarpper.java
 * @Description TODO
 * @createTime 2020年07月11日 15:18:00
 */
@Component
public class DataSourceWarpper {

    private DataSource dataSource;
    private Map<String, String> dataBaseName;
    private Map<String, Connection> map;

    public Map<String, Connection> getMap() {
        return map;
    }

    public void setMap(Map<String, Connection> map) {
        this.map = map;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public DataSourceWarpper() {
        this.map = new HashMap<>();
        this.dataBaseName = new HashMap<>();
    }

    public String createConnection(DataSource dataSource) throws ClassNotFoundException, SQLException {

        try {
            this.dataSource = dataSource;
            String sourceId = UUID.randomUUID().toString();
            Class.forName(dataSource.getClassName());
            Connection connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUserName(), dataSource.getPassWord());
//        connection.setAutoCommit(false);
            map.put(sourceId, connection);
            dataBaseName.put(sourceId, dataSource.getDataBaseName());
            return sourceId;
        } catch (Exception ex) {
            throw ex;
        }
    }


    public DatabaseMetaData getDatabaseMetaData(String sourceId) throws SQLException, ClassNotFoundException {


        if (this.map.size() == 0 || this.map == null || !map.containsKey(sourceId))
            throw new RuntimeException("未登录");
        return this.getConnection(sourceId).getMetaData();
    }

    public Connection getConnection(String sourceId) {

        return this.map.get(sourceId);
    }

    public String getDataBaseName(String sourceId) throws SQLException {
        if (this.dataBaseName.containsKey(sourceId)) {
            return this.dataBaseName.get(sourceId);
        }
        return null;
    }
}
