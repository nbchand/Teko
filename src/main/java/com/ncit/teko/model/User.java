package com.ncit.teko.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    @Column(unique = true)
    private String username;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String password;
    @Column
    private String phoneNumber;
    @Column
    private String gender;
    @Column
    private String moreInformation;
    @NotNull
    @Column(unique = true)
    private String email;
    @Column(name = "verification_code",length = 64)
    private String verificationCode;
    @Column
    private boolean enabled;

}
