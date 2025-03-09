package com.pac.ride.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse extends ApplicationResponse {

    public ErrorResponse(String message, String code) {
        super(message,code);
    }

    public ErrorResponse(String message){
        super(message,"ERROR IN OPERATION ");
    }

    private String cause;

}
