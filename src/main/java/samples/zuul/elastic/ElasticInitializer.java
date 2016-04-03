package samples.zuul.elastic;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

@Service
public class ElasticInitializer {

	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	EventRepository eventRepository;

	@PostConstruct
	public void init() {
		elasticsearchTemplate.deleteIndex(Event.class);
		elasticsearchTemplate.createIndex(Event.class);
		elasticsearchTemplate.putMapping(Event.class);
		
		IntStream.range(1, 100).forEach(value -> {
			Event sample = new Event();
			sample.setPath("/home/users/" + value);
			Instant instant = LocalDate.of(1916 + value, 01, 01)
					.atStartOfDay()
					.atZone(ZoneId.systemDefault())
					.toInstant();
			sample.setCreated(Date.from(instant));
			sample.setLocation(new GeoPoint(47.693829, 9.059826));
			sample.setValue(Double.valueOf(value));
			eventRepository.save(sample);
		});
	}
	
}
