package com.pac.ride.service;

import com.pac.ride.repo.PassengerRepo;
import com.prac.ride.entity.Passenger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerRepo passengerRepo;

    public boolean savePassenger(Passenger passenger){
        try {
            passengerRepo.savePassenger(passenger);
        }catch (Exception e){
            log.error("Error saving passenger to DB ",e);
            return false;
        }
        return true;
    }

    public void getAllPassengers(){
        Flux<Passenger> passengerFlux = passengerRepo.getAllPassengers();
        passengerFlux.subscribe(System.out::println);
    }

}
