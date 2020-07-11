package org.lzz.generate.app.vo;

import java.util.List;

/**
 * @author zeze.li
 * @version 1.0.0
 * @ClassName TableVo.java
 * @Description TODO
 * @createTime 2020年07月11日 16:24:00
 */
public class TableVo {


    private String tableName;
    private List<ColumnVo> columnVos;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnVo> getColumnVos() {
        return columnVos;
    }

    public void setColumnVos(List<ColumnVo> columnVos) {
        this.columnVos = columnVos;
    }
}
