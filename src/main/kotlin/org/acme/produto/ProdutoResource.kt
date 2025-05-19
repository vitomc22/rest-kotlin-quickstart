package org.acme.produto

import jakarta.inject.Inject
import jakarta.validation.Valid
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import org.acme.produto.ProdutoService

@Path("/produtos")
@Produces("application/json")
@Consumes("application/json")
class ProdutoResource {

    @Inject
    lateinit var produtoService: ProdutoService

    @GET
    fun listar(): List<Produto> = produtoService.listar()

    @POST
    fun adicionar(@Valid produto: Produto): Produto = produtoService.adicionar(produto)
}