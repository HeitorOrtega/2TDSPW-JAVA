package br.com.fiap.api_rest.DTO;

import org.springframework.hateoas.Links;

public record LivroResponseDTO(Long id, String infoLivro, Links link) {
}
