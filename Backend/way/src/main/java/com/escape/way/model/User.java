package com.escape.way.model;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name= "user")
@Getter
@Setter
public class User implements Serializable, UserDetails {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    @Column
    private String name;
    private String userId;
    private String pw;
    private float userX;
    private float userY;
    private Long kakaoId;
    private String auth;

    @OneToMany(mappedBy = "user")
    private List<UAMap> uamaps = new ArrayList<UAMap>();

    public Long getUserNo() { return userNo; }

    public void setUserNo(Long userNo) { this.userNo = userNo; }

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

    public Long getKakaoId() { return kakaoId; }

    public void setKakaoId(Long kakaoId) {
        this.kakaoId = kakaoId;
    }

    @Override
    public String toString() {
        return this.name + ", " + this.pw + ", " + this.userId + ", " + this.getUserX() + ", " + this.getUserY() + ", " + this.getKakaoId();
    }

    //sprint security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();

        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }

        return roles;
    }

    @Override
    public String getPassword() {
        return pw;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
