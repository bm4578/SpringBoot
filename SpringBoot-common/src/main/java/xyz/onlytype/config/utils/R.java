package xyz.onlytype.config.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 白也
 * @date 2022/12/21 6:18 下午
 * @title
 */
@Data
public class R implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码", example = "200")
    private Integer code;
    /**
     * 提示消息
     */
    @ApiModelProperty(value = "提示消息", example = "提示消息内容")
    private String message;
    /**
     * 数据对象
     */
    @ApiModelProperty(value = "数据对象")
    private Map<String,Object> data = new HashMap<>();
    /**
     * 构造方法私有化
     */
    private R(){}

    /**
     * 成功
     */
    public static R success(){
        R r = new R();
        r.setCode(ResultCode.SUCCESS_CODE);
        r.setMessage("成功");
        return r;
    }
    public static R error(){
        R r = new R();
        r.setCode(ResultCode.ERROR_CODE);
        r.setMessage("失败");
        return r;
    }

    /**
     * 使用下列方法，方便使用链式编程。
     */
    public R message(String message){
        this.setMessage(message);
        return this;
    }
    public R code(){
        this.setCode(code);
        return this;
    }
    public R map(String key, Object value){
        this.data.put(key,value);
        return this;
    }
}
