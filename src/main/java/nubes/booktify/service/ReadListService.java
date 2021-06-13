package nubes.booktify.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nubes.booktify.exception.NotFoundException;
import nubes.booktify.model.ReadList;
import nubes.booktify.repository.ReadListRepository;

@Service
public class ReadListService {
  @Autowired
  private ReadListRepository readlistRepository;

  public ReadList getReadlistById(Integer id) {
    Optional<ReadList> readlist = readlistRepository.findById(id);

    if (!readlist.isPresent()) {
      throw new NotFoundException("La lista solicitada no existe");
    }

    return readlist.get();
  }

}
