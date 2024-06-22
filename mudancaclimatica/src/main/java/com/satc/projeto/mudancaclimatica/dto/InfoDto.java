package com.satc.projeto.mudancaclimatica.dto;

import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Getter
public class InfoDto implements Serializable {

    private List<String> estudantes;
    private String projeto;
    private String tema;

    public InfoDto() {
        this.estudantes = Arrays.asList("Iuri de Lima Marques", "Kauã Machado", "Jorge Antônio");
        this.projeto = "Climate change detector";
        this.tema = "Mudanças Climáticas: Aplicações para visualização de dados ambientais";
    }
}