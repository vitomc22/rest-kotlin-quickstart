package org.acme.produto

import jakarta.enterprise.context.ApplicationScoped
import jakarta.validation.ConstraintViolationException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
@ApplicationScoped
class ValidationExceptionMapper : ExceptionMapper<ConstraintViolationException> {
    override fun toResponse(exception: ConstraintViolationException): Response {
        val errors = exception.constraintViolations.map {
            mapOf(
                "campo" to it.propertyPath.toString(),
                "mensagem" to it.message
            )
        }
        val corpo = mapOf(
            "mensagem" to "Campos inválidos",
            "erros" to errors
        )
        return Response.status(Response.Status.BAD_REQUEST).entity(corpo).build()
    }
}