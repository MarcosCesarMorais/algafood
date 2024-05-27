package br.com.mcm.apimcmfood.cozinha;

import br.com.mcm.apimcmfood.IntegrationTest;
import br.com.mcm.apimcmfood.application.service.CozinhaService;
import br.com.mcm.apimcmfood.utils.CarregaJson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@IntegrationTest
class CozinhaIT {
    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private Flyway flyway;

    @LocalServerPort
    private int port;

    private int COZINHA_ID_INEXISTENTE = 100;
    private int COZINHA_ID_EXISTENTE = 2;
    private int COZINHA_SEM_RESTAURANTE = 5;


    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        flyway.migrate();
    }

    @Test
    public void dadoBuscarTodos_QuandoListarCozinha_EntaoDeveRetornar5CozinhaComStatus200() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", Matchers.hasSize(5))
                .body("nome", hasItems("Tailandesa", "Indiana"));
    }

    @Test
    public void dadoUmIdValido_QuandoBuscarCozinha_EntaoDeveRetornarUmaCozinhaValidaComStatus200() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_EXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(2),
                        "nome", equalTo("Indiana"));
    }

    @Test
    public void dadoUmIdInexistente_QuandoBuscarCozinha_EntaoDeveRetornarErroComStatus404() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("detail", equalTo("Não existe um cadastro de cozinha com código 100"));
    }

    @Test
    public void dadoUmNomeValido_QuandoAdicionarCozinha_EntaoDeveRetornarCozinhaCadastradaComStatus201() {
        given()
                .body(CarregaJson.getContentFromResource(
                        "/json/cozinha/cozinhaComNomeValido.json"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("nome", equalTo("Chinesa"));
    }


    @Test
    void dadoUmNomeVazio_QuandoAdicionarCozinha_EntaoDeveRetornarErroComStatus401() {
        given()
                .body(CarregaJson.getContentFromResource(
                        "/json/cozinha/cozinhaComNomeVazio.json"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("detail", equalTo("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."),
                        "fields.message", hasItems("Nome da cozinha é obrigatório"));
    }

    @Test
    void dadoUmNomeNulo_QuandoAdicionarCozinha_EntaoDeveRetornarErroComStatus401() {
        given()
                .body(CarregaJson.getContentFromResource(
                        "/json/cozinha/cozinhaComNomeNulo.json"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("detail", equalTo("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."),
                        "fields.message", hasItems("Nome da cozinha é obrigatório"));
    }

    @Test
    void dadoUmIdExistenteSemRestaurante_QuandoRemoverCozinha_EntaoDeveRetornarErroComStatus204() {
        given()
                .pathParam("cozinhaId", COZINHA_SEM_RESTAURANTE)
                .accept(ContentType.JSON)
                .when()
                .delete("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void dadoUmIdExistente_QuandoRemoverCozinha_EntaoDeveRetornarErroComStatus409() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_EXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .delete("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .body("detail", equalTo("Não é possível remover a cozinha com o código 2, pois está associada a um ou mais restaurantes."));
    }

    @Test
    void dadoUmIdInexistente_QuandoRemoverCozinha_EntaoDeveRetornarErroComStatus404() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .delete("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void dadoUmIdExistente_QuandoAtualizarCozinha_EntaoDeveRetornarUmaCozinhaComStatus200() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_EXISTENTE)
                .body(CarregaJson.getContentFromResource(
                        "/json/cozinha/cozinhaComNomeValido.json"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo("Chinesa"));
    }

    @Test
    void dadoUmIdExistenteENomeVazio_QuandoAtualizarCozinha_EntaoDeveRetornarUmComStatus400() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_EXISTENTE)
                .body(CarregaJson.getContentFromResource(
                        "/json/cozinha/cozinhaComNomeVazio.json"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo("Dados inválidos"),
                        "fields.message", hasItems("Nome da cozinha é obrigatório"));
    }

    @Test
    void dadoUmaCozinhaComNomeNulo_QuandoAtualizarCozinha_() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_EXISTENTE)
                .body(CarregaJson.getContentFromResource(
                        "/json/cozinha/cozinhaComNomeNulo.json"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .put("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("detail", equalTo("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."),
                        "fields.message", hasItems("Nome da cozinha é obrigatório"));

    }
}
