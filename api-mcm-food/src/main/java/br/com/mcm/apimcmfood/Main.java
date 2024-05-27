package br.com.mcm.apimcmfood;

import br.com.mcm.apimcmfood.configuration.WebServerConfig;
import br.com.mcm.apimcmfood.infrastructure.repository.custom.impl.CustomJpaRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	public static void main(String[] args) {
		LOG.info("[step:to-be-init] [id:1] Inicializando o Spring");
		System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
		SpringApplication.run(WebServerConfig.class, args);
		LOG.info("[step:inittialized] [id:2] Spring inicializado..");
	}

}
