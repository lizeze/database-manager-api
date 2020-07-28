package org.lzz.generate.app.service.impl;

import org.lzz.generate.app.vo.ColumnVo;
import org.lzz.generate.app.vo.SqlVo;
import org.lzz.generate.app.vo.TableVo;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zeze.li
 * @version 1.0.0
 * @ClassName Postgresql.java
 * @Description TODO
 * @createTime 2020年07月21日 22:16:00
 */
@Service("postgre")
public class PostgresqlServiceImpl extends BaseServiceImpl {

    @Override
    public List<TableVo> getTables(String sourceId, String dataBaseName) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = executeQuery(sourceId, "select * from pg_tables where schemaname='public'");
        List<TableVo> tableVos = new ArrayList<>();
        TableVo tableVo = null;
        while (resultSet.next()) {
            tableVo = new TableVo();
            tableVo.setTableName(resultSet.getString("tablename"));
            tableVos.add(tableVo);
        }


        return tableVos;
    }

    @Override
    public String getListSql(SqlVo sqlVo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select *  from " + sqlVo.getTableName());
        if (sqlVo.getSqlText() != null && sqlVo.getSqlText() != "")
            stringBuilder.append(" where " + sqlVo.getSqlText());
        Integer start = sqlVo.getPageSize() * (sqlVo.getPageIndex() - 1);
        stringBuilder.append(" limit " + sqlVo.getPageSize() + " OFFSET " + start);
        return stringBuilder.toString();

    }

    @Override
    public Map<String, Object> getTableList(SqlVo sqlVo) throws SQLException, ClassNotFoundException {
        String listSql = this.getListSql(sqlVo);
        String countSql = this.getCountSql(sqlVo);
        sqlVo.setListSql(listSql);
        sqlVo.setCountSql(countSql);
        List<ColumnVo> columnVos = this.getColumn(sqlVo.getSourceId(), sqlVo.getDataBaseName(), sqlVo.getTableName());
        return super.getTableList(sqlVo, columnVos);
    }

    @Override
    public String getCountSql(SqlVo sqlVo) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select  count(1) as count  from " + sqlVo.getTableName());
        if (sqlVo.getSqlText() != null && sqlVo.getSqlText() != "")
            stringBuilder.append(" where " + sqlVo.getSqlText());

        return stringBuilder.toString();

    }



    @Override
    public List<ColumnVo> getColumn(String sourceId, String dataBaseName, String tableName) throws SQLException, ClassNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("SELECT  a.attname AS COLUMN_NAME, t.typname AS TYPE_NAME ");
        stringBuilder.append(" , a.attnotnull AS notnull, b.description AS REMARKS");
        stringBuilder.append(" FROM pg_class c, pg_attribute a LEFT JOIN pg_description b ON a.attrelid = b.objoid AND a.attnum = b.objsubid, pg_type t");
        stringBuilder.append(" WHERE c.relname = '" + tableName + "'");
        stringBuilder.append("    AND a.attnum > 0");
        stringBuilder.append("    AND a.attrelid = c.oid");
        stringBuilder.append("    AND a.atttypid = t.oid");
        stringBuilder.append(" ORDER BY a.attnum");

        ResultSet resultSet =executeQuery(sourceId,stringBuilder.toString());

        return super.getColumn(resultSet);
    }


}
