package org.acme.produto

import jakarta.inject.Inject
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Response


@Path("/produtos")
@Produces("application/json")
@Consumes("application/json")
class ProdutoResource(private val producer: ProdutoEventProducer) {

    @Inject
    lateinit var produtoService: ProdutoService

    @GET
    @Path("/{id}")
    fun buscarPorId(@PathParam("id") id: Long): Produto {
        return produtoService.buscar(id)
    }


    @GET
    fun listar(): List<Produto> = produtoService.listar()

    @POST
    fun adicionar(@Valid produto: Produto): Response {
        return produtoService.adicionar(produto)
    }

    @PUT
    fun atualizar(@Valid produto: Produto): Response {
        return produtoService.atualizar(produto)

    }

    @DELETE
    fun remover(@Valid produto: Produto): Response {
        return produtoService.exluir(produto)

    }

    @POST
    @Produces("text/plain")
    fun enviarMsg(@QueryParam("msg") msg: String): String {
        producer.sendEvent(msg)
        return "Mensagem: $msg enviada com sucesso"
    }
}