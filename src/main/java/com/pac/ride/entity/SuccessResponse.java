package com.pac.ride.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SuccessResponse extends ApplicationResponse{

    public SuccessResponse(String message){
        super(message,"SUCCESS");
    }

}
