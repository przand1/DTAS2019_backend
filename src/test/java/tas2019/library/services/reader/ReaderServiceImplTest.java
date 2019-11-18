package tas2019.library.services.reader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tas2019.library.dtos.ReaderDTO;
import tas2019.library.entities.Book;
import tas2019.library.entities.BookStatus;
import tas2019.library.entities.Reader;

import static org.junit.jupiter.api.Assertions.*;

class ReaderServiceImplTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ReaderService readerService;

    @Test
    void shouldReturnCorrectDTO() {
        Reader reader = new Reader();
        reader.setEmail("asd");
        reader.setAddress("a s d");
        reader.setPhone("123");
        reader.setFirstName("alan");
        reader.setLastName("adams");

        Book book1 = new Book();
        Book book2 = new Book();
        Book book3 = new Book();
        BookStatus bookStatus1 = new BookStatus();
        BookStatus bookStatus2 = new BookStatus();
        BookStatus bookStatus3 = new BookStatus();
        entityManager.persist(book1);
        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.persist(book3);
        entityManager.persist(reader);
        bookStatus1.setBook(book1);
        bookStatus1.setReader(reader);
        bookStatus2.setBook(book2);
        bookStatus2.setReader(reader);
        bookStatus3.setBook(book3);
        bookStatus3.setReader(reader);
        entityManager.persist(bookStatus1);
        entityManager.persist(bookStatus2);
        entityManager.persist(bookStatus3);
        entityManager.flush();

        ReaderDTO dto = readerService.getDTO(reader);

        assertEquals(reader.getId(), dto.getId());
        assertEquals(reader.getLastName(), dto.getLastName());
        assertEquals(3, dto.getRentedBooksCount());

    }
}