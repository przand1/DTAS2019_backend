package tas2019.library.services.reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tas2019.library.entities.Reader;
import tas2019.library.repositories.ReaderRepository;

import java.util.Optional;

@Service
public class ReaderServiceImpl implements ReaderService {
    @Autowired
    private ReaderRepository repository;

    @Override
    public Optional<Reader> getById(int id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Reader> getAll() {
        return repository.findAll();
    }

    @Override
    public Reader save(Reader reader) {
        return repository.save(reader);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
