package org.lzz.generate.app.datasource;

/**
 * @author zeze.li
 * @version 1.0.0
 * @ClassName DriverWarpper.java
 * @Description TODO
 * @createTime 2020年07月11日 22:43:00
 */
public class DriverWarpper {

    private DataSource dataSource;

    public DriverWarpper(DataSource dataSource) {
        if (dataSource.getType().equals("mysql")) {
            dataSource.setClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://" + dataSource.getHost() + ":" + dataSource.getPort() + "/" + dataSource.getDataBaseName() + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        }
        this.dataSource = dataSource;
    }


    public DataSource getDataSource() {
        return dataSource;
    }
}
