package com.pac.ride.service;

import com.pac.ride.entity.RideRequest;
import com.pac.ride.entity.RideResponse;
import com.pac.ride.repo.PassengerRepo;
import com.prac.ride.constants.ApplicationTopics;
import com.prac.ride.entity.passenger.Passenger;
import com.prac.ride.entity.ride.Ride;
import com.prac.ride.entity.ride.RideStatus;
import com.prac.ride.exception.AuthException;
import com.prac.ride.util.ApplicationUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerRepo passengerRepo;

    @Qualifier("rideEventProducerTemplate")
    @Autowired
    KafkaTemplate<String,Ride> kafkaTemplate;

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

    //TODO: only use for debug
    private void getAllPassengers(){
        Flux<Passenger> passengerFlux = passengerRepo.getAllPassengers();
        passengerFlux.subscribe(System.out::println);
    }

    public RideResponse bookRide(Long passengerId, RideRequest request){
        String rideId = ApplicationUtil.generateUUID();
        Ride ride = Ride.newBuilder()
                .setId(rideId)
                .setPassengerId(passengerId.toString())
                .setPickupLocation(request.getPickupLocation())
                .setDropOffLocation(request.getDropoffLocation())
                .setRequestTime(ApplicationUtil.getCurrentFormattedTime())
                .setStatus(RideStatus.REQUESTED)
                .build();
        CompletableFuture<SendResult<String, Ride>> future = null;
        RideResponse response = new RideResponse();
        try{
            MessageBuilder<Ride> messageBuilder = MessageBuilder.withPayload(ride)
                            .setHeader(KafkaHeaders.TOPIC, ApplicationTopics.passengerRequestTopic);
            future = kafkaTemplate.send(messageBuilder.build());

            future.whenComplete(((stringRideSendResult, throwable) -> {
                if(throwable == null){
                    log.info("Ride request sent successfully");
                    response.setRideId(rideId);
                    response.setMessage(RideStatus.REQUESTED.name());
                    }
                else {
                    log.error("error sending ride request {}", throwable.getMessage());
                    response.setRideId("Unable to create ride");
                    response.setMessage(RideStatus.CANCELED.name());
                }
            }));

        }catch (Exception e){
            response.setRideId("Unable to create ride");
            response.setMessage(RideStatus.CANCELED.name());
            log.error("Error sending ride request to {}",ApplicationTopics.passengerRequestTopic,e);
        }finally {
         kafkaTemplate.flush();
        }
        return response;
    }

}
