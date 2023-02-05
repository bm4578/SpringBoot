package xyz.onlytype.controller;

import io.minio.*;
import io.minio.messages.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.onlytype.entity.File;
import xyz.onlytype.service.impl.FileServiceImpl;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * @author 白也
 * @date 2022/12/21 9:45 下午
 * @title
 */
@SpringBootTest
class FileControllerTest {
    @Autowired
    private MinioClient minioClient;

    @Autowired
    FileServiceImpl fileService;
    @Test
    void uploader() throws Exception{
     //创建桶
        minioClient.makeBucket(
                MakeBucketArgs.builder()
                        .bucket("my-bucketname")
                        .build());
    }

    //创建子文件夹
    @Test
    void test2() throws Exception{
        minioClient.putObject(
                PutObjectArgs.builder().bucket("my-bucketname").object("path/to/test").stream(
                        new ByteArrayInputStream(new byte[] {}), 0, -1)
                        .build());
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
    @Test
    void test4(){
        List<File> files = fileService.selectAll();
        System.out.println(files);
    }

}