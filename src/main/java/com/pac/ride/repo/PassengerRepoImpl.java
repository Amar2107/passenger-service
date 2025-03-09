package com.pac.ride.repo;

import com.prac.ride.entity.passenger.Passenger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Service
public class PassengerRepoImpl implements PassengerRepo{

    @Qualifier("passengerMongoTemplate")
    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;


    public void savePassenger(Passenger passenger){
         reactiveMongoTemplate.save(passenger).subscribe();
    }

    public Flux<Passenger> getAllPassengers(){
        return reactiveMongoTemplate.findAll(Passenger.class);
    }

    public Mono<Passenger> getPassengerByEmail(String email){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        return reactiveMongoTemplate.findOne(query,Passenger.class);
    }

}
