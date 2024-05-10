package br.com.mcm.apimcmfood;

import br.com.mcm.apimcmfood.infrastructure.repository.custom.impl.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class ApiMcmFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiMcmFoodApplication.class, args);
	}

}
