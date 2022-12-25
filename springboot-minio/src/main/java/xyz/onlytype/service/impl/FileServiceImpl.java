package xyz.onlytype.service.impl;


import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.onlytype.Application;
import xyz.onlytype.config.utils.R;
import xyz.onlytype.entity.File;
import xyz.onlytype.service.FileService;
import xyz.onlytype.utils.getURL;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author 白也
 * @date 2022/12/21 2:35 下午
 * @title
 */
@Service
public class FileServiceImpl implements FileService {
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private getURL getURL;
    @Value("${minio.bucket}")
    private String bucket;

    /**
     * 上传
     */
    @Override
    public List<File> uploader(MultipartFile file){
        String url = null;
        try {
            //创建桶
            if (! minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            log.warn(bucket+"创建成功");
            //上传文件
            minioClient.putObject(PutObjectArgs.builder()
                    .object(file.getOriginalFilename())
                    .bucket(bucket)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            file.getInputStream().close();
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            log.error(e.getMessage());
        }
        return Collections.singletonList(new File(file.getOriginalFilename(), getURL.get(file.getOriginalFilename(), bucket)));
    }

    /**
     * 文件列表
     */

    @Override
    public List<File> selectAll() {
        List<File> list = new ArrayList<>();
        Iterable<Result<Item>> myObjects = minioClient.listObjects(ListObjectsArgs.builder().useUrlEncodingType(true).bucket(bucket).build());
        for (Result<Item> result : myObjects) {
            try {
                Item item = result.get();
                list.add(new File(item.objectName(), getURL.get(item.objectName(), bucket)));
            } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
                log.error(e.getMessage());
            }
        }
        return list;
    }

    /**
     * 删除文件
     * @param fileName 获取文件名
     */

    @Override
    public R delete(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(bucket).object(fileName).build());
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error().code().message("删除失败");
        }
        return R.success().message("删除成功");
    }
}
