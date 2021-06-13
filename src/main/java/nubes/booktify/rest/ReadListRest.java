package nubes.booktify.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nubes.booktify.model.ReadList;
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

}
