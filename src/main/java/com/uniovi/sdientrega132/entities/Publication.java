package com.uniovi.sdientrega132.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
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
    private String photo;
    private String state;

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
        photo = "";
        state = "Aceptada";
        recomendaciones = new ArrayList<>();
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

    public void setPublishingDate(Date publishingDate) {
        this.publishingDate = publishingDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<User> getRecommendations() {
        return recomendaciones;
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
