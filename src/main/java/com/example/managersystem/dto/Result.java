package com.example.managersystem.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;


    public Result() {
    }

    public Result(Integer code, T data, String message) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result success(T data, String message) {
        this.code = HttpStatus.OK.value();
        this.message = message;
        this.data = data;
        return this;
    }

    public Result error(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
        return this;
    }
}
