package nubes.booktify.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nubes.booktify.model.ReadList;
import nubes.booktify.model.ReadListBooks;
import nubes.booktify.model.request.ReadListRequest;
import nubes.booktify.model.request.ReadlistBookRequest;
import nubes.booktify.service.ReadListService;

@RestController
@RequestMapping("/api/v1")
public class ReadListRest {

  @Autowired
  private ReadListService readlistService;

  @GetMapping("/readlist/{id}")
  public ResponseEntity<ReadList> getReadList(@PathVariable Integer id) {
    ReadList readlist = readlistService.getReadlistById(id);

    return ResponseEntity.ok().body(readlist);
  }

  @PostMapping("/readlist")
  public ResponseEntity<ReadList> postReadList(@RequestBody @Valid ReadListRequest readlist) {
    ReadList readList = readlistService.createReadList(readlist);
    return ResponseEntity.ok().body(readList);
  }

  @PutMapping("/readlist/{id}")
  public ResponseEntity<ReadList> putReadList(@PathVariable Integer id,
      @RequestBody @Valid ReadListRequest readListRequest) {
    ReadList readList = readlistService.updateReadlist(id, readListRequest);
    return ResponseEntity.ok().body(readList);
  }

  @DeleteMapping("/readlist/{id}")
  public ResponseEntity<Void> deleteReadList(@PathVariable Integer id) {
    readlistService.deleteReadList(id);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/readlist/{readlistId}/book/{bookId}")
  public ResponseEntity<ReadListBooks> addBook(@PathVariable Integer readlistId, @PathVariable Integer bookId) {
    ReadlistBookRequest request = new ReadlistBookRequest(readlistId, bookId);

    ReadListBooks readlistBook = readlistService.addSong(request);

    return ResponseEntity.ok().body(readlistBook);
  }

  @DeleteMapping("/readlist/book/{id}")
  public ResponseEntity<Void> removeBook(@PathVariable Integer id) {
    readlistService.removeSong(id);
    return ResponseEntity.ok().build();
  }
}
