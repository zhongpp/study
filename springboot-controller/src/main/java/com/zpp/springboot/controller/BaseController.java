package com.zpp.springboot.controller;

import com.zpp.springboot.json.Body;
import com.zpp.springboot.util.BodyValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhongpp
 * on 2018/6/7.
 */
@Component
public class BaseController {

    @Autowired
    private BodyValidation bodyValidation;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(bodyValidation);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Body handleException(MethodArgumentNotValidException e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Body body = new Body();
        if (ObjectUtils.isEmpty(e.getBindingResult().getAllErrors())) {
            body.putError("VALIDATION_ERROR", "validation error", "表单数据不合法");
        } else {
            ObjectError error = e.getBindingResult().getAllErrors().get(0);
            body.putError("VALIDATION_ERROR", error.getCode(), error.getDefaultMessage());
        }
        return body;
    }

    @ExceptionHandler(value = IOException.class)
    public Body handleIOException(IOException e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Body body = new Body();
        body.putError("VALIDATION_ERROR", e.getMessage(), "表单数据不合法");
        return body;
    }
}
