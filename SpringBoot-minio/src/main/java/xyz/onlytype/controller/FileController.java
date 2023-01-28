package xyz.onlytype.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.onlytype.security.config.utils.R;
import xyz.onlytype.service.FileService;
import javax.servlet.http.HttpServletResponse;


/**
 * @author 白也
 * @date 2022/12/21 9:44 下午
 * @title
 */
@RestController
@RequestMapping("file")
@Api(tags = "File")
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     * @param file 文件
     */
    @PostMapping
    @ApiOperation(value = "文件上传")
    @Validated
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true,
                    dataType = "MultipartFile",dataTypeClass = MultipartFile.class,paramType = "query")
    })
    public R uploader(@RequestParam("file") @RequestPart MultipartFile file){
        return R.success().code().map("url",fileService.uploader(file));
    }

    /**
     * 查询文件列表
     */
    @ApiOperation("查询文件")
    @GetMapping
    public R selectAll(){
        return R.success().code().map("data",fileService.selectAll());
    }

    /**
     * 删除文件
     * @param fileName 文件名
     */
    @DeleteMapping
    @ApiOperation("删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "fileName", value = "文件名",required = true)
    })
    public R deleteFile(String fileName){
        return R.success().map("data",fileService.delete(fileName));
    }

    /**
     * 下载文件
     * @param response response
     * @param fileName 文件名
     */
    @ApiOperation(value = "文件下载")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "文件名",dataType = "String",name = "fileName",required = true),
    })
    @GetMapping("/download")
    public R download(HttpServletResponse response,String fileName){
        return R.success().map("data",fileService.download(response,fileName));
    }
}
