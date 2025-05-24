package org.acme.produto

import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.jboss.logging.Logger

@ApplicationScoped
class ProdutoEventProducer(@Channel("produto-events-producer") private val emitter: Emitter<String>) {

    private val log = Logger.getLogger(ProdutoEventProducer::class.java)

    fun sendEvent(event: String) {
        emitter.send(event)
    }
}