package com.java.backend.exception;

import com.java.common.entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    //当控制方法只执行是抛出runtimeexception，那就会自动调用hadlerExcep()方法
    @ExceptionHandler(RuntimeException.class)
    public Result handlerExcep(RuntimeException ex){
        Result result=new Result(false,ex.getMessage());
        return result;
    }
}
