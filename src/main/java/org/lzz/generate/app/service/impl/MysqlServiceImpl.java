package org.lzz.generate.app.service.impl;

import org.lzz.generate.app.service.BaseService;
import org.lzz.generate.app.vo.ColumnVo;
import org.lzz.generate.app.vo.TableVo;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zeze.li
 * @version 1.0.0
 * @ClassName MysqlServiceImpl.java
 * @Description TODO
 * @createTime 2020年07月11日 23:10:00
 */
@Service("mysql")
public class MysqlServiceImpl implements BaseService {

    private final String DRIVER = "com.mysql.jdbc.Driver";

    @Override
    public List<ColumnVo> getColumn(String sourceId, String tableName) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getPrimaryKeys(String sourceId, String tableName) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<TableVo> getTables(String sourceId) throws SQLException, ClassNotFoundException {
        return null;
    }
}
