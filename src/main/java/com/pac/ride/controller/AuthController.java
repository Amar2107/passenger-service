package com.pac.ride.controller;

import com.pac.ride.entity.ApplicationResponse;
import com.pac.ride.entity.ErrorResponse;
import com.pac.ride.entity.LoginRequest;
import com.pac.ride.entity.SuccessResponse;
import com.pac.ride.service.PassengerService;
import com.pac.ride.util.JWTUtil;
import com.prac.ride.entity.passenger.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    PassengerService passengerService;

    @PostMapping("/register")
    public ResponseEntity<ApplicationResponse> register(@RequestBody Passenger passenger){
        if(passengerService.savePassenger(passenger))
            return new ResponseEntity<>(new ApplicationResponse("Info saved successfully "), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(new ErrorResponse("Problem saving user info "),HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping("/login")
    public Mono<ResponseEntity<ApplicationResponse>> login(@RequestBody LoginRequest loginRequest){
        return passengerService.validatePassenger(loginRequest.getEmail(), loginRequest.getPassword()).flatMap(i->{
                 if (i != null) {
                    String token = jwtUtil.generateToken(i);
                        return Mono.just(ResponseEntity.ok(new SuccessResponse(token)));
                    }
                    else
                        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Invalid Creds ")));

        });

    }

}
