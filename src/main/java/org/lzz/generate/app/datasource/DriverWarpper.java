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

    public DriverWarpper(String type) {
        dataSource = new DataSource();
        if (type == "mysql") {
            dataSource.setClassName("com.mysql.jdbc.Driver");
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
