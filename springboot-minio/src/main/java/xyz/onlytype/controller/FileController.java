package xyz.onlytype.controller;

import cn.hutool.core.io.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.onlytype.config.utils.R;
import xyz.onlytype.entity.File;
import xyz.onlytype.service.FileService;

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
    @ApiOperation("查询列表")
    @GetMapping
    public R selectAll(){
        return R.success().code().map("data",fileService.selectAll());
    }
    @DeleteMapping
    @ApiOperation("删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "fileName", value = "文件名",required = true)
    })
    public R deleteFile(String fileName){
        return R.success().map("data",fileService.delete(fileName));
    }
}
