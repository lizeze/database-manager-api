package org.lzz.generate.app.controller;

import org.lzz.generate.app.service.BaseService;
import org.lzz.generate.app.vo.ColumnVo;
import org.lzz.generate.app.vo.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/g")
public class GenerateController {

    @Autowired
    BaseService baseService;

    @GetMapping("/column")
    public ResponseEntity aaa() throws SQLException, ClassNotFoundException {
        List<ColumnVo> list = baseService.getColumn("test");
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    @GetMapping("/table")
    public ResponseEntity getTables() throws SQLException, ClassNotFoundException {

        List<TableVo> tableVos = baseService.getTables();

        return ResponseEntity.status(HttpStatus.OK).body(tableVos);

    }

}
