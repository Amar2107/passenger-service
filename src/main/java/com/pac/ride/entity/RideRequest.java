package com.pac.ride.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RideRequest {

    private String pickupLocation;
    private String dropoffLocation;

}
