package com.tmdttts.backend.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") private Long userId;
    @Column(name = "user_name", nullable = false, length = 150) private String userName;
    @Column(name = "user_email", nullable = false, unique = true, length = 255) private String userEmail;
    @Column(name = "user_phone", length = 30) private String userPhone;
    @Column(name = "user_password_hash", nullable = false, length = 255) private String userPasswordHash;
    @Column(name = "user_role", nullable = false, length = 20) private String userRole = "CUSTOMER";
    @Column(name = "user_status", nullable = false, length = 20) private String userStatus = "ACTIVE";
    @Column(name = "user_created_at", nullable = false) private OffsetDateTime userCreatedAt;
    @Column(name = "user_updated_at", nullable = false) private OffsetDateTime userUpdatedAt;
    @PrePersist void prePersist(){ var now=OffsetDateTime.now(); if(userCreatedAt==null)userCreatedAt=now; if(userUpdatedAt==null)userUpdatedAt=now; }
    @PreUpdate void preUpdate(){ userUpdatedAt=OffsetDateTime.now(); }
    public Long getUserId(){return userId;} public String getUserName(){return userName;} public void setUserName(String v){userName=v;}
    public String getUserEmail(){return userEmail;} public void setUserEmail(String v){userEmail=v;} public String getUserPhone(){return userPhone;} public void setUserPhone(String v){userPhone=v;}
    public String getUserPasswordHash(){return userPasswordHash;} public void setUserPasswordHash(String v){userPasswordHash=v;} public String getUserRole(){return userRole;} public void setUserRole(String v){userRole=v;}
    public String getUserStatus(){return userStatus;} public void setUserStatus(String v){userStatus=v;} public OffsetDateTime getUserCreatedAt(){return userCreatedAt;} public OffsetDateTime getUserUpdatedAt(){return userUpdatedAt;}
}
