package ch.zli.m223.ksh20.user.service;

import ch.zli.m223.ksh20.user.controller.rest.dto.ReservationDto;
import ch.zli.m223.ksh20.user.model.Reservation;
import ch.zli.m223.ksh20.user.model.User;
import ch.zli.m223.ksh20.user.model.enums.Role;
import ch.zli.m223.ksh20.user.repository.ReservationRepository;
import ch.zli.m223.ksh20.user.security.JwtUtils;
import ch.zli.m223.ksh20.user.service.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    public Reservation addReservation(ReservationDto reservationDto, String token) {
        if (!jwtUtils.validateJwtToken(token)) throw new UnauthorizedException();
        if (Role.valueOf(jwtUtils.getRoleFromJwtToken(token)) == Role.GUEST) throw new UnauthorizedException();

        Reservation reservation = new Reservation(reservationDto.getDate(), reservationDto.getDuration());
        List<User> users = userService.getUserList();

        // user is member
        if (Role.valueOf(jwtUtils.getRoleFromJwtToken(token)) == Role.MEMBER) {

            for (User user : users) {
                if (Objects.equals(user.getId(), jwtUtils.getIdFromJwtToken(token))) {
                    reservation.setUser(user);
                }
            }

            // TODO: 12.07.2023 validation
            return reservationRepository.save(reservation);
        }

        // user is admin
        for (User user : users) {
            if (Objects.equals(user.getId(), reservationDto.getUserId())) {
                reservation.setUser(user);
            }
        }

        reservation.setAccepted(reservation.isAccepted());

        // TODO: 12.07.2023 validation
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationList(String token) {
        if (!jwtUtils.validateJwtToken(token)) throw new UnauthorizedException();
        if (Role.valueOf(jwtUtils.getRoleFromJwtToken(token)) == Role.GUEST) throw new UnauthorizedException();

        if (Role.valueOf(jwtUtils.getRoleFromJwtToken(token)) == Role.ADMIN) return reservationRepository.findAll();

        Long id = jwtUtils.getIdFromJwtToken(token);
        Optional<List<Reservation>> r = reservationRepository.findAllByUserId(id);
        return r.orElse(null);
    }

    public void deleteReservation(Long id, String token) {
        if (!jwtUtils.validateJwtToken(token)) throw new UnauthorizedException();
        if (Role.valueOf(jwtUtils.getRoleFromJwtToken(token)) == Role.GUEST) throw new UnauthorizedException();

        if (Role.valueOf(jwtUtils.getRoleFromJwtToken(token)) == Role.MEMBER) {
            Reservation r = reservationRepository.getReferenceById(id);

            if (Objects.equals(r.getUser().getId(), jwtUtils.getIdFromJwtToken(token))) {
                reservationRepository.deleteById(id);
                return;
            } else {
                throw new UnauthorizedException();
            }
        }

        reservationRepository.deleteById(id);
    }

    public Reservation updateReservation(Long id, ReservationDto reservation, String token) {
        if (!jwtUtils.validateJwtToken(token)) throw new UnauthorizedException();
        if (Role.valueOf(jwtUtils.getRoleFromJwtToken(token)) != Role.ADMIN) throw new UnauthorizedException();

        Reservation r = new Reservation(reservation.getDate(), reservation.getDuration());
        r.setId(id);
        r.setAccepted(reservation.isAccepted());

        List<User> users = userService.getUserList();

        for (User user : users) {
            if (Objects.equals(user.getId(), reservation.getUserId())) {
                r.setUser(user);
            }
        }

        return  reservationRepository.save(r);

        // Optional<Reservation> updatedReservation = reservationRepository.updateReservationById(r);
        // return updatedReservation.orElse(null);
    }

    public Reservation acceptReservation(Long id, String token) {
        if (!jwtUtils.validateJwtToken(token)) throw new UnauthorizedException();
        if (Role.valueOf(jwtUtils.getRoleFromJwtToken(token)) != Role.ADMIN) throw new UnauthorizedException();

        List<Reservation> reservations = reservationRepository.findAll();

        for (Reservation r : reservations) {
            if (Objects.equals(r.getId(), id)) {
                r.setAccepted(true);
                 return reservationRepository.save(r);
            }
            // if (Objects.equals(r.getId(), id)) return reservationRepository.updateReservationById(r).orElse(null);
        }

        return null;
    }
}
