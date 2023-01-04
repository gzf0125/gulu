package com.gulu.Common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 *
 */
//annotations注解，拦截加了Controller注解的异常

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody//一会需要加一个方法该方法返回JSON数据
@Slf4j//用于输出日志
public class GlobalExceptionHandler {
    /**
     * 异常处理方法，Controller抛出SQLIntegrityConstraintViolationException该异常
     * 会被拦截到
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        return R.error("操作失败");
    }
}
