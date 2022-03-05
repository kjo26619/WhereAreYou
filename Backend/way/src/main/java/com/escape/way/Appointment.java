package com.escape.way;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "appointment")
@Getter
@Setter
public class Appointment implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointmentNo")
    private Long appointmentNo;

    @Column(length = 45)
    private String name;

    @Column
    private String placeName;

    @Column
    private String placeXY;

    @Column
    private boolean openToAll;

    @OneToMany(mappedBy = "appointment")
    private List<UAMap> uamaps = new ArrayList<UAMap>();
}
