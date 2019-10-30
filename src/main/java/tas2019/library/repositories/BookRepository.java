package tas2019.library.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tas2019.library.entities.Book;

public interface BookRepository extends CrudRepository<Book, Integer>, PagingAndSortingRepository<Book, Integer> {
}
