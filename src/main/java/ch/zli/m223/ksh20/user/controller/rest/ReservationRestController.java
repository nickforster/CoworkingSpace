package ch.zli.m223.ksh20.user.controller.rest;


import ch.zli.m223.ksh20.user.controller.rest.dto.ReservationDto;
import ch.zli.m223.ksh20.user.model.Reservation;
import ch.zli.m223.ksh20.user.model.User;
import ch.zli.m223.ksh20.user.security.JwtUtils;
import ch.zli.m223.ksh20.user.service.ReservationService;
import ch.zli.m223.ksh20.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservation")
public class ReservationRestController {

    @Autowired
    private ReservationService reservationService;
    @PostMapping("/create")
    Reservation register(
            @RequestBody ReservationDto reservation,
            @RequestHeader("Authorization") String header
    ) {
        String token = header.split(" ")[1].trim();

        return reservationService.addReservation(reservation, token);
    }

    @DeleteMapping("/{id}/delete")
    void delete(
            @PathVariable Long id,
            @RequestHeader("Authorization") String header
    ) {
        String token = header.split(" ")[1].trim();

        reservationService.deleteReservation(id, token);
    }

    @PutMapping("/{id}/update")
    Reservation update(
            @PathVariable Long id,
            @RequestBody ReservationDto reservation,
            @RequestHeader("Authorization") String header
    ) {
        String token = header.split(" ")[1].trim();

        return reservationService.updateReservation(id, reservation, token);
    }

    @PutMapping("/{id}/accept")
    Reservation accept(
            @PathVariable Long id,
            @RequestHeader("Authorization") String header
    ) {
        String token = header.split(" ")[1].trim();

        return reservationService.acceptReservation(id, token);
    }

    @PostMapping("/list")
    List<ReservationDto> getReservationList(
            @RequestHeader("Authorization") String header
    ) {
        String token = header.split(" ")[1].trim();

        return reservationService.getReservationList(token).stream().map(ReservationDto::new).collect(Collectors.toList());
    }
}
