package ch.zli.m223.ksh20.user.controller.rest.dto;

import ch.zli.m223.ksh20.user.model.Reservation;
import ch.zli.m223.ksh20.user.model.enums.Duration;

import java.time.LocalDate;

public class ReservationDto {
    private Long id;
    private LocalDate date;
    private Duration duration;
    private boolean accepted;
    private Long userId;

    public ReservationDto() {}

    public ReservationDto(Reservation r) {
        this.id = r.getId();
        this.date = r.getDate();
        this.duration = r.getDuration();
        this.accepted = r.isAccepted();
        this.userId = r.getUser().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
