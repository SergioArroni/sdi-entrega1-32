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

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;



}
