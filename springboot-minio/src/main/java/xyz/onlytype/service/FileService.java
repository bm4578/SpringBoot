package xyz.onlytype.service;
import org.springframework.web.multipart.MultipartFile;
import xyz.onlytype.config.utils.R;
import xyz.onlytype.entity.File;

import java.util.HashMap;
import java.util.List;

/**
 * @author 白也
 * @date 2022/12/21 2:35 下午
 * @title
 */
public interface FileService {
    /**
     * 上传文件
     */
    List<File> uploader(MultipartFile file);

    /**
     * 查询
     */
    List<File> selectAll();

    R delete(String fileName);

}
