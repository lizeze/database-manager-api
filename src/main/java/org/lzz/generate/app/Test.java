package org.lzz.generate.app;

import org.lzz.generate.app.vo.ColumnVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zeze.li
 * @version 1.0.0
 * @ClassName Test.java
 * @Description TODO
 * @createTime 2020年07月11日 14:43:00
 */
public class Test {

    public static void aaa() {

        String ClassName = "dm.jdbc.driver.DmDriver";
        String url = "jdbc:dm://localhost:5236";
        String username = "SYSDBA";
        String password = "SYSDBA";

        //第一步：加载驱动
        try {//加载MySql的驱动类 将驱动注册到DriverManager当中
            Class.forName(ClassName);
        } catch (ClassNotFoundException e) {
            System.out.println("加载驱动失败！请检查驱动名称");
            e.printStackTrace();
        }

        Connection con = null;
        Statement statement = null;
        String sql = null;
        ResultSet resultSet = null;
        try {
            //第二步：获取连接
            con = DriverManager.getConnection(url, username, password);
            //第三步：创建sql
            sql = "SELECT username,password FROM test";
            //第四步：获取statement类
            statement = con.createStatement();

            DatabaseMetaData databaseMetaData = con.getMetaData();
            ResultSet resultSet1 = databaseMetaData.getColumns(null,"%", "test","%");
            //第五步：获取到执行后的结果集resultSet



            List<ColumnVo> columnVoList = new ArrayList<>();
            ColumnVo columnVo = null;
            while(resultSet1.next()){
                //id字段略过
                if(resultSet1.getString("COLUMN_NAME").equals("id")) continue;
                columnVo = new ColumnVo();
                //获取字段名称
                columnVo.setColumnName(resultSet1.getString("COLUMN_NAME"));
                //获取字段类型
                columnVo.setColumnType(resultSet1.getString("TYPE_NAME"));
                //转换字段名称，如 sys_name 变成 SysName
//                columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
                //字段在数据库的注释
                columnVo.setColumnComment(resultSet1.getString("REMARKS"));
                columnVoList.add(columnVo);
            }



            resultSet = statement.executeQuery(sql);



            while (resultSet.next()) {
                //通过结果集的操作方法进行数据的获取   这里可以进行实际的业务操作，例如存到一个对应的实体类，返回给前端
                //这里是获取的
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
            }
        } catch (SQLException e) {
            System.out.println("数据库连接失败！请检查数据库连接信息");
            e.printStackTrace();
        } finally {
            //先关闭结果集
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //然后关闭Statement对象
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //最后关闭连接
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
