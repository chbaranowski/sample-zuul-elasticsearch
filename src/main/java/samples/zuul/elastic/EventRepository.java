package samples.zuul.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EventRepository extends ElasticsearchRepository<Event, String>{

}
