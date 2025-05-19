package org.acme.produto

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional

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
}