package nubes.booktify.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nubes.booktify.exception.NotFoundException;
import nubes.booktify.model.ReadList;
import nubes.booktify.model.User;
import nubes.booktify.model.request.ReadListRequest;
import nubes.booktify.repository.ReadListRepository;
import nubes.booktify.repository.UserRepository;

@Service
public class ReadListService {
  @Autowired
  private ReadListRepository readlistRepository;

  @Autowired
  private UserRepository userRepository;

  public ReadList getReadlistById(Integer id) {
    Optional<ReadList> readlist = readlistRepository.findById(id);

    if (!readlist.isPresent()) {
      throw new NotFoundException("La lista solicitada no existe");
    }

    return readlist.get();
  }

  public ReadList createReadList(ReadListRequest readlistRequest) {
    validateUser(readlistRequest.getUserId());

    ReadList readlist = new ReadList();
    readlistRepository.save(readlist.setReadList(readlistRequest));

    return readlist;
  }

  public ReadList updateReadlist(Integer id, ReadListRequest readlistRequest) {

    Optional<ReadList> readlist = readlistRepository.findById(id);
    if (!readlist.isPresent()) {
      throw new NotFoundException("El ReadList solicitado no existe");
    }

    ReadList editedReadlist = readlist.get().updateReadList(readlistRequest);
    readlistRepository.save(editedReadlist);

    return editedReadlist;
  }

  public void deleteReadList(Integer id) {
    ReadList readlist = getReadlistById(id);
    readlistRepository.delete(readlist);
  }

  private void validateUser(Integer userId) {
    Optional<User> user = userRepository.findById(userId);

    if (!user.isPresent()) {
      throw new NotFoundException("El id del usuario ingresado no existe");
    }
  }

}
