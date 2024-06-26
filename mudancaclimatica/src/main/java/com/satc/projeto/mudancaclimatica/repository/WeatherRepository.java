package com.satc.projeto.mudancaclimatica.repository;

import com.satc.projeto.mudancaclimatica.models.WeatherData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeatherRepository extends MongoRepository<WeatherData, String> {

    List<WeatherData> findByLatitudeAndLongitudeAndRecordDateBetween(String latitude, String longitude, LocalDate startDate, LocalDate endDate);

    Page<WeatherData> findByLatitudeAndLongitudeAndRecordDateBetween(String latitude, String longitude, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
