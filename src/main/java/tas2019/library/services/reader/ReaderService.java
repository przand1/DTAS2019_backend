package tas2019.library.services.reader;

import tas2019.library.dtos.ReaderDTO;
import tas2019.library.entities.Reader;

import java.util.Optional;

public interface ReaderService {
    Optional<Reader> getById(int id);
    Iterable<ReaderDTO> getAll();

    Reader save(Reader reader);
    void delete(int id);

    ReaderDTO getDTO(Reader reader);

    boolean readerCardIsValid(int readerId);
}
