package com.satc.projeto.mudancaclimatica.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class RequestWeatherInfoDto implements Serializable {

    private String latitude;
    private String longitude;
    private LocalDate startDate;
    private LocalDate endDate;

}