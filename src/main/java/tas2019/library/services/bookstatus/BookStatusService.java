package tas2019.library.services.bookstatus;

import org.springframework.data.domain.Pageable;
import tas2019.library.entities.BookStatus;
import tas2019.library.exceptions.BookLimitExceededException;
import tas2019.library.exceptions.CardExpiredException;
import tas2019.library.exceptions.ReaderHasFineException;

import java.util.Optional;

public interface BookStatusService {
    Optional<BookStatus> getById(int id);
    Iterable<BookStatus> getAll();

    BookStatus save(BookStatus status) throws BookLimitExceededException, CardExpiredException, ReaderHasFineException;

    BookStatus uncheckedSave(BookStatus status);

    int countByReaderId(int id);

    Iterable<BookStatus> getByBookTitle(String title, Pageable name2);

    int countByBookTitle(String title);


}
