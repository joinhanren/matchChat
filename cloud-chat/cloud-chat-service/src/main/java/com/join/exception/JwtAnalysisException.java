package com.join.exception;

/**
 * @author join
 * @Description  jwt解析异常
 * @date 2023/3/1 21:04
 */
public class JwtAnalysisException extends RuntimeException{

    public JwtAnalysisException(String msg){
        super(msg);
    }
}
