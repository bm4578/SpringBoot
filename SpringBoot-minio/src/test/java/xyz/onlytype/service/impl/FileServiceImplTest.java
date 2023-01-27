package xyz.onlytype.service.impl;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    public void selectAll() {

    }
}