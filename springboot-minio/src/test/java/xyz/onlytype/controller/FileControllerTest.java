package xyz.onlytype.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.onlytype.service.impl.FileServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 白也
 * @date 2022/12/21 9:45 下午
 * @title
 */
@SpringBootTest
class FileControllerTest {
    @Autowired
    FileServiceImpl fileService;
    @Test
    void uploader(){
    }

}