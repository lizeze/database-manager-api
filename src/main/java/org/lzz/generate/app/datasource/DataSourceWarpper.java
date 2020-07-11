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
    }

    public Connection createConnection(DataSource dataSource) throws ClassNotFoundException, SQLException {

        dataSource.setClassName("dm.jdbc.driver.DmDriver");
        dataSource.setPassWord("dmserver2020");
        dataSource.setUrl("jdbc:dm://localhost:5236");
        dataSource.setUserName("LZZ");
        dataSource.setDataBaseName(dataSource.getUserName());
        this.dataSource = dataSource;
        if (map.containsKey("a")) return map.get(dataSource.getSourceId());

        Class.forName(dataSource.getClassName());
        Connection connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUserName(), dataSource.getPassWord());
        map.put("a", connection);
        return connection;
    }


    public DatabaseMetaData getDatabaseMetaData() throws SQLException, ClassNotFoundException {


        if (this.map.size() == 0 || this.map == null)
            return this.createConnection(new DataSource()).getMetaData();
        return this.getConnection("").getMetaData();
    }

    public Connection getConnection(String key) {

        return this.map.get("a");
    }

}
