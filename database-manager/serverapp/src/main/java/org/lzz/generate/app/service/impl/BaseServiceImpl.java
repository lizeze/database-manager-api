package org.lzz.generate.app.service.impl;

import com.sun.rowset.JdbcRowSetImpl;
import freemarker.template.utility.StringUtil;
import org.lzz.generate.app.datasource.DataSource;
import org.lzz.generate.app.datasource.DataSourceWarpper;
import org.lzz.generate.app.service.BaseService;
import org.lzz.generate.app.vo.ColumnVo;
import org.lzz.generate.app.vo.SqlVo;
import org.lzz.generate.app.vo.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.sql.rowset.JdbcRowSet;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
    public List<ColumnVo> getColumn(String sourceId, String dataBaseName, String tableName) throws SQLException, ClassNotFoundException {
        return null;
    }


    public List<ColumnVo> getColumn(ResultSet resultSet) throws SQLException, ClassNotFoundException {

        List<ColumnVo> columnVoList = new ArrayList<>();
        ColumnVo columnVo = null;
        while (resultSet.next()) {
//            if (resultSet.getString("COLUMN_NAME").equals("id")) continue;
            columnVo = new ColumnVo();
            columnVo.setColumnName(resultSet.getString("COLUMN_NAME"));
            columnVo.setColumnType(resultSet.getString("TYPE_NAME"));
            columnVo.setColumnComment(resultSet.getString("REMARKS"));
            columnVoList.add(columnVo);
        }
        return columnVoList;
    }

    @Override
    public List<String> getPrimaryKeys(String sourceId, String dataBaseName, String tableName) throws SQLException, ClassNotFoundException {

        List<String> list = new ArrayList<>();
        ResultSet primaryKeys = dataSourceWarpper.getDatabaseMetaData(sourceId).getPrimaryKeys(null, dataSourceWarpper.getDataSource().getDataBaseName(), tableName);
        while (primaryKeys.next()) {
            list.add(primaryKeys.getString("column_name"));
        }
        return list;
    }

    @Override
    public List<TableVo> getTables(String sourceId, String dataBaseName) throws SQLException, ClassNotFoundException {

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
    public Map<String, Object> getTableList(SqlVo sqlVo) throws SQLException, ClassNotFoundException {
        return null;
    }

    public Map<String, Object> getTableList(SqlVo sqlVo, List<ColumnVo> columnVos) throws SQLException, ClassNotFoundException {
        JdbcRowSet jrs = new JdbcRowSetImpl(dataSourceWarpper.getConnection(sqlVo.getSourceId()));
        Map<String, Object> responseData = new HashMap<>();

        ResultSet resultSet = this.executeQuery(sqlVo.getSourceId(), sqlVo.getCountSql());
        Integer totalCount = 0;
        while (resultSet.next()) {

            totalCount = Integer.parseInt(resultSet.getString("count"));
        }
        responseData.put("totalCount", totalCount);

        ResultSet rs = executeQuery(sqlVo.getSourceId(), sqlVo.getListSql());
        List<Map<String, Object>> result = new ArrayList<>();
        int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {

            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < columnVos.size(); i++) {

                map.put(columnVos.get(i).getColumnName(), rs.getString(columnVos.get(i).getColumnName()));
            }
            result.add(map);


        }
        responseData.put("data", result);
        return responseData;
    }

    @Override
    public String getCountSql(SqlVo sqlVo) {
        return null;
    }

    @Override
    public String getListSql(SqlVo sqlVo) {
        return null;
    }

    public Integer getCountSql(String sourceId, String tableName, String sqlText) throws SQLException {

        String sql = "select count(1) as count from " + tableName;
        if (sqlText != null && !StringUtils.isEmpty(sqlText))
            sql += " where " + sqlText;
        Integer count = 0;
        ResultSet resultSet = this.executeQuery(sourceId, sql);
        while (resultSet.next()) {

            count = Integer.parseInt(resultSet.getString("count"));
        }
        return count;


    }

    public ResultSet executeQuery(String sourceId, String sql) throws SQLException {

        Statement statement = dataSourceWarpper.getConnection(sourceId).createStatement();
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }
}
