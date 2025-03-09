package com.pac.ride.repo;

import com.prac.ride.entity.Passenger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Service
public class PassengerRepoImpl implements PassengerRepo{

    @Qualifier("passengerMongoTemplate")
    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Passenger> savePassenger(Passenger passenger){
         return reactiveMongoTemplate.save(passenger);
    }

    public Flux<Passenger> getAllPassengers(){
        return reactiveMongoTemplate.findAll(Passenger.class);
    }

}
