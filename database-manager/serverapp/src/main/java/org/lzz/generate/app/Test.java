package org.lzz.generate.app;

import com.alibaba.druid.pool.DruidDataSource;
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


    public void GetDbConnect() throws Exception {
        DruidDataSource dataSource = null;
        try {
            dataSource = new DruidDataSource();
            //设置连接参数 (***自己定义传递的参数***)
            dataSource.setUrl("jdbc:mysql://cdb-n7awaf8u.bj.tencentcdb.com:10029");
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUsername("root");
            dataSource.setPassword("1qaz@WSX");
            //配置初始化大小、最小、最大
            dataSource.setInitialSize(1);
            dataSource.setMinIdle(1);
            dataSource.setMaxActive(20);
            //连接泄漏监测
            dataSource.setRemoveAbandoned(true);
            dataSource.setRemoveAbandonedTimeout(30);
            //配置获取连接等待超时的时间
            dataSource.setMaxWait(20000);
            //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            dataSource.setTimeBetweenEvictionRunsMillis(20000);
            //防止过期
            dataSource.setValidationQuery("SELECT 'x'");
            dataSource.setTestWhileIdle(true);
            dataSource.setTestOnBorrow(true);
        } catch (Exception e) {
        }
        // 建立了连接
        Connection con = dataSource.getConnection();
    }
}
