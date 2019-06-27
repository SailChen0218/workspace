package com.ezshop.desensitize.dto;

import java.io.Serializable;

public class ErrorDto implements Serializable {
    private static final long serialVersionUID = 6881299035371234857L;
    private String name;
    private String message;

    public ErrorDto(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
