package com.pac.ride.controller;

import com.pac.ride.entity.RideResponse;
import com.pac.ride.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PassengerController {

    @Autowired
    PassengerService passengerService;

    public ResponseEntity<RideResponse> bookRide(){
        return null;
    }

}
