package com.joana.book_club.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joana.book_club.models.Book;
import com.joana.book_club.repositories.BookRepository;

@Service
public class BookService {

//The service speaks with the Repository,
//here we are calling an instance of the Repository
  @Autowired
  BookRepository bookRepository;

//method to create a book, takes in a book in ()
  public Book createBook(Book book) {
    return bookRepository.save(book);
  }
//methode to list all the books
  public List<Book> allBooks() {
    List<Book> allBooks = bookRepository.findAll();
    return allBooks;
  }

  // retrieves a book
  public Book findBook(Long id) {
    Optional<Book> optionalBook = bookRepository.findById(id);
    if (optionalBook.isPresent()) {
      return optionalBook.get();
    } else {
      return null;
    }
  }

  // deletes a book
  public void deleteBook(Book book) {
    bookRepository.delete(book);
  }

  // edit/updates a book
  public void updateBook(Book book) {
    bookRepository.save(book);
  }

}