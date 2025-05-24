package org.acme.produto

import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter

@ApplicationScoped
class ProdutoEventProducer(@Channel("produto-events-producer") private val emitter: Emitter<String>) {
    fun sendEvent(event: String) {
        emitter.send(event)
    }
}