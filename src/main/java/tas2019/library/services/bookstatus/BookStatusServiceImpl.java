package tas2019.library.services.bookstatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tas2019.library.entities.BookStatus;
import tas2019.library.repositories.BookStatusRepository;

import java.util.Optional;

@Service
public class BookStatusServiceImpl implements BookStatusService {
    @Autowired
    private BookStatusRepository repository;

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
        return repository.save(status);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
