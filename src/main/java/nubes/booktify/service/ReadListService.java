package nubes.booktify.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nubes.booktify.exception.NotFoundException;
import nubes.booktify.model.Book;
import nubes.booktify.model.ReadList;
import nubes.booktify.model.ReadListBooks;
import nubes.booktify.model.User;
import nubes.booktify.model.request.ReadListRequest;
import nubes.booktify.model.request.ReadlistBookRequest;
import nubes.booktify.repository.BookRepository;
import nubes.booktify.repository.ReadListRepository;
import nubes.booktify.repository.ReadListsBooksRepository;
import nubes.booktify.repository.UserRepository;

@Service
public class ReadListService {
  @Autowired
  private ReadListRepository readlistRepository;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ReadListsBooksRepository readlistsBooks;

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

    ReadList readlist = getReadlistById(id);

    ReadList editedReadlist = readlist.updateReadList(readlistRequest);
    readlistRepository.save(editedReadlist);

    return editedReadlist;
  }

  public void deleteReadList(Integer id) {
    ReadList readlist = getReadlistById(id);
    readlistRepository.delete(readlist);
  }

  public ReadListBooks addSong(ReadlistBookRequest request) {
    validateSong(request.getBookId());
    getReadlistById(request.getReadListId());

    ReadListBooks readlistBooks = new ReadListBooks(request.getReadListId(), request.getBookId());

    readlistsBooks.save(readlistBooks);

    return readlistBooks;
  }

  public void removeSong(Integer id) {
    readlistsBooks.deleteById(id);
  }

  private void validateUser(Integer userId) {
    Optional<User> user = userRepository.findById(userId);

    if (!user.isPresent()) {
      throw new NotFoundException("El id del usuario ingresado no existe");
    }
  }

  private void validateSong(Integer id) {
    Optional<Book> book = bookRepository.findById(id);

    if (!book.isPresent()) {
      throw new NotFoundException("El id del libro ingresado no existe");
    }
  }

}
