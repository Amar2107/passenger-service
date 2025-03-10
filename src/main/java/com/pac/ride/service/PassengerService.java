package com.pac.ride.service;


import com.pac.ride.entity.RideRequest;
import com.pac.ride.entity.RideResponse;
import com.prac.ride.entity.passenger.Passenger;
import reactor.core.publisher.Mono;

public interface PassengerService {

    public boolean savePassenger(Passenger passenger);

    public Mono<Long> validatePassenger(String email, String password);

    public RideResponse bookRide(Long passengerId, RideRequest request);

}
