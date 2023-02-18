package com.joana.book_club.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joana.book_club.models.Book;
import com.joana.book_club.repositories.BookRepository;

@Service
public class BookService {

@Autowired
  BookRepository bookRepository;

  public Book createBook(Book book){
    return bookRepository.save(book);
  }

  public List<Book> allBook(){
    return bookRepository.findAll();
  }

  
}