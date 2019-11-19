package tas2019.library.services.bookstatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tas2019.library.entities.BookStatus;
import tas2019.library.repositories.BookRepository;
import tas2019.library.repositories.BookStatusRepository;
import tas2019.library.repositories.ReaderRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class BookStatusServiceImpl implements BookStatusService {
    @Autowired
    private BookStatusRepository repository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public Optional<BookStatus> getById(int id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<BookStatus> getAll() {
        return repository.findAll();
    }

    @Override
    public BookStatus save(BookStatus status) {
        /*
            Jeśli zawiera książkę lub czytelnika o złym id, nie zapisze i rzuci wyjątek.
         */
        if (
                ! bookRepository.existsById(status.getBook().getId()) ||
                ( Objects.nonNull(status.getReader()) && ! readerRepository.existsById(status.getReader().getId()) )
        ) {
            throw new IllegalArgumentException();
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
