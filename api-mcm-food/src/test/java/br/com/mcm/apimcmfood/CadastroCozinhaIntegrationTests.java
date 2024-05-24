package br.com.mcm.apimcmfood;

import br.com.mcm.apimcmfood.application.service.CozinhaService;
import br.com.mcm.apimcmfood.domain.entity.Cozinha;
import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.utils.CarregaJson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.validation.ConstraintViolationException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test-integration.properties")
class CadastroCozinhaIntegrationTests {
    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private Flyway flyway;

    @LocalServerPort
    private int port;

    private  int COZINHA_ID_INEXISTENTE = 100;
    private  int COZINHA_ID_EXISTENTE = 2;
    private String arquivoJsonCozinhaChinesa;


    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";
        arquivoJsonCozinhaChinesa = CarregaJson.getContentFromResource(
                "/json/cozinha.json");

        flyway.migrate();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarUmaCozinhaValida_QuandoConsultarCozinhaPassandoUmIdValido() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_EXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo("Indiana"));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaPassandoUmIdInexistente() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deveConter4Cozinhas_QuandoConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", Matchers.hasSize(5))
                .body("nome", Matchers.hasItems("Tailandesa", "Indiana"));
    }
    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha() {
        given()
                .body(arquivoJsonCozinhaChinesa)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
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
    void deveFalhar_QuandoExcluirCozinhaEmUso() {
        EntidadeEmUsoException erroEsperado =
                Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
                    cozinhaService.remover(1L);
                });

        assertThat(erroEsperado).isNotNull();
    }

    @Test
    void deveFalhar_QuandoExcluirCozinhaInexistente() {
        EntidadeNaoEncontradaException erroEsperado =
                Assertions.assertThrows(EntidadeNaoEncontradaException.class, () -> {
                    cozinhaService.remover(100L);
                });

        assertThat(erroEsperado).isNotNull();
    }
}
