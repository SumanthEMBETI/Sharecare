package com.sharecare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class User {

    // ================= ID =================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ================= USER DETAILS =================

    private String name;

    @Column(unique = true, nullable = false)
    private String phone;

    private String password;

    private String address;

    // ================= PROFILE PHOTO =================

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] profilePhoto;

    private String profilePhotoType;

    // ================= DEFAULT CONSTRUCTOR =================

    public User() {
    }

    // ================= GET ID =================

    public Long getId() {
        return id;
    }

    // ================= SET ID =================

    public void setId(Long id) {
        this.id = id;
    }

    // ================= GET NAME =================

    public String getName() {
        return name;
    }

    // ================= SET NAME =================

    public void setName(String name) {
        this.name = name;
    }

    // ================= GET PHONE =================

    public String getPhone() {
        return phone;
    }

    // ================= SET PHONE =================

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // ================= GET PASSWORD =================

    public String getPassword() {
        return password;
    }

    // ================= SET PASSWORD =================

    public void setPassword(String password) {
        this.password = password;
    }

    // ================= GET ADDRESS =================

    public String getAddress() {
        return address;
    }

    // ================= SET ADDRESS =================

    public void setAddress(String address) {
        this.address = address;
    }

    // ================= GET PROFILE PHOTO =================

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    // ================= SET PROFILE PHOTO =================

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    // ================= GET PROFILE PHOTO TYPE =================

    public String getProfilePhotoType() {
        return profilePhotoType;
    }

    // ================= SET PROFILE PHOTO TYPE =================

    public void setProfilePhotoType(String profilePhotoType) {
        this.profilePhotoType = profilePhotoType;
    }
}

