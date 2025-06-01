package org.acme.produto

import ProdutoRepository
import io.quarkus.hibernate.reactive.panache.Panache
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.NotFoundException


@ApplicationScoped
class ProdutoService() {
    @Inject
    lateinit var produtoRepository: ProdutoRepository


    fun listar(): Uni<List<Produto>> {
        return Panache.withSession { produtoRepository.listAll() }
    }

    fun buscarPorId(id: Long): Uni<Produto> {
        return Panache.withSession { produtoRepository.findById(id) }
            ?: throw NotFoundException("Produto nao encontrado")
    }

    fun adicionar(produto: Produto): Uni<Produto> {
        return Panache.withTransaction { produto.persist() }
            ?: throw IllegalArgumentException("Erro ao salvar produto: ${produto.nome}")
    }

    fun atualizar(produto: Produto): Uni<Produto> {
        return produtoRepository.findById(produto.id).onItem().ifNull()
            .failWith(NotFoundException("Produto nao encontrado")).flatMap { oldProduto ->
                oldProduto.nome = produto.nome
                oldProduto.preco = produto.preco
                Panache.withTransaction { produtoRepository.persist(oldProduto) }
            }
    }

    fun excluir(produto: Produto): Uni<Void> {
        return Panache.withTransaction {produtoRepository.deleteById(produto.id)}
            .flatMap { deleted ->
                if (deleted) Uni.createFrom().voidItem()
                else Uni.createFrom().failure(NotFoundException("Produto não encontrado"))
            }
    }
}