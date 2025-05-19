package org.acme

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test

@QuarkusTest
class ProdutoResourceTest {

    @Test
    fun`deve retornar uma lista de produtos`() {
        given()
            .`when`()
            .get("/produtos")
            .then()
            .statusCode(200)
            .body(containsString("Camiseta"))
    }

    @Test
    fun`deve criar um novo produto`() {
        val json = """
                {
                "nome": "Livro",
                "preco": 39.90
                }
        """.trimIndent()

        given()
            .contentType("application/json")
            .body(json)
            .`when`()
            .post("/produtos")
            .then()
            .statusCode(200)
            .body(containsString("Livro"))
    }

    @Test
    fun`deve validar os campos de nome`() {
        val json = """
                {
                "nome": "",
                "preco": 39.90
                }
        """.trimIndent()

        given()
            .contentType("application/json")
            .body(json)
            .`when`()
            .post("/produtos")
            .then()
            .statusCode(400)
            .body(containsString("O nome do produto é obrigatório!"))
    }

    @Test
    fun`deve validar os campos de preço`() {
        val json = """
                {
                "nome": "",
                "preco": -5
                }
        """.trimIndent()

        given()
            .contentType("application/json")
            .body(json)
            .`when`()
            .post("/produtos")
            .then()
            .statusCode(400)
            .body(containsString("O preço deve ser maior que 1 centavo !"))
    }
}