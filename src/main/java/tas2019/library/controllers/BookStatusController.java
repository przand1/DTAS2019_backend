package tas2019.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tas2019.library.entities.BookStatus;
import tas2019.library.services.bookstatus.BookStatusService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${serverAddress}")
public class BookStatusController {

    @Autowired
    private BookStatusService service;

    @GetMapping(
            path = "/bookstatus/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BookStatus> getById(@PathVariable("id") int id) {
        Optional<BookStatus> status = service.getById(id);
        return status.isPresent() ?
                ResponseEntity.ok(status.get()) :
                ResponseEntity.notFound().build();
    }

    @GetMapping(
            path = "/bookstatus",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Iterable<BookStatus> getAll() {
        return service.getAll();
    }

    @PostMapping("/bookstatus")
    public ResponseEntity<BookStatus> create(@RequestBody @Valid @NotNull BookStatus status) {
        service.save(status);
        /*
            Uwaga! Nie pomylić obiektu status z metodą status() z klasy ResponseEntity
         */
        return ResponseEntity.status(HttpStatus.CREATED).body(status);
    }

    @PutMapping("/bookstatus")
    public ResponseEntity<Void> edit(@RequestBody @Valid @NotNull BookStatus status) {
        Optional<BookStatus> status1 = service.getById(status.getId());
        if (status1.isPresent()) {
            service.save(status);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/bookstatus/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        Optional<BookStatus> status = service.getById(id);
        if (status.isPresent()) {
            service.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}