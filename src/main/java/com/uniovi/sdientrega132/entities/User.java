package com.uniovi.sdientrega132.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private long id;
    //Nombre
    private String name;
    private String surname;
    @NotNull
    @Column(unique = true)
    private String email;
    private String password;
    @Transient
    private String passwordConfirm;
    private String role;

    @ManyToMany
    private List<Friend> amigos;

    public User() {
    }

    public User(String name, String surname, String email) {
        super();
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surnames) {
        this.surname = surnames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Friend> getAmigos() {
        return amigos;
    }

    public  void addAmigo(Long u){
        amigos.add(new Friend(this.id, u, false));
    }

    public boolean esAmigo(User u) {
        for (Friend f : amigos) {
            if (f.getUser1_id() == u.getId() || f.getUser2_id() == u.getId()) {
            return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surnames='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
