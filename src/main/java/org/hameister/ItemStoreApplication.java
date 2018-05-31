package org.hameister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

public class ItemStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemStoreApplication.class, args);
	}
}
