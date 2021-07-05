package nubes.booktify.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import nubes.booktify.model.document.BookIndex;

public interface BookIndexRepository extends ElasticsearchRepository<BookIndex, Integer>{
    
}
