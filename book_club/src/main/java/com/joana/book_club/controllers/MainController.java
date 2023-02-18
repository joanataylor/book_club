package com.joana.book_club.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.joana.book_club.models.Book;
import com.joana.book_club.models.LoginUser;
import com.joana.book_club.models.User;
import com.joana.book_club.services.BookService;
import com.joana.book_club.services.UserService;

@Controller
public class MainController {
  // Add once service is implemented:
  @Autowired
  private UserService userServ;
  @Autowired
  private BookService bookService;

  @GetMapping("/")
  public String index(Model model) {

    // Bind empty User and LoginUser objects to the JSP
    // to capture the form input
    model.addAttribute("newUser", new User());
    model.addAttribute("newLogin", new LoginUser());
    return "index.jsp";
  }

  @GetMapping("/books")
  public String dashboard(Model model, HttpSession session) {
    if (session.getAttribute("userId") == null) {
      return "redirect:/";
    }
    Long id = (Long) session.getAttribute("userId");
    User user = userServ.getById(id);
    List<Book> book = bookService.allBook();
    model.addAttribute("user", user);
    model.addAttribute("book", book);
    model.addAttribute("allBooks", bookService.allBook());
    return "dashboard.jsp";
  }

  @PostMapping("/register")
  public String register(@Valid @ModelAttribute("newUser") User newUser,
      BindingResult result, Model model, HttpSession session) {

    // TO-DO Later -- call a register method in the service
    // to do some extra validations and create a new user!
    User user = userServ.register(newUser, result);

    if (result.hasErrors()) {
      // Be sure to send in the empty LoginUser before
      // re-rendering the page.
      model.addAttribute("newLogin", new LoginUser());
      return "index.jsp";
    }

    // No errors!
    // TO-DO Later: Store their ID from the DB in session,
    // in other words, log them in.
    session.setAttribute("userId", user.getId());
    return "redirect:/books";
  }

  @PostMapping("/login")
  public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin,
      BindingResult result, Model model, HttpSession session) {

    // Add once service is implemented:
    User user = userServ.login(newLogin, result);

    if (result.hasErrors()) {
      model.addAttribute("newUser", new User());
      return "index.jsp";
    }

    // No errors!
    // TO-DO Later: Store their ID from the DB in session,
    // in other words, log them in.
    session.setAttribute("userId", user.getId());
    return "redirect:/books";
  }

  @RequestMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/";
  }

  // *************************************************************** */
  @RequestMapping("/books/new")
  public String newDojo(@ModelAttribute("book") Book book, Model model) {
    // List<User> users = userServ.allUsers();
    // model.addAttribute("users", users);
    return "books/createBook.jsp";
  }

  @PostMapping("/books/create")
  public String createeeebook(@Valid @ModelAttribute("newBook") Book book, BindingResult result, Model model) {
    if (result.hasErrors()) {
      List<Book> books = bookService.allBook();
      model.addAttribute("book", books);
      return "books/createBook.jsp";
    } else {
      bookService.createBook(book);
      // System.out.println("***********" + ninja.getDojo().getId());
      return "redirect:/books";
    }
  }

  // @GetMapping("/books/{user_id}")
  // public String showBook(@PathVariable Long user_id, Model model){
  //   // User userPerson = userServ.getById(); 
  //   // model.addAttribute("user", userPerson);
  //   return "redirect:/books";
  // }


}