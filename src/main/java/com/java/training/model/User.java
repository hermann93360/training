package com.java.training.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tr_user")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
                      strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id",updatable = false, nullable = false)
    private UUID id;

    @Column
    private String name;

    @Column
    private String email;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column
    private UUID idOfKeycloak;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", idOfKeycloak=" + idOfKeycloak +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getIdOfKeycloak() {
        return idOfKeycloak;
    }

    public void setIdOfKeycloak(UUID idOfKeycloak) {
        this.idOfKeycloak = idOfKeycloak;
    }

    public User(UUID id, String name, String email, UUID idOfKeycloak) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.idOfKeycloak = idOfKeycloak;
    }

    public User(){

    }

}
