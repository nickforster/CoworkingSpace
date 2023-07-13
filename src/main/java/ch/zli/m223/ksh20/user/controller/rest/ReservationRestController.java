package ch.zli.m223.ksh20.user.controller.rest;


import ch.zli.m223.ksh20.user.controller.rest.dto.ReservationDto;
import ch.zli.m223.ksh20.user.model.Reservation;
import ch.zli.m223.ksh20.user.service.ReservationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservation")
public class ReservationRestController {

    @Autowired
    private ReservationService reservationService;
    @PostMapping("/create")
    @ApiOperation("Create a new reservation")
    Reservation register(
            @RequestBody @ApiParam("Reservation data") ReservationDto reservation,
            @RequestHeader("Authorization") @ApiParam("Authorization header with jwt token") String header
    ) {
        String token = header.split(" ")[1].trim();

        return reservationService.addReservation(reservation, token);
    }

    @DeleteMapping("/{id}/delete")
    @ApiOperation("Delete a reservation")
    void delete(
            @PathVariable @ApiParam("Id of the reservation") Long id,
            @RequestHeader("Authorization") @ApiParam("Authorization header with jwt token") String header
    ) {
        String token = header.split(" ")[1].trim();

        reservationService.deleteReservation(id, token);
    }

    @PutMapping("/{id}/update")
    @ApiOperation("Update a reservation")
    Reservation update(
            @PathVariable @ApiParam("Id of the reservation") Long id,
            @RequestBody @ApiParam("Reservation data")ReservationDto reservation,
            @RequestHeader("Authorization") @ApiParam("Authorization header with jwt token") String header
    ) {
        String token = header.split(" ")[1].trim();

        return reservationService.updateReservation(id, reservation, token);
    }

    @PutMapping("/{id}/accept")
    @ApiOperation("Accept a reservation as an admin")
    Reservation accept(
            @PathVariable @ApiParam("Id of the reservation") Long id,
            @RequestHeader("Authorization") @ApiParam("Authorization header with jwt token") String header
    ) {
        String token = header.split(" ")[1].trim();

        return reservationService.acceptReservation(id, token);
    }

    @PostMapping("/list")
    @ApiOperation("List all reservations")
    List<ReservationDto> getReservationList(
            @RequestHeader("Authorization") @ApiParam("Authorization header with jwt token") String header
    ) {
        String token = header.split(" ")[1].trim();

        return reservationService.getReservationList(token).stream().map(ReservationDto::new).collect(Collectors.toList());
    }
}
