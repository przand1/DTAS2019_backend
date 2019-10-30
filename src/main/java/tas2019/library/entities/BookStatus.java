package tas2019.library.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookStatus {

    @Id
    @GeneratedValue
    @Column
    private int id;

    @OneToOne
    private Book book;

    @Column
    private boolean rented;

    @Column
    private Date rentedOn;

    @Column
    private Date rentedUntil;

    @ManyToOne
    private Reader reader;

    public BookStatus() {}

    public int getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public Date getRentedOn() {
        return rentedOn;
    }

    public void setRentedOn(Date rentedOn) {
        this.rentedOn = rentedOn;
    }

    public Date getRentedUntil() {
        return rentedUntil;
    }

    public void setRentedUntil(Date rentedUntil) {
        this.rentedUntil = rentedUntil;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}
