package com.snowlinesoftware.blog.optionalvalues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
  @Autowired
  private EntityManager entityManager;

  @Autowired
  private BookRepository bookRepository;

  public List<Book> findBooks(String title, Optional<String> authorFirstName) {
    List<Book> books = bookRepository.findByTitlePriceAuthor(title,
        null,
        authorFirstName.orElse(null));
    return books;
  }

  public List<Book> findBooksByTitlePrice(Optional<String> title, Optional<Integer> price) {
    List<Book> books = bookRepository.findByTitlePriceAuthor(title.orElse(null),
        price.orElse(null),
        null);
    return books;
  }

  public List<Book> findByTypedQuery(Optional<String> title, Optional<Integer> price,
      Optional<String> author) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
    Root<Book> root = query.from(Book.class);
    List<Predicate> predicates = new ArrayList<>();
    if (title.isPresent())
      predicates.add(criteriaBuilder.equal(root.get("title"), title.get()));
    if (price.isPresent())
      predicates.add(criteriaBuilder.equal(root.get("price"), price.get()));
    if (author.isPresent())
      predicates.add(criteriaBuilder.equal(root.get("author").get("name"), author.get()));
    query.where(predicates.toArray(new Predicate[predicates.size()]));
    return entityManager.createQuery(query).getResultList();
  }

  public void addBook(String title, int price, String authorName) {
    Author a = new Author();
    a.setName(authorName);
    Book book = new Book();
    book.setAuthor(a);
    book.setPrice(price);
    book.setTitle(title);
    bookRepository.save(book);
  }
}
