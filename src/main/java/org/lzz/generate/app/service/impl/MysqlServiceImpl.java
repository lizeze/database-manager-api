package org.lzz.generate.app.service.impl;

import org.lzz.generate.app.datasource.DataSource;
import org.lzz.generate.app.datasource.DataSourceWarpper;
import org.lzz.generate.app.service.BaseService;
import org.lzz.generate.app.vo.ColumnVo;
import org.lzz.generate.app.vo.SqlVo;
import org.lzz.generate.app.vo.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zeze.li
 * @version 1.0.0
 * @ClassName MysqlServiceImpl.java
 * @Description TODO
 * @createTime 2020年07月11日 23:10:00
 */
@Service("mysql")
public class MysqlServiceImpl  extends  BaseServiceImpl {

    private final String DRIVER = "com.mysql.jdbc.Driver";


    @Autowired
    private DataSourceWarpper dataSourceWarpper;


    @Override
    public List<String> getPrimaryKeys(String sourceId, String tableName) throws SQLException, ClassNotFoundException {

        List<String> list = new ArrayList<>();
        ResultSet primaryKeys = dataSourceWarpper.getDatabaseMetaData(sourceId).getPrimaryKeys(dataSourceWarpper.getDataSource().getDataBaseName(), dataSourceWarpper.getDataSource().getDataBaseName(), tableName);
        while (primaryKeys.next()) {
            list.add(primaryKeys.getString("column_name"));
        }
        return list;
    }

    @Override
    public List<TableVo> getTables(String sourceId) throws SQLException, ClassNotFoundException {
        dataSourceWarpper.createConnection(new DataSource());

        Statement statement = dataSourceWarpper.getConnection(sourceId).createStatement();
        ResultSet rs = statement.executeQuery("show table status");

        List<TableVo> tableVos = new ArrayList<>();
        TableVo tableVo = null;
        while (rs.next()) {
            tableVo = new TableVo();
            String tableName = rs.getString("name");
            String remark = rs.getString("comment");
            tableVo.setTableName(tableName);
            tableVo.setRemark(remark);
            tableVos.add(tableVo);
        }
        return tableVos;
    }

    @Override
    public List<String> getDataBase(String sourceId) throws SQLException {
        Statement statement = dataSourceWarpper.getConnection(sourceId).createStatement();
        ResultSet rs = statement.executeQuery("SHOW DATABASES");

        List<String> dataBases = new ArrayList<>();
        TableVo tableVo = null;
        while (rs.next()) {
            tableVo = new TableVo();
            String dataBaseName = rs.getString("DataBase");
            dataBases.add(dataBaseName);
        }
        return dataBases;
    }


}
