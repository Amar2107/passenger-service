package com.pac.ride.controller;

import com.pac.ride.entity.RideRequest;
import com.pac.ride.entity.RideResponse;
import com.pac.ride.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PassengerController {

    @Autowired
    PassengerService passengerService;

    @PostMapping("/book-ride")
    public ResponseEntity<RideResponse> bookRide(@AuthenticationPrincipal Long passengerId, @RequestBody RideRequest rideRequest){

        if(passengerId == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RideResponse(null, null));

        return ResponseEntity.status(HttpStatus.OK).body(passengerService.bookRide(passengerId,rideRequest));

    }

}
