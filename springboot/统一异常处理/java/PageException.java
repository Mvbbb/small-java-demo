package com.example.demo.exception;

import com.example.demo.constant.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <p>
 * 页面异常
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-10-02 21:18
 */
@Getter
public class PageException extends BaseException {

	public PageException(Status status) {
		super(status);
	}

	public PageException(Integer code, String message) {
		super(code, message);
	}
}
