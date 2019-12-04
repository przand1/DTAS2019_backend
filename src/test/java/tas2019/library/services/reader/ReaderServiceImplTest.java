package tas2019.library.services.reader;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import tas2019.library.entities.Reader;
import tas2019.library.repositories.ReaderRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
class ReaderServiceImplTest {

    @TestConfiguration
    static class ReaderServiceImplTestContextConfiguration {
        @Bean
        public ReaderService readerService() {
            return new ReaderServiceImpl();
        }
    }
    @Autowired
    private ReaderService readerService;
    @MockBean
    private ReaderRepository readerRepository;

    @Test
    void readerCardIsValid() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse("2009-12-31");
        Date date2 = sdf.parse("2110-01-31");
        Reader reader1 = new Reader();
        Reader reader2 = new Reader();
        reader1.setCardExpiryDate(date1);
        reader2.setCardExpiryDate(date2);

        assertFalse(readerService.readerCardIsValid(reader1.getId()));
        assertTrue(readerService.readerCardIsValid(reader2.getId()));
    }
}