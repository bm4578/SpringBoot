package xyz.onlytype.config.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 白也
 * @title 返回于前端的信息
 * @date 2023/1/27 6:10 下午
 */

@Data
public class ResultModel<T> implements Serializable {
    /**
     * 状态码
     * 200:成功
     * 500：失败
     */
    private int code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回主体
     */
    private T result;

    private static ResultModel resultModel = new ResultModel();

    public static ResultModel ok(){
        resultModel.setCode(200);
        resultModel.setMsg("ok");
        resultModel.setResult(null);
        return resultModel;
    }
    public static ResultModel ok(String msg){
        resultModel.setCode(200);
        resultModel.setMsg(msg);
        resultModel.setResult(null);
        return resultModel;
    }
    public static ResultModel ok(Object data){
        resultModel.setCode(200);
        resultModel.setMsg("ok");
        resultModel.setResult(data);
        return resultModel;
    }
    public static ResultModel ok(String msg,Object data){
        resultModel.setCode(200);
        resultModel.setMsg(msg);
        resultModel.setResult(data);
        return resultModel;
    }

    public static ResultModel error(){
        resultModel.setCode(500);
        resultModel.setMsg("error");
        resultModel.setResult(null);
        return resultModel;
    }
    public static ResultModel error(String msg){
        resultModel.setCode(500);
        resultModel.setMsg(msg);
        resultModel.setResult(null);
        return resultModel;
    }
    public static ResultModel error(Object data){
        resultModel.setCode(500);
        resultModel.setMsg("error");
        resultModel.setResult(data);
        return resultModel;
    }
    public static ResultModel error(String msg,Object data){
        resultModel.setCode(500);
        resultModel.setMsg(msg);
        resultModel.setResult(data);
        return resultModel;
    }
    public static ResultModel error(int code,Object data){
        resultModel.setCode(code);
        resultModel.setResult(data);
        return resultModel;
    }
}
