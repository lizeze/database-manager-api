package org.lzz.generate.app.service.impl;

import org.lzz.generate.app.datasource.DataSource;
import org.lzz.generate.app.datasource.DataSourceWarpper;
import org.lzz.generate.app.service.BaseService;
import org.lzz.generate.app.vo.ColumnVo;
import org.lzz.generate.app.vo.SqlVo;
import org.lzz.generate.app.vo.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zeze.li
 * @version 1.0.0
 * @ClassName BaseServiceImpl.java
 * @Description TODO
 * @createTime 2020年07月11日 17:04:00
 */
@SuppressWarnings("unchecked")
public class BaseServiceImpl implements BaseService {


    @Autowired
    private DataSourceWarpper dataSourceWarpper;

    @Override
    public List<ColumnVo> getColumn(String sourceId, String dataBaseName,String tableName) throws SQLException, ClassNotFoundException {

        ResultSet columns = dataSourceWarpper.getDatabaseMetaData(sourceId).getColumns(dataSourceWarpper.getDataBaseName(sourceId), dataSourceWarpper.getDataBaseName(sourceId), tableName, "%");
        List<ColumnVo> columnVoList = new ArrayList<>();
        ColumnVo columnVo = null;
        while (columns.next()) {
            if (columns.getString("COLUMN_NAME").equals("id")) continue;
            columnVo = new ColumnVo();
            columnVo.setColumnName(columns.getString("COLUMN_NAME"));
            columnVo.setColumnType(columns.getString("TYPE_NAME"));
            columnVo.setColumnComment(columns.getString("REMARKS"));
            columnVoList.add(columnVo);
        }
        return columnVoList;
    }

    @Override
    public List<String> getPrimaryKeys(String sourceId, String dataBaseName,String tableName) throws SQLException, ClassNotFoundException {

        List<String> list = new ArrayList<>();
        ResultSet primaryKeys = dataSourceWarpper.getDatabaseMetaData(sourceId).getPrimaryKeys(null, dataSourceWarpper.getDataSource().getDataBaseName(), tableName);
        while (primaryKeys.next()) {
            list.add(primaryKeys.getString("column_name"));
        }
        return list;
    }

    @Override
    public List<TableVo> getTables(String sourceId,String dataBaseName) throws SQLException, ClassNotFoundException {

        String[] types = new String[1];
        types[0] = "TABLE";
        ResultSet tables = dataSourceWarpper.getDatabaseMetaData(sourceId).getTables(null, dataSourceWarpper.getDataSource().getDataBaseName(), null, types);
        List<TableVo> tableVos = new ArrayList<>();
        TableVo tableVo = null;
        while (tables.next()) {
            tableVo = new TableVo();
            tableVo.setTableName(tables.getString("Table_Name"));
            tableVos.add(tableVo);

        }
        return tableVos;
    }

    @Override
    public List<String> getDataBase(String sourceId) throws SQLException {
        return null;
    }

    @Override
    public int executeQuery(SqlVo sqlVo) throws SQLException {
        Statement statement = dataSourceWarpper.getConnection(sqlVo.getSourceId()).createStatement();
        statement.execute(sqlVo.getSqlText());
        return 1;
    }

    @Override
    public void commit(String sourceId) throws SQLException {

        dataSourceWarpper.getMap().get(sourceId).commit();

    }

    @Override
    public void rollback(String sourceId) throws SQLException {
        dataSourceWarpper.getMap().get(sourceId).rollback();
    }

    @Override
    public List<Map<String, Object>> getTableList(String sourceId, String tableName) throws SQLException, ClassNotFoundException {
        return null;
    }
}
