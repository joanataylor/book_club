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

@Entity
@Table(name="books")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  @Size(min=3, max=30, message=" must be between 3 and 30 characters")
  private String title;
  @NotNull
  @Size(min=3, max=30, message=" must be between 3 and 30 characters")
  private String author;
  @NotNull
  @Size(min=3, max=30, message=" must be between 3 and 30 characters")
  private String thoughts;

  @Column(updatable=false)
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date createdAt;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date updatedAt;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="user_id")
  private User user;

  public Book(){
    
  }

  public Book(String title, String author, String thoughts, User user) {
    this.title = title;
    this.author = author;
    this.thoughts = thoughts;
    this.user = user;
  }
  
  @PrePersist
  protected void onCreate() {
    this.createdAt = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = new Date();
  }


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