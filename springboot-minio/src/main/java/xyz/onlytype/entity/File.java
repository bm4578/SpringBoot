package xyz.onlytype.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 白也
 * @date 2022/12/22 4:45 下午
 * @title
 */
@Data
@AllArgsConstructor
@EntityScan
@NoArgsConstructor
public class File implements Serializable {
    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称", example = "file.jpg")
    private String FileName;
    /**
     * 返回直链
     */
    @ApiModelProperty(value = "url", example = "http://localhost/test/test.img")
    private String url;

}
