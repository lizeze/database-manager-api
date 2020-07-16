package org.lzz.generate.app.service.impl;

import com.sun.rowset.JdbcRowSetImpl;
import org.lzz.generate.app.datasource.DataSource;
import org.lzz.generate.app.datasource.DataSourceWarpper;
import org.lzz.generate.app.service.BaseService;
import org.lzz.generate.app.vo.ColumnVo;
import org.lzz.generate.app.vo.SqlVo;
import org.lzz.generate.app.vo.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.rowset.JdbcRowSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zeze.li
 * @version 1.0.0
 * @ClassName MysqlServiceImpl.java
 * @Description TODO
 * @createTime 2020年07月11日 23:10:00
 */
@Service("mysql")
public class MysqlServiceImpl extends BaseServiceImpl {

    private final String DRIVER = "com.mysql.jdbc.Driver";


    @Autowired
    private DataSourceWarpper dataSourceWarpper;


    @Override
    public List<String> getPrimaryKeys(String sourceId, String dataBaseName, String tableName) throws SQLException, ClassNotFoundException {

        List<String> list = new ArrayList<>();
        ResultSet primaryKeys = dataSourceWarpper.getDatabaseMetaData(sourceId).getPrimaryKeys(dataSourceWarpper.getDataSource().getDataBaseName(), dataSourceWarpper.getDataSource().getDataBaseName(), tableName);
        while (primaryKeys.next()) {
            list.add(primaryKeys.getString("column_name"));
        }
        return list;
    }

    @Override
    public List<TableVo> getTables(String sourceId, String dataBaseName) throws SQLException, ClassNotFoundException {


        //        Statement statement = dataSourceWarpper.getConnection(sourceId).createStatement();

        JdbcRowSet jrs = new JdbcRowSetImpl(dataSourceWarpper.getConnection(sourceId));

        jrs.setCommand("use " + dataBaseName); //执行选择数据库操作
        jrs.execute();
        jrs.setCommand("show table status");
        jrs.execute();

        //        ResultSet rs = statement.executeQuery(stringBuilder.toString());

        List<TableVo> tableVos = new ArrayList<>();
        TableVo tableVo = null;
        while (jrs.next()) {
            tableVo = new TableVo();
            String tableName = jrs.getString("name");
            String remark = jrs.getString("comment");
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
            String name = rs.getString("DataBase");
            dataBases.add(name);
        }
        return dataBases;
    }

    @Override
    public List<Map<String, Object>> getTableList(String sourceId, String tableName) throws SQLException, ClassNotFoundException {
        JdbcRowSet jrs = new JdbcRowSetImpl(dataSourceWarpper.getConnection(sourceId));

        Statement statement = dataSourceWarpper.getConnection(sourceId).createStatement();
        ResultSet rs = statement.executeQuery("select *  from " + tableName);
        List<Map<String, Object>> result = new ArrayList<>();
        List<ColumnVo> columnVos = super.getColumn(sourceId, "", tableName);
        int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {

            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < columnVos.size(); i++) {

                map.put(columnVos.get(i).getColumnName(), rs.getString(columnVos.get(i).getColumnName()));
            }
            result.add(map);


        }
        return result;

    }
}
