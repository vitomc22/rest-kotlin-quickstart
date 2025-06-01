import io.quarkus.hibernate.reactive.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import org.acme.produto.Produto


@ApplicationScoped
class ProdutoRepository : PanacheRepository<Produto> {
}

