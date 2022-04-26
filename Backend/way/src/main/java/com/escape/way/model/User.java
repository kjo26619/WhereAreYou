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

    @Column
    private String name;
    private String userId;
    private String pw;
    private float userX;
    private float userY;
    private Long kakaoId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Long getKakaoId() {
        return kakaoId;
    }

    public void setKakaoId(Long kakaoId) {
        this.kakaoId = kakaoId;
    }

    @Override
    public String toString() {
        return this.name + ", " + this.pw + ", " + this.userId + ", " + this.getUserX() + ", " + this.getUserY() + ", " + this.getKakaoId();
    }
}
