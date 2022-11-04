package com.yangxcc.gulimall.product.exception;

import com.yangxcc.common.exception.ExceptionEnumValue;
import com.yangxcc.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Slf4j
//@RestControllerAdvice
@ControllerAdvice(basePackages = "com.yangxcc.gulimall.product.controller")
@ResponseBody
public class ExceptionController {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleEntityValidationException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{}，异常类型为{}", e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        HashMap<String, String> errMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((item) -> {
            errMap.put(item.getField(), item.getDefaultMessage());
        });
        return R.error(ExceptionEnumValue.VALID_EXCEPTION.getCode(), ExceptionEnumValue.VALID_EXCEPTION.getMsg()).put("data", errMap);
    }

    // 该异常类能够处理所有的
    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable e) {
        return R.error(ExceptionEnumValue.GENERAL_EXCEPTION.getCode(), ExceptionEnumValue.VALID_EXCEPTION.getMsg());
    }
}
