package tas2019.library.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import tas2019.library.entities.Book;
import tas2019.library.entities.BookStatus;
import tas2019.library.entities.Reader;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookStatusRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookStatusRepository bookStatusRepository;

    @Test
    public void countByReaderIdShouldReturn3() {
        Reader reader = new Reader();

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

        Assert.assertEquals(3, bookStatusRepository.countByReaderId(reader.getId()));

    }

    @Test
    public void countByReaderShouldReturn0() {
        Reader reader = new Reader();
        entityManager.persist(reader);
        entityManager.flush();

        Assert.assertEquals(0, bookStatusRepository.countByReaderId(reader.getId()));
    }
}