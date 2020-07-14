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
