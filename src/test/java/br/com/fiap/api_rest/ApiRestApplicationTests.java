package br.com.fiap.api_rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "API de Livros " , description = "Exemplo de APIRestul com swagger da 2TDSPW"))

class ApiRestApplicationTests {
	public static void main(String[] args){ SpringApplication.run(ApiRestApplication.class, args); }

}
