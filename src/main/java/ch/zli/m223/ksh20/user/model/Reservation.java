package ch.zli.m223.ksh20.user.model;

import ch.zli.m223.ksh20.user.model.enums.Duration;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "`reservation`")
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private Duration duration;

    private boolean accepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    public Reservation() { }

    public Reservation(LocalDate date, Duration duration) {
        this.date = date;
        this.duration = duration;
        this.accepted = false;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserById(Long id) {
        this.user = new User();
        this.user.setId(id);
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
