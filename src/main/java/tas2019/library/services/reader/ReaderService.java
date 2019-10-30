package tas2019.library.services.reader;

import tas2019.library.entities.Reader;

import java.util.Optional;

public interface ReaderService {
    Optional<Reader> getById(int id);
    Iterable<Reader> getAll();

    Reader save(Reader reader);
    void delete(int id);
}
