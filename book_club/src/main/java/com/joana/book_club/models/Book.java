package com.joana.book_club.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

//When the Controller needs to retrieve or modify data,
//it sends requests to the Model layer.
//The Model layer then uses the ORM or DAO layer to interact with
//the database, perform the requested operations, and return the results to the Controller.

@Entity
@Table(name="books")
public class Book {

  //creates a unique ID-autoIcrementing type in sql database
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //list the fields that I want listed in the @Table
  @NotNull
  @Size(min=3, max=30, message=" must be between 3 and 30 characters")
  private String title;

  //notnull or notempty handles validations
  @NotNull
  @Size(min=3, max=30, message=" must be between 3 and 30 characters")
  private String author;

  @NotNull
  @Size(min=3, max=30, message=" must be between 3 and 30 characters")
  private String thoughts;

  //created a non user-able updatable field --- created and updated AT
  @Column(updatable=false)
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date createdAt;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date updatedAt;

  //creates the PrimaryKey element that joins this table to the One table...
  //meaning Books can belong to ONE user... and user can have many books
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="user_id")
  private User user;


  public Book(){
    //leave this contrusctor empty
  }

  //no need to put Long id, nor created and updated at
  //why? because these above mentioned will not change based on input from user
  public Book(String title, String author, String thoughts, User user) {
    this.title = title;
    this.author = author;
    this.thoughts = thoughts;
    this.user = user;
  }


//handles the updating and creating  
  @PrePersist
  protected void onCreate() {
    this.createdAt = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = new Date();
  }

//*********** getters and setters ************/
  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getThoughts() {
    return this.thoughts;
  }

  public void setThoughts(String thoughts) {
    this.thoughts = thoughts;
  }

  public Date getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return this.updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  
}