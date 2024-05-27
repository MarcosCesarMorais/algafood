package br.com.mcm.apimcmfood.testeDeIntegracao;

import br.com.mcm.apimcmfood.DatabaseCleaner;
import br.com.mcm.apimcmfood.IntegrationTest;
import br.com.mcm.apimcmfood.AdicionaCorpoJson;
import br.com.mcm.apimcmfood.utils.CarregaJson;
import br.com.mcm.apimcmfood.domain.entity.Cozinha;
import br.com.mcm.apimcmfood.infrastructure.repository.CozinhaRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@IntegrationTest
class CozinhaIT {
    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @LocalServerPort
    private int port;
    private int COZINHA_ID_INEXISTENTE = 100;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables();
    }

    @Test
    public void dadoBuscarTodos_QuandoListarCozinha_EntaoDeveRetornarQtdDeCozinhasComStatus200() {

        var quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();

        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", Matchers.hasSize(quantidadeCozinhasCadastradas));
    }

    @Test
    public void dadoUmIdValido_QuandoBuscarCozinha_EntaoDeveRetornarUmaCozinhaValidaComStatus200() {

        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        given()
                .pathParam("cozinhaId", cozinhaBrasileira.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo(cozinhaBrasileira.getNome()));
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
                .body(AdicionaCorpoJson.getContentFromResource(
                                "/json/cozinha/cozinhaComNomeValido.json"))
                //.body(" { \"nome\" : \"Chinesa\" } ")
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
                .body(AdicionaCorpoJson.getContentFromResource(
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
                .body(AdicionaCorpoJson.getContentFromResource(
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
    void dadoUmIdExistenteSemRestaurante_QuandoRemoverCozinha_EntaoDeveRetornarErroComStatus404() {
        given()
                .pathParam("cozinhaId", 1)
                .accept(ContentType.JSON)
                .when()
                .delete("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void dadoUmIdExistente_QuandoRemoverCozinha_EntaoDeveRetornarErroComStatus409() {
        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        var cozinhaAtual = cozinhaRepository.save(cozinhaBrasileira);

        given()
                .pathParam("cozinhaId", cozinhaAtual.getId())
                .accept(ContentType.JSON)
                .when()
                .delete("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
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

        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        given()
                .pathParam("cozinhaId", cozinhaBrasileira.getId())
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

        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        given()
                .pathParam("cozinhaId", cozinhaBrasileira.getId())
                .body(AdicionaCorpoJson.getContentFromResource(
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

        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        given()
                .pathParam("cozinhaId", cozinhaBrasileira.getId())
                .body(AdicionaCorpoJson.getContentFromResource(
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
