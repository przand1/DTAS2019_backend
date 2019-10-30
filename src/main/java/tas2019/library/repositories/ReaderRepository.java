package tas2019.library.repositories;

import org.springframework.data.repository.CrudRepository;
import tas2019.library.entities.Reader;

public interface ReaderRepository extends CrudRepository<Reader, Integer> {
}
