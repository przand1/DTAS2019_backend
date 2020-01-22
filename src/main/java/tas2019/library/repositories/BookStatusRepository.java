package tas2019.library.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tas2019.library.entities.BookStatus;

import java.util.List;


public interface BookStatusRepository extends CrudRepository<BookStatus, Integer> {
    int countByReaderId(int id);
    BookStatus findByBookId(int id);

    @Query("SELECT b FROM BookStatus b WHERE LOWER(b.book.title) LIKE LOWER(CONCAT('%',title,'%'))")
    List<BookStatus> findByBookTitle(@Param("title") String title, Pageable name) ;




}
