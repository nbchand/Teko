package com.ncit.teko.model;

import com.sun.istack.NotNull;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class User implements Serializable {

    private static final long serialVersionUID =  2L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int userId;

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

    @NotNull
    @Column(unique = true)
    private String email;

    @Column(name = "verification_code",length = 64)
    private String verificationCode;

    @Column
    private boolean enabled;

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return this.verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "{" +
            " userId='" + getUserId() + "'" +
            ", username='" + getUsername() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", password='" + getPassword() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", gender='" + getGender() + "'" +
            ", email='" + getEmail() + "'" +
            ", verificationCode='" + getVerificationCode() + "'" +
            ", enabled='" + isEnabled() + "'" +
            "}";
    }

}