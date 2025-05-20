package org.acme.produto

import jakarta.inject.Inject
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Response


@Path("/produtos")
@Produces("application/json")
@Consumes("application/json")
class ProdutoResource {

    @Inject
    lateinit var produtoService: ProdutoService

    @GET
    fun listar(): List<Produto> = produtoService.listar()

    @POST
    fun adicionar(@Valid produto: Produto): Response {
        produtoService.adicionar(produto)
        return Response.ok("Produto: ${produto.nome} criado com sucesso").build()
    }

    @PUT
    fun atualizar(@Valid produto: Produto): Response {
        produtoService.atualizar(produto)
        return Response.ok("Produto: ${produto.nome} atualizado com sucesso").build()
    }

    @DELETE
    fun remover(@Valid produto: Produto): Response {
        produtoService.exluir(produto)
        return Response.noContent().build()
    }
}