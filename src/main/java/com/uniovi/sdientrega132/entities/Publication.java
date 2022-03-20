package com.uniovi.sdientrega132.entities;

import javax.persistence.*;
import java.util.Date;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
