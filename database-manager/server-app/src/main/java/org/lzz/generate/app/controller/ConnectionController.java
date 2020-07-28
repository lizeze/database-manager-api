package org.lzz.generate.app.controller;

import org.lzz.generate.app.datasource.DataSource;
import org.lzz.generate.app.datasource.DataSourceWarpper;
import org.lzz.generate.app.datasource.DriverWarpper;
import org.lzz.generate.app.service.BaseService;
import org.lzz.generate.app.vo.SqlVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.DataBuffer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * @author zeze.li
 * @version 1.0.0
 * @ClassName ConnectionController.java
 * @Description TODO
 * @createTime 2020年07月12日 12:18:00
 */
@RestController
@RequestMapping("/api/connection")
public class ConnectionController {


    @Autowired
    DataSourceWarpper dataSourceWarpper;

    @Qualifier("mysql")
    @Autowired
    private BaseService baseService;

    @PostMapping("/")
    public ResponseEntity createConnection(@RequestBody DataSource dataSource) throws SQLException, ClassNotFoundException {
        String sourceId = dataSourceWarpper.createConnection(new DriverWarpper(dataSource).getDataSource());
        dataSource.setSourceId(sourceId);
        return ResponseEntity.status(HttpStatus.OK).body(dataSource);
    }

    @PostMapping("/{sourceId}")
    public ResponseEntity getDataVase(@PathVariable String sourceId) throws SQLException, ClassNotFoundException {
        List<String> dataBase = baseService.getDataBase(sourceId);
        return ResponseEntity.status(HttpStatus.OK).body(dataBase);
    }

    @DeleteMapping("/{sourceId}")
    public ResponseEntity deleteDataVase(@PathVariable String sourceId) throws SQLException, ClassNotFoundException {
        if (dataSourceWarpper.getMap().containsKey(sourceId)) {
            dataSourceWarpper.getMap().get(sourceId).close();
            dataSourceWarpper.getMap().remove(sourceId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
    @PostMapping("/commit/{sourceId}")
    public ResponseEntity commit(@PathVariable String sourceId) throws SQLException {

        baseService.commit(sourceId);

        return ResponseEntity.status(HttpStatus.OK).body(1);

    }

}
