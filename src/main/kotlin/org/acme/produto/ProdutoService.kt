package org.acme.produto

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class ProdutoService {
    @Inject
    lateinit var produtoRepository: ProdutoRepository

    fun listar(): List<Produto> = produtoRepository.listAll()

    @Transactional
    fun adicionar(produto: Produto): Produto {
        produtoRepository.persist(produto)
        return produto
    }

    @Transactional
    fun atualizar(produto: Produto): Produto {
        val newProduto =
            produtoRepository.findById(produto.id!!) ?: throw NotFoundException("Produto ${produto.nome} não econtrado")
        newProduto.nome = produto.nome
        newProduto.preco = produto.preco
        produtoRepository.persistAndFlush(newProduto)
        return newProduto
    }

    @Transactional
    fun exluir(produto: Produto): Produto {
        produtoRepository.findById(produto.id!!) ?: throw NotFoundException("Produto ${produto.nome} não econtrado")
        produtoRepository.deleteById(produto.id!!)
        return produto
    }

}