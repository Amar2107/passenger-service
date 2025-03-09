package com.pac.ride.repo;


import com.prac.ride.entity.Passenger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PassengerRepo {

    public Mono<Passenger> savePassenger(Passenger passenger);

    public Flux<Passenger> getAllPassengers();
}
