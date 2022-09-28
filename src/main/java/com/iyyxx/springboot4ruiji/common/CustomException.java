package com.iyyxx.springboot4ruiji.common;

/**
 * @className: CustomException
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/9/28/0028 17:31:25
 **/
public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
