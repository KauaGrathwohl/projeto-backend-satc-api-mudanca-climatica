package com.satc.projeto.mudancaclimatica.controllers;

import com.satc.projeto.mudancaclimatica.dto.WeatherDataDto;
import com.satc.projeto.mudancaclimatica.params.RequestWeatherInfoParam;
import com.satc.projeto.mudancaclimatica.models.WeatherData;
import com.satc.projeto.mudancaclimatica.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public List<WeatherData> getWeatherChanges(@RequestParam String latitude,
                                               @RequestParam String longitude,
                                               @RequestParam LocalDate startDate,
                                               @RequestParam LocalDate endDate) {
        RequestWeatherInfoParam requestWeatherInfoDto = new RequestWeatherInfoParam(latitude, longitude, startDate, endDate);
        return weatherService.getWeatherChanges(requestWeatherInfoDto);
    }

    @GetMapping("page-weather-data")
    public Page<WeatherData> getWeatherChangesPage(@RequestParam String latitude,
                                                   @RequestParam String longitude,
                                                   @RequestParam LocalDate startDate,
                                                   @RequestParam LocalDate endDate,
                                                   @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
                                                   @RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
        RequestWeatherInfoParam requestWeatherInfoDto = new RequestWeatherInfoParam(latitude, longitude, startDate, endDate);
        return weatherService.getWeatherChangesPage(requestWeatherInfoDto, offset, limit);
    }

    @PostMapping
    public WeatherData createWeatherRecord(@RequestBody WeatherDataDto weatherDataDto) {
        return weatherService.createWeatherRecord(weatherDataDto);
    }

    @PutMapping("{id}")
    public WeatherData updateWeatherRecord(@PathVariable("id") String id,
                                           @RequestBody WeatherDataDto weatherDataDto) {
        return weatherService.updateWeatherRecord(id, weatherDataDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteRecord(@PathVariable("id") String id) {
        weatherService.deleteWeatherRecord(id);
        return ResponseEntity.ok().build();
    }

}