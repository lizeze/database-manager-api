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
    public Map<String, Object> getTableList(SqlVo sqlVo, List<ColumnVo> columnVos) throws SQLException, ClassNotFoundException {

        String tableName = "`" + sqlVo.getTableName() + "`";
        sqlVo.setTableName(tableName);
        return super.getTableList(sqlVo, columnVos);
    }

    @Override
    public List<TableVo> getTables(String sourceId, String dataBaseName) throws SQLException, ClassNotFoundException {


        //        Statement statement = dataSourceWarpper.getConnection(sourceId).createStatement();

        JdbcRowSet jrs = new JdbcRowSetImpl(dataSourceWarpper.getConnection(sourceId));

        jrs.setCommand("use `" + dataBaseName + "`"); //执行选择数据库操作
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
    public List<ColumnVo> getColumn(ResultSet resultSet) throws SQLException, ClassNotFoundException {
//        ResultSet columns = dataSourceWarpper.getDatabaseMetaData(sourceId).getColumns(dataSourceWarpper.getDataBaseName(sourceId), dataSourceWarpper.getDataBaseName(sourceId), tableName, "%");
        List<ColumnVo> columnVoList = new ArrayList<>();
        ColumnVo columnVo = null;
        while (resultSet.next()) {
            if (resultSet.getString("COLUMN_NAME").equals("id")) continue;
            columnVo = new ColumnVo();
            columnVo.setColumnName(resultSet.getString("COLUMN_NAME"));
            columnVo.setColumnType(resultSet.getString("TYPE_NAME"));
            columnVo.setColumnComment(resultSet.getString("REMARKS"));
            columnVoList.add(columnVo);
        }
        return columnVoList;
    }
}
