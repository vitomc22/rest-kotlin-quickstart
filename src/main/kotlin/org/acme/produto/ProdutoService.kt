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

    fun listar(): List<Produto> = produtoRepository.listAll()

    @Transactional
    fun adicionar(produto: Produto): Response {
        produtoRepository.persist(produto)
        return Response.ok(produto).build()
    }

    @Transactional
    fun atualizar(produto: Produto): Response {
        val newProduto = produtoRepository.findById(produto.id!!)
        ?: throw NotFoundException("Produto ${produto.nome} não econtrado")
        newProduto.nome = produto.nome
        newProduto.preco = produto.preco
        produtoRepository.persistAndFlush(newProduto)
        return Response.ok("Produto: ${produto.nome} atualizado com sucesso").build()
    }

    @Transactional
    fun exluir(produto: Produto): Response {
        produtoRepository.findById(produto.id!!) ?: throw NotFoundException("Produto ${produto.nome} não econtrado")
        produtoRepository.deleteById(produto.id!!)
        return Response.noContent().build()
    }

}