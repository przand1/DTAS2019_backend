package tas2019.library.services.bookstatus;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tas2019.library.entities.BookStatus;
import tas2019.library.repositories.BookRepository;
import tas2019.library.repositories.BookStatusRepository;
import tas2019.library.repositories.ReaderRepository;

import java.util.*;

@Service
public class BookStatusServiceImpl implements BookStatusService {
    @Autowired
    private BookStatusRepository repository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReaderRepository readerRepository;

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
    public BookStatus save(BookStatus status) {
        /*
            Jeśli zawiera książkę lub czytelnika o złym id, nie zapisze i rzuci wyjątek.
         */
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
        if (status.isRented()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            status.setRentedOn(calendar.getTime());
            calendar.add(Calendar.DATE,  14);
            status.setRentedUntil(calendar.getTime());
        } else {
            status.setRentedUntil(null);
            status.setRentedOn(null);
        }
        return repository.save(status);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public int countByReaderId(int id) {
        return repository.countByReaderId(id);
    }
}
