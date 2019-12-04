package tas2019.library.services.bookstatus;

import tas2019.library.entities.BookStatus;

import java.util.Optional;

public interface BookStatusService {
    Optional<BookStatus> getById(int id);
    Iterable<BookStatus> getAll();

    BookStatus save(BookStatus status);

    int countByReaderId(int id);
}
