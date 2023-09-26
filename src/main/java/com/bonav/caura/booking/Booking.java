package com.bonav.caura.booking;

import com.bonav.caura.cars.Car;
import com.bonav.caura.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Booking     {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne
    Car car;
    @ManyToOne
    User user;
    LocalDate start;
    LocalDate end;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @PrePersist
    void beforeInsert(){
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }

    @PreUpdate
    void beforeUpdate(){
        this.updatedAt=LocalDateTime.now();
    }
    }

