package com.satc.projeto.mudancaclimatica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MudancaclimaticaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MudancaclimaticaApplication.class, args);
	}

}
