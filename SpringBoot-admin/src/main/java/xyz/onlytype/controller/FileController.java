package xyz.onlytype.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.onlytype.config.utils.R;
import xyz.onlytype.config.utils.ResultModel;
import xyz.onlytype.service.FileService;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 白也
 * @title
 * @date 2023/2/4 10:14 下午
 */
@Api(value = "file", tags = {"文件服务"})
@RestController
@RequestMapping("/api/file")
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     * @param file 文件流
     * @return 上传结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "file", name = "file", value = "文件流", required = true),
            @ApiImplicitParam(value = "token",name = "token",paramType  = "header", dataType = "String", required=true)
    })
    @ApiOperation(value = "文件上传", notes = "文件上传", httpMethod = "POST")
    @PostMapping
    public ResultModel uploader(@RequestParam("file") @RequestPart MultipartFile file){
        try {
            return ResultModel.ok("上传成功",fileService.uploader(file));
        } catch (Exception e) {
            return ResultModel.error("上传失败",e.getMessage());
        }
    }

    /**
     * 查询文件列表
     * @return  文件列表
     */
    @ApiImplicitParams({
            @ApiImplicitParam(value = "token",name = "token",paramType  = "header", dataType = "String", required=true)
    })
    @ApiOperation(value = "查询文件列表", notes = "查询文件列表", httpMethod = "GET")
    @GetMapping
    public ResultModel selectFileList(){
        try {
            return ResultModel.ok(fileService.selectAll());
        } catch (Exception e) {
            return ResultModel.error("查询失败",e.getMessage());
        }
    }

    /**
     * 删除文件
     * @param fileName 文件名
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "fileName", value = "文件名", required = true),
            @ApiImplicitParam(value = "token",name = "token",paramType  = "header", dataType = "String", required=true)
    })
    @ApiOperation(value = "删除文件", notes = "删除文件", httpMethod = "DELETE")
    @DeleteMapping
    public ResultModel deleteFile(String fileName){
        try {
            return ResultModel.ok(fileService.delete(fileName));
        } catch (Exception e) {
            return ResultModel.error("删除失败",e.getMessage());
        }
    }

    /**
     * 下载文件
     * @param response response流
     * @param fileName 文件名
     * @return 返回结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "response流", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "fileName", value = "文件名", required = true),
            @ApiImplicitParam(value = "token",name = "token",paramType  = "header", dataType = "String", required=true)

    })
    @ApiOperation(value = "下载文件", notes = "下载文件", httpMethod = "GET")
    @GetMapping("download")
    public ResultModel download(HttpServletResponse response, String fileName){
        try {
            return ResultModel.ok(fileService.download(response,fileName));
        } catch (Exception e) {
            return ResultModel.error("下载失败",e.getMessage());
        }
    }

}
