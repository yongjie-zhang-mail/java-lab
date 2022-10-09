package com.yj.lab.spring.config.exception;

import com.yj.lab.spring.model.constant.MessageConstant;
import com.yj.lab.spring.model.vo.response.common.ErrorEntity;
import com.yj.lab.spring.model.vo.response.common.ErrorInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * @author zhangyj21
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ErrorEntity<ErrorInfoVo> validExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest r) {
        log.error("method:{}, requestUrl:{}, msg:{}", r.getMethod(), r.getRequestURL(), e.getMessage(), e);
        String defaultMessage = getDefaultMessage(e.getBindingResult());
        return new ErrorEntity<>(MessageConstant.CODE_ERROR, defaultMessage, getErrorInfo(r, e));
    }

    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     */
    @ExceptionHandler(BindException.class)
    ErrorEntity<ErrorInfoVo> bindExceptionHandler(BindException e, HttpServletRequest r) {
        log.error("method:{}, requestUrl:{}, msg:{}", r.getMethod(), r.getRequestURL(), e.getMessage(), e);
        String defaultMessage = getDefaultMessage(e.getBindingResult());
        return new ErrorEntity<>(MessageConstant.CODE_ERROR, defaultMessage, getErrorInfo(r, e));
    }

    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    ErrorEntity<ErrorInfoVo> constraintViolationExceptionHandler(ConstraintViolationException e, HttpServletRequest r) {
        log.error("method:{}, requestUrl:{}, msg:{}", r.getMethod(), r.getRequestURL(), e.getMessage(), e);
        String defaultMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage).reduce((s1, s2) -> s1 + "," + s2).orElse("");
        return new ErrorEntity<>(MessageConstant.CODE_ERROR, defaultMessage, getErrorInfo(r, e));
    }

    /**
     * 业务异常 BusinessException 处理器
     *
     * @param r HttpServletRequest
     * @param e BusinessException
     * @return ErrorEntity
     */
    @ExceptionHandler(BusinessException.class)
    ErrorEntity<ErrorInfoVo> businessExceptionHandler(HttpServletRequest r, BusinessException e) {
        log.error("method:{}, requestUrl:{}, msg:{}", r.getMethod(), r.getRequestURL(), e.getMessage(), e);
        return new ErrorEntity<>(e.getCode(), e.getMsg(), getErrorInfo(r, e));
    }

    /**
     * 其他通用异常 Exception 处理
     */
    @ExceptionHandler(Exception.class)
    ErrorEntity<ErrorInfoVo> handleException(Exception e, HttpServletRequest r) {
        log.error("method:{}, requestUrl:{}, msg:{}", r.getMethod(), r.getRequestURL(), e.getMessage(), e);
        return new ErrorEntity<>(MessageConstant.CODE_ERROR, "系统繁忙，请稍后再试：".concat(e.getMessage()), getErrorInfo(r, e));
    }

    /**
     * valid异常统一获取message
     */
    private String getDefaultMessage(BindingResult bindingResult) {
        String defaultMessage;
        if (bindingResult.getFieldError() == null) {
            defaultMessage = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .reduce((s1, s2) -> s1 + "," + s2).orElse("");
        } else {
            defaultMessage = bindingResult.getFieldError().getDefaultMessage();
        }
        return defaultMessage;
    }

    /**
     * 组装异常请求信息
     */
    private ErrorInfoVo getErrorInfo(HttpServletRequest request, Exception e) {
        ErrorInfoVo errorInfoVo = new ErrorInfoVo();
        errorInfoVo.setPath(request.getRequestURI());
        errorInfoVo.setMethod(request.getMethod());
        if (e.getCause() != null) {
            errorInfoVo.setCause(e.getCause().toString());
        }
        errorInfoVo.setException(e.getClass().toString());
        errorInfoVo.setMessage(e.getMessage());
        errorInfoVo.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return errorInfoVo;
    }
}

















