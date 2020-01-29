package tas2019.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tas2019.library.entities.Book;
import tas2019.library.services.book.BookService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${serverAddress}")
public class BookController {
    
    @Autowired
    private BookService service;
    
    @GetMapping(
            path = "/book/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Book> getById(@PathVariable("id") int id) {
        Optional<Book> book = service.getById(id);
        return book.isPresent() ?
                ResponseEntity.ok(book.get()) :
                ResponseEntity.notFound().build();
    }
    
    @GetMapping(
            path = "/book",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Iterable<Book> getAll() {
        return service.getAll();
    }




    @GetMapping(
            path = "/book/find/{query}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Iterable<Book> Find(@PathVariable("query")String title)// wyciągniecie query do title
    {
        List<Book> result = new ArrayList<>();
        Iterable<Book> books = service.getAll();// wszystkie ksiazki do filtrowania
        Iterator<Book> iterator =books.iterator();//tworzymy iterator do wszystkich ksiazek cos jak petla bez indeksów nastepny element cały czas
        Pattern queryPattern = Pattern.compile(title); //tworzymy regexa z fraza wpisaną zeby sprawdzić dany tytuł/autor
        while(iterator.hasNext()){
            Book book = iterator.next();//wyciagam pojedyncze ksiazki
            Matcher titlematcher = queryPattern.matcher(book.getTitle());// matcher zapisuje odp regex
            Matcher autormatcher = queryPattern.matcher(book.getAuthor());
            if (titlematcher.find() || autormatcher.find()) {
                result.add(book);
            }
        }
        Iterable<Book> response = () -> result.iterator();
        return response;
    }








    @GetMapping(
            path = "book/page/{page}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Iterable<Book> getAllPaging(
            @PathVariable("page") Integer pageNr,
            @RequestParam(name = "size") Optional<Integer> perPage
    ) {
        return service.getAllPaging(pageNr, perPage.orElse(10));
    }

    @PostMapping("/book")
    public ResponseEntity<Book> create(@RequestBody @Valid @NotNull Book book) {
        service.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/book")
    public ResponseEntity<Void> edit(@RequestBody @Valid @NotNull Book book) {
        Optional<Book> book1 = service.getById(book.getId());
        if (book1.isPresent()) {
            service.save(book);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        //TODO usunąć powiązany BookStatus!
        Optional<Book> book = service.getById(id);
        if (book.isPresent()) {
            service.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
