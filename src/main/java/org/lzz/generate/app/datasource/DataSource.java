package org.lzz.generate.app.datasource;

/**
 * @author zeze.li
 * @version 1.0.0
 * @ClassName DataSource.java
 * @Description TODO
 * @createTime 2020年07月11日 15:17:00
 */
public class DataSource {


    private String sourceId;
    private String className;
    private String url;
    private String userName;
    private String passWord;
    private String dataBaseName;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }
}
