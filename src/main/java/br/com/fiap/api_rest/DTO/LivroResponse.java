package br.com.fiap.api_rest.DTO;

import org.springframework.hateoas.Link;

public class LivroResponse{
    private Long id;
    private String infoLivro;
    private static Link link;


    public LivroResponse(Long id , String infoLivro) {
        this.id = id;
        this.infoLivro = infoLivro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfoLivro() {
        return infoLivro;
    }

    public void setInfoLivro(String infoLivro) {
        this.infoLivro = infoLivro;
    }

    public Link getLink() {
        return link;
    }

    public static void setLink(Link link) {
        LivroResponse.link = link;
    }

}

