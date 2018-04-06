package com.locker.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

@Entity
public class Locker {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinTable(name = "LOCKER_USER",
               joinColumns = {@JoinColumn(name = "LOCKER_ID")},
               inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    private User owner;

    public Locker() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Locker [id=" + id + ", owner=" + owner + "]";
    }

}
