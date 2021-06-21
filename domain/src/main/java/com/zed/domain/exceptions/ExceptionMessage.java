package com.zed.domain.exceptions;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zed
 */
@Data
public class ExceptionMessage implements Serializable {

	private String title;
	
	private String message;
	
    private ExceptionMessage(String title, String message) {
        super();
        this.title = title;
        this.message = message;
    }
    
	public static ExceptionMessage getInstance(String title, String message) {
	    return new ExceptionMessage(title, message);
	}
	
    public static ExceptionMessage getInstance(String message) {
        return new ExceptionMessage("error", message);
    }


}
