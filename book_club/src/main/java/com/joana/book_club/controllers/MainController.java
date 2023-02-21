package com.joana.book_club.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joana.book_club.models.Book;
import com.joana.book_club.models.LoginUser;
import com.joana.book_club.models.User;
import com.joana.book_club.services.BookService;
import com.joana.book_club.services.UserService;


//Controller speaks to the service and repository
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

  //handles register form
  @PostMapping("/register")

  //modelAttribute variable "newUser" is used in the form it corresponds to
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

  @GetMapping("/books")
  public String dashboard(Model model, HttpSession session) {
    if (session.getAttribute("userId") == null) {
      return "redirect:/";
    }
    Long id = (Long) session.getAttribute("userId");

    List<Book> books = bookService.allBooks();
    model.addAttribute("books", books);

    User user = userServ.getById(id);
    model.addAttribute("user", user);
    return "dashboard.jsp";
  }

  // *************************************************************** */
  @RequestMapping("/books/new")
  public String newBook(@ModelAttribute("book") Book book, HttpSession session, Model model) {
  //   if(result.hasErrors()) {	
  //   return "books/createBook.jsp";
  // }
  if (session.getAttribute("userId") == null) {
    return "redirect:/";
  }
  User user = userServ.getById((Long) session.getAttribute("userId"));
  model.addAttribute("user", user);
  return "books/createBook.jsp";
}

  @PostMapping("/books/create")
  public String createBook(@Valid @ModelAttribute("book") Book book, BindingResult result, Model model) {
    if (result.hasErrors()) {
      return "books/createBook.jsp";
    } else {
      bookService.createBook(book);
      List<Book> books = bookService.allBooks();
      model.addAttribute("books", books);
      return "redirect:/books";
    }
  }

  @DeleteMapping("/books/delete/{id}")
  public String delete(@PathVariable("id")Long id){
    Book book = bookService.findBook(id);
      bookService.deleteBook(book);
      return "redirect:/books";
  }
  
  @GetMapping("/books/{id}")
  public String display(@PathVariable("id")Long id, Model model){
    Book book = bookService.findBook(id);
    model.addAttribute("book", book);
    return "books/displayBook.jsp";
  }




  
  @RequestMapping("/books/edit/{id}")
  public String showOne(@PathVariable("id")Long id, Model model){
    Book books = bookService.findBook(id);
    model.addAttribute("books", books);
    return "books/editBook.jsp";
  }


  @PutMapping("/edit/{id}")
  public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("books") Book book,  BindingResult result, Model model) {
    if(result.hasErrors()){
      model.addAttribute("book", book);
      return "books/editBook.jsp";

    }else{
          bookService.updateBook(book);
          return "redirect:/books";
        }
  }

}