package org.lzz.generate.app.controller;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.lzz.generate.app.FreeMarkerTemplateUtils;
import org.lzz.generate.app.datasource.DataSource;
import org.lzz.generate.app.datasource.DataSourceWarpper;
import org.lzz.generate.app.vo.ColumnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zeze.li
 * @version 1.0.0
 * @ClassName TestController.java
 * @Description TODO
 * @createTime 2020年07月11日 15:25:00
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private DataSourceWarpper dataSourceWarpper;

    @GetMapping("/")
    public void aa() {
        Map<String, Connection> a = dataSourceWarpper.getMap();

        Map<String, Connection> map = new HashMap<>();
        map.put("1", null);
        dataSourceWarpper.setMap(map);

    }


    @PostMapping("/")
    public String createConnection(@RequestBody DataSource dataSource) throws SQLException, ClassNotFoundException {
        String sql = "SELECT username,password FROM test";
        //第四步：获取statement类
        Statement statement = dataSourceWarpper.createConnection(dataSource).createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return "";
    }

    @PostMapping("/table")
    public ResponseEntity getTable(@RequestBody Map<String, String> map) throws SQLException, ClassNotFoundException {
        DatabaseMetaData databaseMetaData = dataSourceWarpper.getConnection("").getMetaData();
        ResultSet resultSet1 = databaseMetaData.getColumns(null, "%", map.get("tableName"), "%");
        ResultSet resultSet2 = databaseMetaData.getPrimaryKeys(null, null, "test");

        while (resultSet2.next()) {
            System.out.print(resultSet2.getString("column_name") + " ");

        }

        List<ColumnVo> columnVoList = new ArrayList<>();
        ColumnVo columnVo = null;
        while (resultSet1.next()) {
            //id字段略过
            if (resultSet1.getString("COLUMN_NAME").equals("id")) continue;
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
        return ResponseEntity.status(HttpStatus.OK).body(columnVoList);


    }

    @GetMapping("/a")
    public String aaaa() throws IOException, TemplateException {
        try {
            File mapperFile = new File("E:\\a.java");
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("msg", "22222");
            Template template = FreeMarkerTemplateUtils.getTemplate("test\\demo.ftl");

            FileOutputStream fos = new FileOutputStream(mapperFile);

            Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
            template.process(dataMap, out);
        } catch (Exception ex) {
            String mess = ex
                    .getMessage();
        }


        return "2";
    }


}
