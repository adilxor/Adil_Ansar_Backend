package com.prezi.backend.response;

import java.io.Serializable;

public class SimpleResponseDTO implements Serializable {
    private String message;
    private Integer status;

    public SimpleResponseDTO(String message, Integer status){
        this.message = message;
        this.status = status;
    }

    public SimpleResponseDTO(String message){
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
