package org.acme.produto

import io.quarkus.hibernate.reactive.panache.PanacheEntity
import jakarta.persistence.Entity
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
class Produto : PanacheEntity() {

    @field:NotBlank(message = "O nome do produto é obrigatório!")
    @field:Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres!")
    var nome: String = ""

    @field:DecimalMin(value = "0.1", message = "O preço deve ser maior que 1 centavo !")
    var preco: Double = 0.0
}