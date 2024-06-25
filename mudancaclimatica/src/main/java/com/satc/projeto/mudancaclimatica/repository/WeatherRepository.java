package com.satc.projeto.mudancaclimatica.repository;

import com.satc.projeto.mudancaclimatica.models.WeatherData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends MongoRepository<WeatherData, String> {
}
