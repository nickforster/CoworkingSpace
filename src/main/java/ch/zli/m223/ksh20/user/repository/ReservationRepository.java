package ch.zli.m223.ksh20.user.repository;

import ch.zli.m223.ksh20.user.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<List<Reservation>> findAllByUserId(Long userId);
}
