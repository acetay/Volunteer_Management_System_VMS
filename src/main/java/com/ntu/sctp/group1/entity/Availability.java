package com.ntu.sctp.group1.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="availability")
public class Availability {

    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Id
    public Integer id;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "date")
    public LocalDate date;

    @Column(name = "avail")
    public boolean avail;

    @Column(name = "timeslot")
    public String timeslot;

    @Column(name="created_at", updatable= false)
    Timestamp createdAt = new Timestamp(new Date().getTime());

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


    @ManyToOne
    @JoinColumn(name = "volunteer_id", referencedColumnName = "id")
    public Volunteer volunteer;

}