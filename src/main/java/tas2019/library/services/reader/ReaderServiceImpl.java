package tas2019.library.services.reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tas2019.library.dtos.ReaderDTO;
import tas2019.library.entities.Reader;
import tas2019.library.repositories.BookStatusRepository;
import tas2019.library.repositories.ReaderRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReaderServiceImpl implements ReaderService {
    @Autowired
    private ReaderRepository repository;
    @Autowired
    private BookStatusRepository bookStatusRepository;

    @Override
    public Optional<Reader> getById(int id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<ReaderDTO> getAll() {
        return (Iterable<ReaderDTO>) ((ArrayList)repository.findAll())
                .stream()
                .map(reader -> getDTO((Reader) reader))
                .collect(Collectors.toList());
    }

    @Override
    public Reader save(Reader reader) {
        return repository.save(reader);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public ReaderDTO getDTO(Reader reader) {
        return new ReaderDTO(reader, bookStatusRepository.countByReaderId(reader.getId()));
    }

    @Override
    public boolean readerCardIsValid(int readerId) {
        int i = 0;
        Reader reader = repository.findById(readerId).get();
        if (Objects.nonNull(reader.getCardExpiryDate()))
            i = reader.getCardExpiryDate().compareTo(new Date());
        return i > 0;
    }

    @Override
    public void applyFine(int diff, int readerId) {
        Optional<Reader> reader = repository.findById(readerId);
        if (reader.isPresent()) {
            reader.get().setFine(reader.get().getFine() + diff);
        }
    }

    @Override
    public boolean readerHasFine(int id) {
        Optional<Reader> readerOptional = repository.findById(id);
        if (readerOptional.isPresent()) {
            return readerOptional.get().getFine() > 0;
        }
        return false;
    }
}
