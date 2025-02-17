package br.com.fiap.api_rest.service;

import br.com.fiap.api_rest.DTO.LivroRequest;
import br.com.fiap.api_rest.DTO.LivroRequestDTO;
import br.com.fiap.api_rest.DTO.LivroResponse;
import br.com.fiap.api_rest.model.Livro;
import org.springframework.stereotype.Service;

@Service
public class LivroService {
    public Livro requestToLivro(LivroRequest livroRequest){
        Livro livro = new Livro();
        livro.setAutor(livroRequest.getAutor());
        livro.setTitulo(livroRequest.getTitulo());
        return livro;
    }

    public Livro recordLivro(LivroRequestDTO livroRecord){
        Livro livro = new Livro();
        livro.setTitulo(livroRecord.titulo());
        livro.setAutor(livroRecord.autor());
        return livro;
    }

    public static LivroResponse livroToResponse(Livro livro){
        return new LivroResponse (livro.getAutor() + " - " + livro.getTitulo());

    }
}
