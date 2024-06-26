package com.satc.projeto.mudancaclimatica.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class WeatherDataDto implements Serializable {

    private String longitude;
    private String latitude;
    private Double carbonMonoxideMean;
    private Double ozone;
    private Double uvIndex;

    public WeatherDataDto(String longitude, String latitude, Double carbonMonoxideMean, Double ozone, Double uvIndex) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.carbonMonoxideMean = carbonMonoxideMean;
        this.ozone = ozone;
        this.uvIndex = uvIndex;
    }
}
