package com.escape.way.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
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
    private Long appointmentNo;
    private String name;
    private String placeName;

    @Column(precision = 17, scale = 14)
    private double latitude;

    @Column(precision = 17, scale = 14)
    private double longitude;

    @Column
    private ZonedDateTime time;

    @Column
    private ZonedDateTime updateTime;

    @OneToMany(mappedBy = "appointment",  fetch = FetchType.LAZY)
    private List<UAMap> uamaps = new ArrayList<UAMap>();

    public List<UAMap> getUamaps() {
        return uamaps;
    }

    public void setUamaps(List<UAMap> uamaps) {
        this.uamaps = uamaps;
    }
}
