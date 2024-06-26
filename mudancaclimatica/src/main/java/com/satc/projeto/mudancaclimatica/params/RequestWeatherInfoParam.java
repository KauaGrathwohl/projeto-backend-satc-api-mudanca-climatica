package com.satc.projeto.mudancaclimatica.params;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class RequestWeatherInfoParam implements Serializable {

    private String latitude;
    private String longitude;
    private LocalDate startDate;
    private LocalDate endDate;

}