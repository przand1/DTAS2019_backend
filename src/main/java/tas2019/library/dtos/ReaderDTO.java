package tas2019.library.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import tas2019.library.entities.Reader;

import java.util.Date;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReaderDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String email;
    private int rentedBooksCount;
    private Date cardExpiryDate;
    private int fine;

    public ReaderDTO(Reader reader, int bookCount) {
        id = reader.getId();
        firstName = reader.getFirstName();
        lastName = reader.getLastName();
        address = reader.getAddress();
        phone = reader.getPhone();
        email = reader.getEmail();
        rentedBooksCount = bookCount;
        cardExpiryDate = reader.getCardExpiryDate();
        fine = reader.getFine();
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getRentedBooksCount() {
        return rentedBooksCount;
    }

    public Date getCardExpiryDate() {
        return cardExpiryDate;
    }

    public int getFine() {
        return fine;
    }
}
