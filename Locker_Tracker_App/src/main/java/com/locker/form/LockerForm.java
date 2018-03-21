package com.locker.form;

import javax.validation.constraints.Pattern;

public class LockerForm {

    @Pattern(regexp = "[0-9]+")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LockerForm [id=" + id + "]";
    }

}
