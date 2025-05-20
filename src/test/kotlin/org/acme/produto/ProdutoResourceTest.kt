package org.acme.produto

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test

@QuarkusTest
class ProdutoResourceTest {

    @Test
    fun `deve retornar uma lista de produtos`() {
        RestAssured.given().`when`().get("/produtos").then().statusCode(200).body(CoreMatchers.containsString("Livro"))
    }

    @Test
    fun `deve criar um novo produto`() {
        val json = """
                {
                "nome": "Livro",
                "preco": 39.90
                }
        """.trimIndent()

        RestAssured.given().contentType("application/json").body(json).`when`().post("/produtos").then().statusCode(200)
            .body(CoreMatchers.containsString("Livro"))
    }

    @Test
    fun `deve excuir um produto`() {
        val json = """
                {
                "nome": "Livro",
                "preco": 39.90,
                "id": 2
                }
        """.trimIndent()

        RestAssured.given().contentType("application/json").body(json).`when`().delete("/produtos").then()
            .statusCode(204)
    }

    @Test
    fun `deve atualizar um produto`() {
        val json = """
                {
                "nome": "carro",
                "preco": 39.90,
                "id": 1
                }
        """.trimIndent()

        RestAssured.given().contentType("application/json").body(json).`when`().put("/produtos").then().statusCode(200)
            .body(CoreMatchers.containsString("carro"))
    }

    @Test
    fun `deve validar os campos de nome`() {
        val json = """
                {
                "nome": "",
                "preco": 39.90
                }
        """.trimIndent()

        RestAssured.given().contentType("application/json").body(json).`when`().post("/produtos").then().statusCode(400)
            .body(CoreMatchers.containsString("O nome do produto é obrigatório!"))
    }

    @Test
    fun `deve validar os campos de preco`() {
        val json = """
                {
                "nome": "",
                "preco": -5
                }
        """.trimIndent()

        RestAssured.given().contentType("application/json").body(json).`when`().post("/produtos").then().statusCode(400)
            .body(CoreMatchers.containsString("O preço deve ser maior que 1 centavo !"))
    }
}