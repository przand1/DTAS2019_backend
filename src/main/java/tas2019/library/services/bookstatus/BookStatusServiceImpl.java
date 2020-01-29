package tas2019.library.services.bookstatus;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tas2019.library.entities.BookStatus;
import tas2019.library.exceptions.BookLimitExceededException;
import tas2019.library.exceptions.CardExpiredException;
import tas2019.library.exceptions.ReaderHasFineException;
import tas2019.library.repositories.BookRepository;
import tas2019.library.repositories.BookStatusRepository;
import tas2019.library.repositories.ReaderRepository;
import tas2019.library.services.reader.ReaderService;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class BookStatusServiceImpl implements BookStatusService {
    @Autowired
    private BookStatusRepository repository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private ReaderService readerService;

    private Logger logger = Logger.getLogger(BookStatusService.class);

    @Override
    public Optional<BookStatus> getById(int id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<BookStatus> getAll() {
        List list = (List) repository.findAll();
        Collections.sort(list);
        return list;
    }

    @Override
    public BookStatus save(BookStatus status) throws BookLimitExceededException, CardExpiredException, ReaderHasFineException {
        /*
            Jeśli zawiera książkę lub czytelnika o złym id, nie zapisze i rzuci wyjątek.
         */
        checkInput(status);
        if (status.isRented()) {
            handleRental(status);
        } else {
            handleReturn(status);
        }
        return repository.save(status);
    }

    private void checkInput(BookStatus status) {
        if (! bookRepository.existsById(status.getBook().getId())) {
            logger.error("Book with ID " + status.getBook().getId() + " not found.");
            throw new IllegalArgumentException();
        }
        if (
                Objects.nonNull(status.getReader()) &&
                ! readerRepository.existsById(status.getReader().getId())
        ) {
            logger.error("Reader with ID " + status.getReader().getId() + " not found.");
            throw new IllegalArgumentException();
        }
    }

    private void handleRental(BookStatus status) throws BookLimitExceededException, CardExpiredException, ReaderHasFineException {
        if (repository.countByReaderId(status.getReader().getId()) >= 4) {
            throw new BookLimitExceededException("Already has 4 or more books");
        }
        if (!readerService.readerCardIsValid(status.getReader().getId())) {
            throw new CardExpiredException("Card expired");
        }
        if (readerService.readerHasFine(status.getReader().getId())) {
            throw new ReaderHasFineException("Fined!");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        status.setRentedOn(calendar.getTime());
        calendar.add(Calendar.DATE,  14);
        status.setRentedUntil(calendar.getTime());
    }

    private void handleReturn(BookStatus status) {
        Date now = new Date();
        Optional<BookStatus> optionalBookStatus = repository.findById(status.getId());
        if (
                optionalBookStatus.isPresent() &&
                optionalBookStatus.get().getRentedUntil().compareTo(now) < 0
        ) {
            long diffInMillies = Math.abs(optionalBookStatus.get().getRentedUntil().getTime() - now.getTime());
            int diff = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            readerService.applyFine(diff, optionalBookStatus.get().getReader().getId());
        }
        status.setRentedUntil(null);
        status.setRentedOn(null);
    }

    @Override
    public BookStatus uncheckedSave(BookStatus status) {
        return repository.save(status);
    }

    @Override
    public int countByReaderId(int id) {
        return repository.countByReaderId(id);
    }

    @Override
    public Iterable<BookStatus> getByBookTitle(String title, Pageable name2) {
        return repository.findByBookTitle(title,name2);
    }

    @Override
    public int countByBookTitle(String title) {
        return repository.countByBookTitle(title);
    }
}
