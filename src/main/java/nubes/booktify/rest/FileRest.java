package nubes.booktify.rest;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import nubes.booktify.model.UploadFileResponse;
import nubes.booktify.service.FileStorageService;

@RestController
@RequestMapping("/api/books")
public class FileRest {

  @Autowired
  private FileStorageService fileStorageService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/uploadFile/{id}")
  public ResponseEntity<UploadFileResponse> uploadFile(@RequestParam("file") MultipartFile file,
      @PathVariable Integer id) {
    UploadFileResponse response = fileStorageService.storeFile(file, id);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/downloadFile/{fileName}")
  public ResponseEntity<UrlResource> downloadFile(@PathVariable String fileName) throws MalformedURLException {
    UrlResource pdf = fileStorageService.loadFileAsResource(fileName);

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaTypeFactory.getMediaType(pdf).orElse(MediaType.APPLICATION_OCTET_STREAM)).body(pdf);
  }
}