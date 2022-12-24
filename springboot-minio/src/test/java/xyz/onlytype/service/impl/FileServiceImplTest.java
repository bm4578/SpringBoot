package xyz.onlytype.service.impl;

import io.minio.*;
import io.minio.messages.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * @author 白也
 * @date 2022/12/22 11:07 上午
 * @title
 */
@SpringBootTest
class FileServiceImplTest {
    @Autowired
    MinioClient minioClient;
    @Value("${minio.bucket}")
    private String bucket;

    @Test
    public void selectAll() throws Exception{
        Iterable<Result<Item>> myObjects = minioClient.listObjects(ListObjectsArgs.builder()
                .useUrlEncodingType(true)
                .bucket(bucket).build());
//        HashMap<Object, Object> list = new HashMap<>();
//        int i = 0;
        for (Result<Item> result : myObjects) {
            Item item = result.get();
            System.out.println(item.lastModified() + ", " + item.size() + ", " + item.objectName());
        }
    }
}