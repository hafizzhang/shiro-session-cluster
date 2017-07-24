package com.hafiz.www.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Desc:全局异常处理器
 * Created by hafiz.zhang on 2016/8/27.
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        //handler就是处理器适配器要执行的处理器（只有method方法）

        //1.解析出异常类型
        CustomException exception = null;
        //如果该异常类型是系统自定义的异常，直接取出异常信息，在错误页面展示
        if(ex instanceof CustomException){
            exception = (CustomException)ex;
        }
        else{
            //如果该异常类型不是系统自定义的异常，构造一个自定义的异常类型（信息为“未知错误”）
            exception = new CustomException("未知错误,请于管理员联系");
        }

        ModelAndView modelAndView = new ModelAndView();

        //将错误信息传到页面
        modelAndView.addObject("message", exception.getMessage());

        //指定错误页面
        modelAndView.setViewName("error");

        return modelAndView;
    }
}
