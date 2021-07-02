package nubes.booktify.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfiguration extends AbstractElasticsearchConfiguration {
    
    @Value("${elasticsearch.port}")
    private Integer port;

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration 
        = ClientConfiguration.builder()
        .connectedTo("localhost:"+this.port)
        .build();

        return RestClients.create(clientConfiguration).rest();
    }

}
