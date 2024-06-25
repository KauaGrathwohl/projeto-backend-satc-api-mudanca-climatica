package com.satc.projeto.mudancaclimatica.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "WeatherData")
public class WeatherData {

    @Id
    private String id;

    @Field("longitude")
    private String longitude;

    @Field("latitude")
    private String latitude;

    @Field("recordDate")
    private LocalDateTime recordDate;

    @Field("carbonMonoxideMean")
    private Double carbonMonoxideMean;

    @Field("ozone")
    private Double ozone;

    @Field("uvIndex")
    private Double uvIndex;

    public WeatherData(String longitude, String latitude, LocalDateTime recordDate, Double carbonMonoxideMean, Double ozone, Double uvIndex) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.recordDate = recordDate;
        this.carbonMonoxideMean = carbonMonoxideMean;
        this.ozone = ozone;
        this.uvIndex = uvIndex;
    }

}