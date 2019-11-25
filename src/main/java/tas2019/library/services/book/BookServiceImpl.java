package tas2019.library.services.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tas2019.library.entities.Book;
import tas2019.library.entities.BookStatus;
import tas2019.library.repositories.BookRepository;
import tas2019.library.repositories.BookStatusRepository;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookRepository repository;
    @Autowired
    public BookStatusRepository bookStatusRepo;

    @Override
    public Optional<Book> getById(int id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Book> getAll() {
        return repository.findAll();
    }

    @Override
    public Iterable<Book> getAllPaging(Integer pageNr, Integer perPage) {
        return repository.findAll(PageRequest.of(pageNr, perPage));
    }

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }

    @Override
    public void delete(int id) {
        int statusId = bookStatusRepo.findByBookId(id).getId();
        bookStatusRepo.deleteById(statusId);
        repository.deleteById(id);
    }
}
