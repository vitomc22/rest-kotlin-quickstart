package org.acme.produto

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import jakarta.ws.rs.core.Response

@ApplicationScoped
class ProdutoService {
    @Inject
    lateinit var produtoRepository: ProdutoRepository

    @Inject
    lateinit var producer: ProdutoEventProducer

    fun listar(): List<Produto> = produtoRepository.listAll()

    fun buscar(id: Long): Produto {
        producer.sendEvent("Produto ${id} buscado com sucesso")
        return produtoRepository.findById(id) ?: throw NotFoundException("Produto nao encontrado")
    }

    @Transactional
    fun adicionar(produto: Produto): Response {
        produtoRepository.persist(produto)
        producer.sendEvent("Produto ${produto.nome} adicionado com sucesso")
        return Response.status(Response.Status.CREATED).entity(produto).build()
    }

    @Transactional
    fun atualizar(produto: Produto): Response {
        val newProduto =
            produtoRepository.findById(produto.id!!) ?: throw NotFoundException("Produto ${produto.nome} não econtrado")
        newProduto.nome = produto.nome
        newProduto.preco = produto.preco
        produtoRepository.persistAndFlush(newProduto)
        producer.sendEvent("Produto ${produto.nome} atualizado com sucesso")
        return Response.ok("Produto: ${produto.nome} atualizado com sucesso").build()
    }

    @Transactional
    fun exluir(produto: Produto): Response {
        produtoRepository.findById(produto.id!!) ?: throw NotFoundException("Produto ${produto.nome} não econtrado")
        produtoRepository.deleteById(produto.id!!)
        producer.sendEvent("Produto ${produto.nome} excluido com sucesso")
        return Response.noContent().build()
    }

}