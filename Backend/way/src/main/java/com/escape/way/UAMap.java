package com.escape.way;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name= "uaMap")
@Getter
@Setter
public class UAMap implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapNo")
    private Long mapNo;

    @ManyToOne
    @JoinColumn(name = "userNo")
    private User user;

    @ManyToOne
    @JoinColumn(name = "appointmentNo")
    private Appointment appointment;

    @Column
    private boolean owner;
}
