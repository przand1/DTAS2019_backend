package tas2019.library.components;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tas2019.library.entities.Book;
import tas2019.library.entities.BookStatus;
import tas2019.library.entities.Reader;
import tas2019.library.exceptions.BookLimitExceededException;
import tas2019.library.exceptions.CardExpiredException;
import tas2019.library.exceptions.ReaderHasFineException;
import tas2019.library.services.book.BookService;
import tas2019.library.services.bookstatus.BookStatusService;
import tas2019.library.services.reader.ReaderService;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class ModelGen {

    @Autowired
    private BookService bookService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private BookStatusService bookStatusService;

    private Logger logger = Logger.getLogger(ModelGen.class);

    @PostConstruct
    void generateMockData() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 1);
        Date date = calendar.getTime();


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

        Book og = new Book();
        og.setTitle("Ogniem i Mieczem");
        og.setAuthor("Sienkiewicz Henryk");
        og.setYear(1979);
        og.setCategory("Powieść historyczna");

        Book potop = new Book();
        potop.setTitle("Potop");
        potop.setAuthor("Sienkiewicz Henryk");
        potop.setYear(1979);
        potop.setCategory("Powieść historyczna");

        Reader nowak = new Reader();
        nowak.setLastName("Nowak");
        nowak.setFirstName("Jan");
        nowak.setPhone("493843193");
        nowak.setAddress("Mickiewicza 6B/5, 60-243 Poznań");
        nowak.setEmail("nowak@abc.de");
        nowak.setCardExpiryDate(date);

        Reader gosia = new Reader();
        gosia.setLastName("Stępień");
        gosia.setFirstName("Małgorzata");
        gosia.setPhone("382917435");
        gosia.setAddress("Zakopiańska 83, 62-653 Kiekrz");
        gosia.setEmail("stepien@abc.de");
        gosia.setCardExpiryDate(date);

        Reader ania = new Reader();
        ania.setLastName("Nowakowska");
        ania.setFirstName("Anna");
        ania.setPhone("174839236");
        ania.setAddress("Wioślarska 1, 60-342 Poznań");
        ania.setEmail("nowakowska@abc.de");
        ania.setCardExpiryDate(date);

        Reader late = new Reader();
        late.setLastName("Spóźniony");
        late.setFirstName("Jan");
        late.setAddress("Polna 43, 60-331 Poznań");
        late.setEmail("late@abc.se");
        late.setCardExpiryDate(calendar.getTime());

        calendar.add(Calendar.YEAR, -2);

        Reader expired = new Reader();
        expired.setLastName("Nieważny");
        expired.setFirstName("Jan");
        expired.setAddress("Polna 33, 60-331 Poznań");
        expired.setEmail("expired@abc.se");
        expired.setCardExpiryDate(calendar.getTime());

        readerService.save(ania);
        readerService.save(gosia);
        readerService.save(nowak);
        readerService.save(expired);
        readerService.save(late);

        bookService.save(tok);
        bookService.save(lotr);
        bookService.save(lotr2);
        bookService.save(king);
        bookService.save(og);
        bookService.save(potop);

        BookStatus tokSt = new BookStatus();
        tokSt.setBook(tok);
        BookStatus lotrSt = new BookStatus();
        lotrSt.setBook(lotr);
        BookStatus lotr2St = new BookStatus();
        lotr2St.setBook(lotr2);
        BookStatus kingSt = new BookStatus();
        kingSt.setBook(king);
        BookStatus ogSt = new BookStatus();
        ogSt.setBook(og);
        BookStatus potopSt = new BookStatus();
        potopSt.setBook(potop);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = format.parse("2018-12-31");
        Date date2 = format.parse("2019-01-13");

        potopSt.setRented(true);
        potopSt.setReader(late);
        potopSt.setRentedOn(date1);
        potopSt.setRentedUntil(date2);

        try {
            bookStatusService.save(tokSt);
            bookStatusService.save(lotrSt);
            bookStatusService.save(lotr2St);
            bookStatusService.save(kingSt);
            bookStatusService.save(ogSt);
            bookStatusService.uncheckedSave(potopSt);
        } catch (BookLimitExceededException | CardExpiredException | ReaderHasFineException e) {
            logger.error(e.toString());
        }

    }
}
