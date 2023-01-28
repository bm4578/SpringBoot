package xyz.onlytype.service.impl;


import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.onlytype.Application;
import xyz.onlytype.security.config.utils.R;
import xyz.onlytype.entity.File;
import xyz.onlytype.service.FileService;
import xyz.onlytype.utils.getURL;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
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
     * 下载文件
     * @param file 文件
     */
    @Override
    public List<File> uploader(MultipartFile file){
        String url = null;
        try {
            //创建桶
            if (! minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
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
     * 查询文件
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
     * @param fileName 文件名
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

    /**
     * 下载文件
     *
     * @param fileName 文件名
     */
    @Override
    public R download( HttpServletResponse response, String fileName) {
        // get object given the bucket and object name
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .build())) {
            // Read data from stream
            //浏览器指定下载类型
            response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
            //下载文件
            IOUtils.copy(stream, response.getOutputStream());
        }catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e){
            log.error(e.getMessage());
            return R.error().message("下载失败");
        }
        return R.success().message("下载成功");
    }

}
