package br.com.fiap.api_rest.controller;

import br.com.fiap.api_rest.DTO.LivroRequest;
import br.com.fiap.api_rest.DTO.LivroResponse;
import br.com.fiap.api_rest.DTO.LivroResponseDTO;
import br.com.fiap.api_rest.model.Livro;
import br.com.fiap.api_rest.repository.LivroRepository;
import br.com.fiap.api_rest.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/livros", consumes = {"application/json"})
@Tag(name = "api-livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private LivroService livroService;

    // CREATE - Cria um novo livro
    @Operation(summary = "Cria um novo livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro Criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class))),
            @ApiResponse(responseCode = "400" , description = "Parâmetros informados são inválidos",
            content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<Livro> createLivro(@Valid @RequestBody LivroRequest livro) {
        Livro livroSalvo = livroRepository.save(livroService.requestToLivro(livro));
        return new ResponseEntity<>(livroSalvo, HttpStatus.CREATED);
    }

    // READ - Lista todos os livros por páginas
    @Operation(summary = "Lista todos os livros por páginas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista executada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum livro encontrado",
                    content = @Content(schema = @Schema()))

    })
    @GetMapping
    public ResponseEntity<Page<LivroResponseDTO>> readLivros(@RequestParam(defaultValue = "0") Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 2, Sort.by("autor").ascending().and(Sort.by("titulo").ascending()));

        return new ResponseEntity<>(livroService.findAllDTO(pageable), HttpStatus.OK);
    }

    // READ - Retorna um livro por id
    @Operation(summary = "Retorna um livro por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorno por id executado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class))),
            @ApiResponse(responseCode = "400" , description = "Parâmetros informados são inválidos",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> readLivro(@PathVariable Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        if (livro.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LivroResponse livroResponse = livroService.livroToResponse(livro.get());
        livroResponse.setLink(
                linkTo(methodOn(LivroController.class).readLivros(0))
                        .withRel("Lista de livros")
        );

        return new ResponseEntity<>(livroResponse, HttpStatus.OK);
    }

    // UPDATE - Atualiza um livro existente
    @Operation(summary = "Atualiza um livro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro Criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class))),
            @ApiResponse(responseCode = "400" , description = "Nenhum livro encontrado com esse ID",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Livro> updateLivro(@PathVariable Long id, @RequestBody LivroRequest livro) {
        Optional<Livro> livroExistente = livroRepository.findById(id);
        if (livroExistente.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Livro livroConvertido = livroService.requestToLivro(livro);
        livroConvertido.setId(livroExistente.get().getId());
        Livro livroSalvo = livroRepository.save(livroConvertido);

        return new ResponseEntity<>(livroSalvo, HttpStatus.CREATED);
    }

    // DELETE - Deleta um livro por id
    @Operation(summary = "Deleta um livro por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro excluido com sucesso",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400" , description = "Nenhum livro foi encontrado com esse ID",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        Optional<Livro> livroExistente = livroRepository.findById(id);
        if (livroExistente.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        livroRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
