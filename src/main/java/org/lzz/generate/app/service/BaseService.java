package org.lzz.generate.app.service;

import org.lzz.generate.app.vo.ColumnVo;
import org.lzz.generate.app.vo.TableVo;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zeze.li
 * @version 1.0.0
 * @ClassName BaseService.java
 * @Description TODO
 * @createTime 2020年07月11日 14:57:00
 */
public interface BaseService {


    List<ColumnVo> getColumn( String tableName) throws SQLException, ClassNotFoundException;

    List<String> getPrimaryKeys( String tableName) throws SQLException, ClassNotFoundException;


    List<TableVo> getTables() throws SQLException, ClassNotFoundException;

}
