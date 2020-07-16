package org.lzz.generate.app.service;

import org.lzz.generate.app.vo.ColumnVo;
import org.lzz.generate.app.vo.SqlVo;
import org.lzz.generate.app.vo.TableVo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author zeze.li
 * @version 1.0.0
 * @ClassName BaseService.java
 * @Description TODO
 * @createTime 2020年07月11日 14:57:00
 */
public interface BaseService {


    List<ColumnVo> getColumn(String sourceId, String dataBaseName, String tableName) throws SQLException, ClassNotFoundException;

    List<String> getPrimaryKeys(String sourceId, String dataBaseName, String tableName) throws SQLException, ClassNotFoundException;


    List<TableVo> getTables(String sourceId, String dataBaseName) throws SQLException, ClassNotFoundException;

    List<String> getDataBase(String sourceId) throws SQLException;

    int executeQuery(SqlVo sqlVo) throws SQLException;

    void commit(String sourceId) throws SQLException;

    void rollback(String sourceId) throws SQLException;

    List<Map<String, Object>> getTableList(String sourceId, String tableName) throws SQLException, ClassNotFoundException;
}
