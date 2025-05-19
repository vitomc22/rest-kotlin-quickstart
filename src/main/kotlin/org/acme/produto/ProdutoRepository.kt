package org.acme.produto

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ProdutoRepository : PanacheRepository<Produto> {
}