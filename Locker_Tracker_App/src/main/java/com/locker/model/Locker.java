package com.locker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Locker {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @Column(name = "OWNER_ID")
    private String owner;

    public Locker() {
        
    }    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(mappedBy = "user")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Locker [id=" + id + ", owner=" + owner + "]";
    }
    
}
