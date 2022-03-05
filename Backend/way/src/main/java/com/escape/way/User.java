package com.escape.way;

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

    @OneToMany(mappedBy = "user")
    private List<UAMap> uamaps = new ArrayList<UAMap>();
}
