package com.escape.way.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "user")
@Getter
@Setter
public class User implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(length = 45)
    private String name;

    @Column(length = 45)
    private String id;

    @Column(length = 45)
    private String pw;

    @Column
    private float userX;

    @Column
    private float userY;

    @OneToMany(mappedBy = "user")
    private List<UAMap> uamaps = new ArrayList<UAMap>();

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public float getUserX() {
        return userX;
    }

    public void setUserX(float userX) {
        this.userX = userX;
    }

    public float getUserY() {
        return userY;
    }

    public void setUserY(float userY) {
        this.userY = userY;
    }

    public List<UAMap> getUamaps() {
        return uamaps;
    }

    public void setUamaps(List<UAMap> uamaps) {
        this.uamaps = uamaps;
    }
}
