package com.pac.ride.service;

import com.pac.ride.entity.RideRequest;
import com.pac.ride.entity.RideResponse;
import com.pac.ride.exception.AuthException;
import com.pac.ride.repo.PassengerRepo;
import com.prac.ride.entity.passenger.Passenger;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerRepo passengerRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean savePassenger(Passenger passenger){
        try {
            String hashPassword = passwordEncoder.encode(passenger.getPassword());
            passenger.setPassword(hashPassword);
            passengerRepo.savePassenger(passenger);
        }catch (Exception e){
            log.error("Error saving passenger to DB ",e);
            return false;
        }
        return true;
    }

    public Mono<Long> validatePassenger(String email, String password){

        return passengerRepo.getPassengerByEmail(email).flatMap(passenger ->{
            if (passwordEncoder.matches(password,passenger.getPassword()))
                return Mono.just(passenger.getId());
            else
                return Mono.error(new AuthException("Invalid Credentials"));
        });
    }

    @PostConstruct
    private void getAllPassengers(){
        Flux<Passenger> passengerFlux = passengerRepo.getAllPassengers();
        passengerFlux.subscribe(System.out::println);
    }

    public RideResponse bookRide(Long passengerId, RideRequest request){
        //TODO: add ride service call
        return new RideResponse("1234","Ride created");
    }

}
