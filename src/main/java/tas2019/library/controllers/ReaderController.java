package tas2019.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tas2019.library.entities.Reader;
import tas2019.library.services.reader.ReaderService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${serverAddress}")
public class ReaderController {

    @Autowired
    private ReaderService service;

    @GetMapping(
            path = "/reader/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Reader> getById(@PathVariable("id") int id) {
        Optional<Reader> reader= service.getById(id);
        return reader.isPresent() ?
                ResponseEntity.ok(reader.get()) :
                ResponseEntity.notFound().build();
    }

    @GetMapping(
            path = "/reader",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Iterable<Reader> getAll() {
        return service.getAll();
    }

    @PostMapping("/reader")
    public ResponseEntity<Reader> create(@RequestBody @Valid @NotNull Reader reader) {
        service.save(reader);
        return ResponseEntity.status(HttpStatus.CREATED).body(reader);
    }

    @PutMapping("/reader")
    public ResponseEntity<Void> edit(@RequestBody @Valid @NotNull Reader reader) {
        Optional<Reader> reader1 = service.getById(reader.getId());
        if (reader1.isPresent()) {
            service.save(reader);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/reader/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        Optional<Reader> reader= service.getById(id);
        if (reader.isPresent()) {
            service.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
