package com.pac.ride.controller;

import com.pac.ride.entity.RideResponse;
import com.pac.ride.service.PassengerService;
import com.prac.ride.entity.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Autowired
    PassengerService passengerService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Passenger passenger){
        if(passengerService.savePassenger(passenger))
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<RideResponse> bookRide(){
        return null;
    }

}
