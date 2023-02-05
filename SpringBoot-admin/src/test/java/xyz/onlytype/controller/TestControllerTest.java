package xyz.onlytype.controller;

import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.onlytype.entity.File;
import xyz.onlytype.service.FileService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 白也
 * @title
 * @date 2023/2/4 10:01 下午
 */
@SpringBootTest
class TestControllerTest {
    @Autowired
    private FileService fileService;
    @Autowired
    private MinioClient minioClient;

    @Test
    void minioTest() {
        List<File> files = fileService.selectAll();
        System.out.println(files);
    }
    //递归遍历目录的所有文件
    @Test
    void test3() throws Exception{
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket("my-bucketname").recursive(true).build());
        for (Result<Item> result : results) {
            Item item = result.get();
            System.out.println(item.objectName());
        }
    }
}