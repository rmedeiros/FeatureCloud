package com.onekin.featurecloud.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "developer")
public class Developer {

    @Id
    @Column(name = "iddeveloper")
    int idDeveloper;
    String name;
    String email;

    public Developer() {
    }


    public Developer(int idDeveloper, String name) {
        super();
        this.idDeveloper = idDeveloper;
        this.name = name;
    }


    public int getIdDeveloper() {
        return idDeveloper;
    }

    public void setIdDeveloper(int idDeveloper) {
        this.idDeveloper = idDeveloper;
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


}
