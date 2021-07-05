package nubes.booktify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import nubes.booktify.exception.NotFoundException;
import nubes.booktify.exception.UnprocessableEntity;
import nubes.booktify.model.Book;
import nubes.booktify.model.UploadFileResponse;
import nubes.booktify.repository.BookRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class FileStorageService {

  @Autowired
  BookRepository bookRepository;

  @Transactional
  public UploadFileResponse storeFile(MultipartFile file, Integer id) {
    Book book = this.findBook(id);

    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    String path = System.getProperty("user.dir");

    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/books/downloadFile/").path(fileName)
        .toUriString();

    try {
      byte[] bytes = file.getBytes();
      Path pathPdf = Paths.get(path + "/books/" + file.getOriginalFilename());
      Files.write(pathPdf, bytes);
    } catch (IOException e) {
      throw new UnprocessableEntity("No se pudo cargar el archivo");
    }

    book.setUrlDownload(fileDownloadUri);
    bookRepository.save(book);

    return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
  }

  public UrlResource loadFileAsResource(String fileName) throws MalformedURLException {
    String ubicacion = "books/" + fileName;
    UrlResource pdf = new UrlResource("file:" + ubicacion);
    return pdf;
  }

  private Book findBook(Integer id){
    Optional<Book> book = bookRepository.findById(id);

    if(!book.isPresent()){
      throw new NotFoundException("No se encontro el libro");
    }

    return book.get();
  }
}