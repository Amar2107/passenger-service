package com.pac.ride.repo;



import com.prac.ride.entity.passenger.Passenger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PassengerRepo {

    public void savePassenger(Passenger passenger);

    public Flux<Passenger> getAllPassengers();

    public Mono<Passenger> getPassengerByEmail(String email);
}
