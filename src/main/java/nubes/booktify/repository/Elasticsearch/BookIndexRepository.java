package nubes.booktify.repository.Elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import nubes.booktify.model.article.BookIndex;

public interface BookIndexRepository extends ElasticsearchRepository<BookIndex, Integer>{
    
}
