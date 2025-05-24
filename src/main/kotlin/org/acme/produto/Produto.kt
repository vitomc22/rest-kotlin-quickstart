package org.acme.produto

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
class Produto(

    @field:NotBlank(message = "O nome do produto é obrigatório!")
    @field:Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres!")
    var nome: String = "",


    @field:DecimalMin(value = "0.1", message = "O preço deve ser maior que 1 centavo !")
    var preco: Double = 0.0
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}