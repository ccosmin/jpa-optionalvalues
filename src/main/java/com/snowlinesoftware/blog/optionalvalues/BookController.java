package com.snowlinesoftware.blog.optionalvalues;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
  @Autowired
  private BookService bookService;

  @PostConstruct
  private void fillBooks() {
    bookService.addBook("Awesome thriller", 100, "John Doe");
    bookService.addBook("Crime does not pay", 200, "Jane Doe");
  }

  @GetMapping("/search-by-title")
  @ResponseBody
  private List<Book> searchByTitle(@RequestParam String title) {
    return bookService.findBooks(title, Optional.empty());
  }

  @GetMapping("/search-by-title-author")
  @ResponseBody
  private List<Book> searchByTitle(@RequestParam(required = false) String title,
      @RequestParam(required = false) String author) {
    return bookService.findBooks(title, Optional.ofNullable(author));
  }

  @GetMapping("/search-by-title-price")
  @ResponseBody
  private List<Book> searchByTitlePrice(@RequestParam(required = false) String title,
      @RequestParam(required = false) Integer price) {
    return bookService.findBooksByTitlePrice(Optional.ofNullable(title),
        Optional.ofNullable(price));
  }

  @GetMapping("/search-by-typed-query")
  @ResponseBody
  private List<Book> searchByTitlePrice(@RequestParam(required = false) String title,
      @RequestParam(required = false) Integer price,
      @RequestParam(required = false) String author) {
    return bookService.findByTypedQuery(Optional.ofNullable(title),
        Optional.ofNullable(price),
        Optional.ofNullable(author));
  }
}
