package com.unifier.dto;

import java.io.Serializable;

public class ExceptionDTO implements Serializable {

    private String message;

    public ExceptionDTO(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
