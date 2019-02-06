package com.prezi.backend.fixtures;

import com.prezi.backend.response.SimpleResponseDTO;

public class SimpleResponseDTOBuilder {
    SimpleResponseDTO simpleResponseDTO;
    private String message;
    private Integer status;
    public SimpleResponseDTOBuilder(String message, Integer status){
        simpleResponseDTO = new SimpleResponseDTO(message, status);
    }

    public SimpleResponseDTO build() {return this.simpleResponseDTO;}
}
