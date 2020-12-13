package com.snowlinesoftware.blog.optionalvalues;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {
  @Query("select b from Book b where "
      + "(:price is null or b.price = :price) and "
      + "(:title is null or b.title = :title) and "
      + "(:authorName is null or b.author.name = :authorName)")
  List<Book> findByTitlePriceAuthor(String title, Integer price, String authorName);
}
