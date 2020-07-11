package org.lzz.generate.app.service.impl;

import org.lzz.generate.app.datasource.DataSource;
import org.lzz.generate.app.datasource.DataSourceWarpper;
import org.lzz.generate.app.service.BaseService;
import org.lzz.generate.app.vo.ColumnVo;
import org.lzz.generate.app.vo.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zeze.li
 * @version 1.0.0
 * @ClassName BaseServiceImpl.java
 * @Description TODO
 * @createTime 2020年07月11日 17:04:00
 */
@Service
public class BaseServiceImpl implements BaseService {


    @Autowired
    private DataSourceWarpper dataSourceWarpper;

    @Override
    public List<ColumnVo> getColumn(String sourceId, String tableName) throws SQLException, ClassNotFoundException {
        ResultSet columns = dataSourceWarpper.getDatabaseMetaData().getColumns(null, dataSourceWarpper.getDataSource().getDataBaseName(), tableName, "%");
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
    public List<String> getPrimaryKeys(String sourceId, String tableName) throws SQLException, ClassNotFoundException {

        List<String> list = new ArrayList<>();
        ResultSet primaryKeys = dataSourceWarpper.getDatabaseMetaData().getPrimaryKeys(null, dataSourceWarpper.getDataSource().getDataBaseName(), tableName);
        while (primaryKeys.next()) {
            list.add(primaryKeys.getString("column_name"));
        }
        return list;
    }

    @Override
    public List<TableVo> getTables(String sourceId) throws SQLException, ClassNotFoundException {
        dataSourceWarpper.createConnection(new DataSource());
        Statement statement = dataSourceWarpper.getConnection("").createStatement();
        ResultSet rs = statement.executeQuery("SHOW TABLES");
        while (rs.next()) {
            String tableName = rs.getString(1);
            System.out.println(rs.getString(1));
        }
        String[] types = new String[1];
        types[0] = "TABLE";
        ResultSet tables = dataSourceWarpper.getDatabaseMetaData().getTables(null, dataSourceWarpper.getDataSource().getDataBaseName(), null, types);
        List<TableVo> tableVos = new ArrayList<>();
        TableVo tableVo = null;
        while (tables.next()) {
            tableVo = new TableVo();
            tableVo.setTableName(tables.getString("Table_Name"));
            tableVos.add(tableVo);

        }
        return tableVos;
    }
}
