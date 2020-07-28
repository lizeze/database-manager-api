package org.lzz.generate.app.vo;

/**
 * @author ：lzz
 * @BelongsProject: org.lzz.generate.app.vo
 * @date ：Created in 2020/7/13 14:03
 * @description ：
 * @modified By：
 */
public class SqlVo {

    private String sourceId;
    private String sqlText;
    private String dataBaseName;
    private Integer pageIndex;
    private String tableName;
    private Integer pageSize;
    private String countSql;
    private String listSql;

    public String getListSql() {
        return listSql;
    }

    public void setListSql(String listSql) {
        this.listSql = listSql;
    }

    public String getCountSql() {
        return countSql;
    }

    public void setCountSql(String countSql) {
        this.countSql = countSql;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }
}
