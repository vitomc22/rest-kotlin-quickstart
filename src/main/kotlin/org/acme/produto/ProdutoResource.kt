package org.acme.produto

import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Response
import org.jboss.resteasy.reactive.RestResponse


@Path("/produtos")
@Produces("application/json")
@Consumes("application/json")
class ProdutoResource() {

    @Inject
    lateinit var produtoService: ProdutoService

    @Inject
    lateinit var producer: ProdutoEventProducer

    @GET
    @Path("/{id}")
    fun buscarPorId(@PathParam("id") id: Long): Uni<RestResponse<Produto>> {
        return produtoService.buscarPorId(id).map { produto ->
            produto?.let {
                RestResponse.ok(it)
            } ?: RestResponse.status(RestResponse.Status.NOT_FOUND)
        }
    }

    @GET
    fun listar(): Uni<RestResponse<List<Produto>>> {
        return produtoService.listar().map { produtos ->
            produtos?.let {
                RestResponse.ok(it)
            } ?: RestResponse.status(RestResponse.Status.NOT_FOUND)
        }
    }

    @POST
    fun adicionar(@Valid produto: Produto): Uni<RestResponse<Produto>> {
        return produtoService.adicionar(produto).map { produto ->
            produto?.let {
                RestResponse.status(RestResponse.Status.CREATED, it)
            } ?: RestResponse.status(RestResponse.Status.BAD_REQUEST)
        }

    }

    @PUT
    fun atualizar(@Valid produto: Produto): Uni<RestResponse<Produto>> {
        return produtoService.atualizar(produto).map { produto ->
            produto.let {
                RestResponse.status(RestResponse.Status.OK, it)
            } ?: RestResponse.status(RestResponse.Status.BAD_REQUEST)
        }
    }

    @DELETE
    fun remover(@Valid produto: Produto):Uni<RestResponse<Produto>> {
        return produtoService.excluir(produto).map {produto ->
            produto.let {
                RestResponse.status(RestResponse.Status.NO_CONTENT)
            }?: RestResponse.status(RestResponse.Status.BAD_REQUEST)
        }
    }
}