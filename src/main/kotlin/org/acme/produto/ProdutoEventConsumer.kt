package org.acme.produto

import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.jboss.logging.Logger

@ApplicationScoped

class ProdutoEventConsumer {

    private val log = Logger.getLogger(ProdutoEventConsumer::class.java)

    @Incoming("produto-events-consumer")
    fun receive(event: String) {
        log.infov("🟢 Evento recebido no Kafka: {0}", event)
    }
}