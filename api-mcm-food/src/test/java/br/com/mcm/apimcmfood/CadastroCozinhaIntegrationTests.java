package br.com.mcm.apimcmfood;

import br.com.mcm.apimcmfood.application.service.CozinhaService;
import br.com.mcm.apimcmfood.domain.entity.Cozinha;
import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIntegrationTests {
	@Autowired
	private CozinhaService cozinhaService;

	@LocalServerPort
	private int port;

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas(){
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		given()
			.basePath("/cozinhas")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	@Test
	public void deveCadastroCozinha_QuandoPassarCozinhaValida() {
		// cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Brazileira");

		// ação
		novaCozinha = cozinhaService.adicionar(novaCozinha);

		// validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
	@Test
	void deveFalhar_QuandoCadastroCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado =
				Assertions.assertThrows(ConstraintViolationException.class, () -> {
					cozinhaService.adicionar(novaCozinha);
				});
		assertThat(erroEsperado).isNotNull();
	}

	@Test
	void deveFalhar_QuandoExcluirCozinhaEmUso(){
		EntidadeEmUsoException erroEsperado =
				Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
					cozinhaService.remover(1L);
				});

		assertThat(erroEsperado).isNotNull();
	}

	@Test
	void deveFalhar_QuandoExcluirCozinhaInexistente(){
		EntidadeNaoEncontradaException erroEsperado =
				Assertions.assertThrows(EntidadeNaoEncontradaException.class, () ->{
					cozinhaService.remover(100L);
				});

		assertThat(erroEsperado).isNotNull();
	}
}
