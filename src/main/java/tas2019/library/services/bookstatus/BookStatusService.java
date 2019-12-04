package tas2019.library.services.bookstatus;

import tas2019.library.entities.BookStatus;
import tas2019.library.exceptions.BookLimitExceededException;

import java.util.Optional;

public interface BookStatusService {
    Optional<BookStatus> getById(int id);
    Iterable<BookStatus> getAll();

    BookStatus save(BookStatus status) throws BookLimitExceededException;

    int countByReaderId(int id);
}
