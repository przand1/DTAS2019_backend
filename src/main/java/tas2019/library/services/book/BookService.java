package tas2019.library.services.book;

import tas2019.library.entities.Book;

import java.util.Optional;

public interface BookService {

    Optional<Book> getById(int id);
    Iterable<Book> getAll();
    Iterable<Book> getAllPaging(Integer pageNr,Integer perPage);

    Book save(Book book);
    void delete(int id);

}
