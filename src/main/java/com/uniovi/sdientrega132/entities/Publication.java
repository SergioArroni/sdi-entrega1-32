package com.uniovi.sdientrega132.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="publication")
public class Publication {
    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String text;
    private Date publishingDate;
    private String foto;

    @ManyToMany
    private List<User> recomendaciones;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Publication() {
    }

    public Publication(String title, String text, User user) {
        this.title=title;
        this.text=text;
        this.user=user;
        publishingDate = new Date();
        foto = "";
        recomendaciones = new ArrayList<User>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(Date publishingDate) {
        this.publishingDate = publishingDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<User> getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(List<User> recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public String printRecomendations(){
        return recomendaciones.size() + " Recs.";
    }

    @Override
    public String toString() {
        return "Publication{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", publishingDate=" + publishingDate +
                '}';
    }
}
