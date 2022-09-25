package com.escape.way.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
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
    private String pw;
    private Long kakaoId;
    private String auth;

    @Column(precision = 17, scale = 14)
    private double latitude;
    @Column(precision = 17, scale = 14)
    private double longitude;

    @Column(unique = true)
    private String userId;

    @Column
    private ZonedDateTime updateTime;

    @OneToMany(mappedBy = "user")
    private List<UAMap> uamaps = new ArrayList<UAMap>();

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

    public ZonedDateTime getUpdateTime() { return this.updateTime; }

    public void setUpdateTime(ZonedDateTime updateTime) { this.updateTime = updateTime; }
    @Override
    public String toString() {
        return this.name + ", " + this.pw + ", " + this.userId + ", " + this.latitude + ", " + this.longitude + ", " + this.kakaoId;
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

    public void setAuth(String role_user) {
        this.auth = role_user;
    }
}
