package tas2019.library.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tas2019.library.entities.Book;
import tas2019.library.entities.BookStatus;
import tas2019.library.entities.Reader;
import tas2019.library.exceptions.BookLimitExceededException;
import tas2019.library.services.book.BookService;
import tas2019.library.services.bookstatus.BookStatusService;
import tas2019.library.services.reader.ReaderService;

import javax.annotation.PostConstruct;

@Component
public class ModelGen {

    @Autowired
    private BookService bookService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private BookStatusService bookStatusService;

    @PostConstruct
    void generateMockData() {
        Book tok = new Book();
        tok.setAuthor("Tokarczuk Olga");
        tok.setCategory("Publicystyka");
        tok.setTitle("Podróż ludzi Księgi");
        tok.setYear(1993);

        Book lotr = new Book();
        lotr.setYear(1999);
        lotr.setTitle("Władca Pieścieni: Dwie wieże");
        lotr.setCategory("Fantastyka");
        lotr.setAuthor("Tolkien J.R.R.");

        Book lotr2 = new Book();
        lotr2.setYear(1999);
        lotr2.setTitle("Władca Pieścieni: Dwie wieże");
        lotr2.setCategory("Fantastyka");
        lotr2.setAuthor("Tolkien J.R.R.");

        Book king = new Book();
        king.setAuthor("King Stephen");
        king.setTitle("To");
        king.setYear(2018);
        king.setCategory("Kryminał");

        Reader nowak = new Reader();
        nowak.setLastName("Nowak");
        nowak.setFirstName("Jan");
        nowak.setPhone("493843193");
        nowak.setAddress("Mickiewicza 6B/5, 60-243 Poznań");
        nowak.setEmail("nowak@abc.de");

        Reader gosia = new Reader();
        gosia.setLastName("Stępień");
        gosia.setFirstName("Małgorzata");
        gosia.setPhone("382917435");
        gosia.setAddress("Zakopiańska 83, 62-653 Kiekrz");
        gosia.setEmail("stepien@abc.de");

        Reader ania = new Reader();
        ania.setLastName("Nowakowska");
        ania.setFirstName("Anna");
        ania.setPhone("174839236");
        ania.setAddress("Wioślarska 1, 60-342 Poznań");
        ania.setEmail("nowakowska@abc.de");

        readerService.save(ania);
        readerService.save(gosia);
        readerService.save(nowak);

        bookService.save(tok);
        bookService.save(lotr);
        bookService.save(lotr2);
        bookService.save(king);

        BookStatus tokSt = new BookStatus();
        tokSt.setBook(tok);
        BookStatus lotrSt = new BookStatus();
        lotrSt.setBook(lotr);
        BookStatus lotr2St = new BookStatus();
        lotr2St.setBook(lotr2);
        BookStatus kingSt = new BookStatus();
        kingSt.setBook(king);

        try {
            bookStatusService.save(tokSt);
            bookStatusService.save(lotrSt);
            bookStatusService.save(lotr2St);
            bookStatusService.save(kingSt);
        } catch (BookLimitExceededException e) {
            e.printStackTrace();
        }

    }
}
