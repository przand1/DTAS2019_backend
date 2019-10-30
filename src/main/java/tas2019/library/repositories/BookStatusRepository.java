package tas2019.library.repositories;

import org.springframework.data.repository.CrudRepository;
import tas2019.library.entities.BookStatus;

public interface BookStatusRepository extends CrudRepository<BookStatus, Integer> {
}
