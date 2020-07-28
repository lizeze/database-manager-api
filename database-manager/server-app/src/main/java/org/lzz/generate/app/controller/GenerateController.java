package org.lzz.generate.app.controller;

import org.lzz.generate.app.service.BaseService;
import org.lzz.generate.app.vo.ColumnVo;
import org.lzz.generate.app.vo.SqlVo;
import org.lzz.generate.app.vo.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zeze.li
 * @version 1.0.0
 * @ClassName GenerateController.java
 * @Description TODO
 * @createTime 2020年07月11日 19:48:00
 */
@RestController
@RequestMapping("/api/mysql")
public class GenerateController {

    @Autowired
    @Qualifier("mysql")
    BaseService baseService;

    @GetMapping("/column")
    public ResponseEntity aaa() throws SQLException, ClassNotFoundException {
        List<ColumnVo> list = baseService.getColumn("", "", "test");
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    @GetMapping("/table/{sourceId}")
    public ResponseEntity getTables(@PathVariable String sourceId) throws SQLException, ClassNotFoundException {

        List<TableVo> tableVos = baseService.getTables(sourceId, "");

        return ResponseEntity.status(HttpStatus.OK).body(tableVos);

    }


    @GetMapping("/pk")
    public ResponseEntity getPrimaryKeys() throws SQLException, ClassNotFoundException {

        List<String> tablekeys = baseService.getPrimaryKeys("", "", "test");

        return ResponseEntity.status(HttpStatus.OK).body(tablekeys);

    }


    @GetMapping("/database/{sourceId}")
    public ResponseEntity getDataBase(@PathVariable String sourceId) throws SQLException, ClassNotFoundException {

        List<String> dataBase = baseService.getDataBase(sourceId);

        return ResponseEntity.status(HttpStatus.OK).body(dataBase);

    }


    @PostMapping("/exec")
    public ResponseEntity execSql(@RequestBody SqlVo sqlVo) throws SQLException, ClassNotFoundException {

        baseService.executeQuery(sqlVo);

        return ResponseEntity.status(HttpStatus.OK).body(1);

    }


}
