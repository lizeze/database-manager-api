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

        String dataSourceType = dataSource.getType();
        switch (dataSourceType) {
            case "mysql":
                dataSource.setClassName("com.mysql.jdbc.Driver");
                dataSource.setUrl("jdbc:mysql://" + dataSource.getHost() + ":" + dataSource.getPort() + "/" + dataSource.getDataBaseName() + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
                break;
            case "dm":
                dataSource.setClassName("dm.jdbc.driver.DmDriver");
                dataSource.setUrl("jdbc:dm://" + dataSource.getHost() + ":" + dataSource.getPort());
                dataSource.setDataBaseName(dataSource.getUserName());
                break;

        }

        this.dataSource = dataSource;
    }


    public DataSource getDataSource() {
        return dataSource;
    }
}
