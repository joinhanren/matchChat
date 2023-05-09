package com.join.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author join
 * @Description
 * @date 2023/3/1 20:06
 */
@Data
@AllArgsConstructor
public class Result {

    private boolean success;
    private int code;
    private String message;
    private Object data;

    //成功
    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }
    //失败
    public static Result fail(int code,String msg){
        return new Result(false,code,msg,null);
    }
}
