package com.escape.way.model;

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
    private float placeX;

    @Column
    private float placeY;

    @Column
    private boolean openToAll;

    @OneToMany(mappedBy = "appointment")
    private List<UAMap> uamaps = new ArrayList<UAMap>();

    public Long getAppointmentNo() {
        return appointmentNo;
    }

    public void setAppointmentNo(Long appointmentNo) {
        this.appointmentNo = appointmentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public float getPlaceX() {
        return placeX;
    }

    public void setPlaceX(float placeX) {
        this.placeX = placeX;
    }

    public float getPlaceY() {
        return placeY;
    }

    public void setPlaceY(float placeY) {
        this.placeY = placeY;
    }

    public boolean isOpenToAll() {
        return openToAll;
    }

    public void setOpenToAll(boolean openToAll) {
        this.openToAll = openToAll;
    }

    public List<UAMap> getUamaps() {
        return uamaps;
    }

    public void setUamaps(List<UAMap> uamaps) {
        this.uamaps = uamaps;
    }
}
